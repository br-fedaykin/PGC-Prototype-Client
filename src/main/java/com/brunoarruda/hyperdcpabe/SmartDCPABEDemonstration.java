package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import picocli.CommandLine;

public class SmartDCPABEDemonstration {

    private static final int BUFFER_SIZE = 1024;
    private static final CommandLine cmd = new picocli.CommandLine(new com.brunoarruda.hyperdcpabe.CommandLine());

    private static class PersonData {
        public final String NAME, EMAIL, ADDRESS, GID, PRIV_KEY;

        public PersonData(String name, String address, String privateKey) {
            this.NAME = name;
            this.EMAIL = name.toLowerCase() + "@email.com";
            this.ADDRESS = address;
            this.GID = name + "-" + this.ADDRESS;
            this.PRIV_KEY = privateKey;
        }
    }

    private static final PersonData ADMIN = new PersonData(
        "admin",
        "0xFae373E0BFfaE794fA818D749D6da38D4f7cA986",
        "e4d8c81796894ea5bf202e3a3204948dddd62f4d709c278bf8096898957be241"
    );
    private static final PersonData CRM = new PersonData(
        "CRM",
        "0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0",
        "e15b910f8c61580befebecff2d79abf38998035cbc317400a96c4736a424f6dc"
    );
    private static final PersonData ALICE = new PersonData(
        "Alice",
        "0xb038476875480BCE0D0FCf0991B4BB108A3FCB47",
        "4237a475aa6579f2a0fc85d90cbcda1fad3db70391315a6c37b51de3a8cb503a"
    );
    private static final PersonData BOB = new PersonData(
        "Bob",
        "0xF7908374b1a445cCf65F729887dbB695c918BEfc",
        "ab0439882857ffb5859c1a3a6bf40a6848daeaab6605c873c3e425de53c2c4ab"
    );
    public static void runMilestone(int scenario, int subcenario, boolean profileEnabled) {
        switch (scenario) {
            case 1:
                runMilestone1(profileEnabled);
                break;
            default:
                runMilestone2(subcenario, profileEnabled);
                break;
        }
    }

    public static void runMilestone1(boolean profileEnabled) {
        /*
         * Milestone 1 cenário: novo prontuário Cliente 1 java usa o código do ABE (cria
         * usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado) Cliente
         * 2 java usa o código do ABE (supondo que possui o atributo1, obtém prontuário1
         * codificado e o decodifica)
         */

        deleteDirectory(new File("data"));
        // pre-setup to deliver file to be encrypted to user folder
        String path = String.format("data/client/%s/", ALICE.GID);
        new File(path).mkdirs();
        getFileFromResources(path, "demo/lorem_ipsum.md", "lorem_ipsum.md");

        // admin inicia o sistema e cria os contratos
        if (profileEnabled) {
            cmd.execute("init", ADMIN.NAME, ADMIN.EMAIL, ADMIN.PRIV_KEY, "--profile");
        } else {
            cmd.execute("init", ADMIN.NAME, ADMIN.EMAIL, ADMIN.PRIV_KEY);
        }

        // certificador cria perfil e atributo, e os publica
        cmd.execute("create-user", CRM.NAME, CRM.EMAIL, CRM.PRIV_KEY);
        cmd.execute("create-certifier");
        cmd.execute("create-attributes", "atributo1", "atributo2", "atributo3");
        cmd.execute("publish", "user", "certifier", "attributes");

        // usuário 1 - Bob, cria perfil e solicita concessão do atributo 1 (chave pessoal ABE)
        cmd.execute("create-user", BOB.NAME, BOB.EMAIL, BOB.PRIV_KEY);
        cmd.execute("publish", "user");
        cmd.execute("request-attributes",  CRM.GID, "atributo1");

        // usuário 2 - Alice, cria perfil, recebe chaves públicas e criptografa um documento
        cmd.execute("create-user", ALICE.NAME, ALICE.EMAIL, ALICE.PRIV_KEY);
        cmd.execute("publish", "user");
        cmd.execute("get-attributes", CRM.GID, "atributo1");
        cmd.execute("encrypt", "lorem_ipsum.md", "atributo1", CRM.GID);
        cmd.execute("send", "lorem_ipsum.md");

        // certificador recebe requisição de atributo e o concede ao Bob
        cmd.execute("load", CRM.GID);
        cmd.execute("check-requests", "pending");
        cmd.execute("yield-attributes", BOB.GID, "0");
        cmd.execute("send", "attributes", BOB.GID);

        // usuário 1 - Bob, de posse do atributo, o descriptografa
        cmd.execute("load", BOB.GID);
        cmd.execute("check-requests", "ok");
        cmd.execute("get-personal-keys");
        cmd.execute("get-recordings", ALICE.GID, "lorem_ipsum.md");
        if (profileEnabled) {
            cmd.execute("decrypt", "lorem_ipsum.md", "--finish-profile");
        } else {
            cmd.execute("decrypt", "lorem_ipsum.md");
        }
    }

    public static void runMilestone2(int subcenario, boolean profileEnabled) {
        runMilestone1(profileEnabled);
        String path = String.format("data/client/%s/", ALICE.GID);
        if (subcenario == 1) {
            /*
            * cenário: novo prontuário
            * Cliente 1 java (usuário já criado) obtém novos atributos: atributo2 e
            * atributo3. O atributo1 já foi obtido no milestone1. Cliente 1 java envia
            * prontuario2 (i.e., pdf2 encriptado) com atributo2 AND atributo3 Cliente 2
            * java (só com atributo1) tenta mas não consegue obter o prontuário2 (nem
            * decodificá-lo) pois não tem atributos.
            */

            // pre-setup to deliver file to be encrypted to user folder
            new File(path).mkdirs();
            getFileFromResources(path, "demo/lorem_ipsum2.md", "lorem_ipsum2.md");
            cmd.execute("load", ALICE.GID);
            cmd.execute("get-attributes", CRM.GID, "atributo2", "atributo3");
            cmd.execute("encrypt", "lorem_ipsum2.md", "and atributo2 atributo3", CRM.GID);
            cmd.execute("send", "lorem_ipsum2.md");
            cmd.execute("load" , BOB.GID);
            cmd.execute("get-recordings", ALICE.GID, "lorem_ipsum2.md");
            cmd.execute("decrypt", "lorem_ipsum2.md");
        } else if (subcenario == 2) {
            /*
            * cenário: atualiza somente prontuário
            * Cliente 1 java (usuário já criado) envia prontuário1, mesmo atributo1, novo
            * pdf1 Classe Prontuario tem atributo dataEnvio, portanto atualizar atributo.
            * Cliente 2 java obtém prontuário1 codificado e o decodifica (pois possui
            * atributo1 do milestone1).
            */
            getFileFromResources(path, "demo/lorem_ipsum-edit.md", "lorem_ipsum.md");
            cmd.execute("load", ALICE.GID);
            cmd.execute("encrypt", "lorem_ipsum.md", "atributo1", CRM.GID);
            cmd.execute("send", "lorem_ipsum.md");
            cmd.execute("load", BOB.GID);
            cmd.execute("get-recordings", ALICE.GID, "lorem_ipsum.md");
            cmd.execute("decrypt", "lorem_ipsum.md");
        } else if (subcenario == 3) {
            /*
            * Cliente 1 java procura prontuario1 e atualiza: a) atributo1 por atributo1 AND
            * atributo2 e b) dataEnvio. Cliente 2 java (só com atributo1) tenta mas não
            * consegue obter o prontuário1 (nem decodificá-lo) pois não tem atributo2.
            */
            cmd.execute("load", ALICE.GID);
            cmd.execute("get-attributes", CRM.GID, "atributo1");
            cmd.execute("encrypt", "lorem_ipsum", "and atributo1 atributo2", CRM.GID);
            cmd.execute("send", "lorem_ipsum.md");
            cmd.execute("load" , BOB.GID);
            cmd.execute("get-recordings", ALICE.GID, "lorem_ipsum.md");
            cmd.execute("decrypt", "lorem_ipsum.md");
        }
    }

    private static void getFileFromResources(String path, String inputFileName, String outputFileName) {
        ClassLoader classLoader = CommandLine.class.getClassLoader();

        URL resource = classLoader.getResource(inputFileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            File f = new File(resource.getFile());
            try (FileInputStream fis = new FileInputStream(f);
                BufferedInputStream bis = new BufferedInputStream(fis);
                FileOutputStream fos = new FileOutputStream(path + outputFileName)) {
                byte[] buff = new byte[BUFFER_SIZE];
                int readBytes;
                while ((readBytes = bis.read(buff)) != -1) {
                    fos.write(buff, 0, readBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
