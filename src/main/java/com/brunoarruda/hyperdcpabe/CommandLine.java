package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.Client;
import com.brunoarruda.hyperdcpabe.blockchain.BlockchainConnection;

/**
 * CommandLine
 */
public class CommandLine {

    private static final Map<String, String> COMMAND_ALIAS = new Hashtable<>();
    private static final int BUFFER_SIZE = 1024;
    private static Client client;

    static {
        populateCommands();
    }

    private static void populateCommands() {
        // user commands
        COMMAND_ALIAS.put("-u", "--create-user");
        COMMAND_ALIAS.put("-c", "--create-certifier");
        COMMAND_ALIAS.put("-l", "--load");

        // DCPABE commands
        COMMAND_ALIAS.put("-a", "--create-attributes");
        COMMAND_ALIAS.put("-y", "--yield-attributes");
        COMMAND_ALIAS.put("-e", "--encrypt");
        COMMAND_ALIAS.put("-d", "--decrypt");

        // blockchain commands
        COMMAND_ALIAS.put("-r", "--request-attributes");
        COMMAND_ALIAS.put("-cr", "--check-requests");
        COMMAND_ALIAS.put("-ga", "--get-attributes");
        COMMAND_ALIAS.put("-gr", "--get-recordings");
        COMMAND_ALIAS.put("-p", "--publish");

        // integrated blockchain / server commands
        COMMAND_ALIAS.put("-s", "--send");

        // testing command
        COMMAND_ALIAS.put("-m", "--milestone");
        COMMAND_ALIAS.put("-h", "--help");
    }

    public static void main(String[] args) {
        // TODO: allow multi input on main, if no args provided
        if (args.length == 0) {
            return;
        }
        if (client == null) {
            client = new Client(new BlockchainConnection());
        }
        String[] attributes;
        switch (args[0]) {
        case "-m":
        case "--milestone":
            // needed to provide the file to be encrypted
            String[] numberSplit = args[1].split("\\.");
            int[] choices = new int[numberSplit.length];
            for (int i = 0; i < choices.length; i++) {
                choices[i] = Integer.parseInt(numberSplit[i]);
            }
            runMilestone(choices);
            break;
        case "-l":
        case "--load":
            client.changeUser(args[1]);
            break;
        case "-u":
        case "--create-user":
            String name = args[1];
            String email = args[2];
            client.createUser(name, email);
            break;
        case "-c":
        case "--create-certifier":
            if (args.length == 3) {
                name = args[1];
                email = args[2];
                client.createCertifier(name, email);
            } else {
                client.createCertifier();
            }
            break;
        case "-a":
        case "--create-attributes":
            attributes = new String[args.length - 1];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 1];
            }
            client.createABEKeys(attributes);
            break;
        case "-r":
        case "--request-attribute":
            attributes = new String[args.length - 2];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 2];
            }
            client.requestAttribute(args[1], attributes);
            break;
        case "-cr":
        case "--check-requests":
            if (args[1].equals("download")) {
                client.getPersonalKeys();
            } else {
                client.checkAttributeRequests(args[1]);
            }
            break;
        case "-p":
        case "--publish":
            for (int i = 1; i < args.length; i++) {
                client.publish(args[i]);
            }
            break;
        case "-y":
        case "--yield-attributes":
            attributes = new String[args.length - 2];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 2];
            }
            client.yieldAttribute(args[1], attributes);
            break;
        case "-ga":
        case "--get-attributes":
            attributes = new String[args.length - 2];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 2];
            }
            client.getAttributes(args[1], attributes);
            break;
        case "-gr":
        case "--get-recordings":
            String[] recordings = new String[args.length - 2];
            for (int i = 0; i < recordings.length; i++) {
                recordings[i] = args[i + 2];
            }
            client.getRecordings(args[1], recordings);
            break;
        case "-e":
        case "--encrypt":
            String[] authorities = new String[args.length - 3];
            for (int i = 0; i < authorities.length; i++) {
                authorities[i] = args[i + 3];
            }
            client.encrypt(args[1], args[2], authorities);
            break;
        case "-s":
        case "--send":
            if (args[1].equals("attributes")) {
                for (int i = 2; i < args.length; i++) {
                    client.sendAttributes(args[i]);
                }
            } else {
                for (int i = 1; i < args.length; i++) {
                    client.send(args[i]);
                }
            }
            break;
        case "-d":
        case "--decrypt":
            client.decrypt(args[1]);
            break;
        }
        try {
            client.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void runMilestone(int[] choice) {
        // TODO: move milestone command sets to a shell script
        List<String[]> multiArgs = new ArrayList<String[]>();
        /**
         * Milestone 1 cenário: novo prontuário Cliente 1 java usa o código do ABE (cria
         * usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado) Cliente
         * 2 java usa o código do ABE (supondo que possui o atributo1, obtém prontuário1
         * codificado e o decodifica)
         */
        if (choice[0] == 1) {
            // pre-setup to deliver file to be encrypted to user folder
            String path = "data\\client\\Alice-04b41\\";
            new File(path).mkdirs();
            getFileFromResources(path, "lorem_ipsum.md");

            // certificador cria perfil e atributo, e os publica
            multiArgs.add("--create-user CRM crm@email.com".split(" "));
            multiArgs.add("--create-certifier".split(" "));
            multiArgs.add("--create-attributes atributo1 atributo2 atributo3".split(" "));
            multiArgs.add("--publish user certifier attributes".split(" "));

            // usuário 1 - Bob, cria perfil e solicita concessão do atributo 1 (chave pessoal ABE)
            multiArgs.add("--create-user Bob bob@email.com".split(" "));
            multiArgs.add("--publish user".split(" "));
            multiArgs.add("--load Bob-04206".split(" "));
            multiArgs.add("--request-attribute CRM-04170 atributo1".split(" "));

            // usuário 2 - Alice, cria perfil, recebe chaves públicas e criptografa um documento
            multiArgs.add("--create-user Alice alice@email.com".split(" "));
            multiArgs.add("--publish user".split(" "));
            multiArgs.add("--load Alice-04b41".split(" "));
            multiArgs.add("--get-attributes CRM-04170 atributo1".split(" "));
            multiArgs.add("--encrypt lorem_ipsum.md atributo1 CRM-04170".split(" "));
            multiArgs.add("--send lorem_ipsum.md".split(" "));

            // certificador recebe requisição de atributo e o concede ao Bob
            multiArgs.add("--load CRM-04170".split(" "));
            multiArgs.add("--check-requests pending".split(" "));
            multiArgs.add("--yield-attributes Bob-04206 atributo1".split(" "));
            multiArgs.add("--send attributes Bob-04206".split(" "));

            // usuário 1 - Bob, de posse do atributo, o descriptografa
            multiArgs.add("--load Bob-04206".split(" "));
            multiArgs.add("--check-requests ok".split(" "));
            multiArgs.add("--check-requests download".split(" "));
            multiArgs.add("--get-recordings Alice-04b41 lorem_ipsum.md".split(" "));
            multiArgs.add("--decrypt lorem_ipsum.md".split(" "));
        }

        if (choice[0] == 2) {
            // pre-setup to deliver file to be encrypted to user folder
            String path = "data\\client\\Alice-04b41\\";
            new File(path).mkdirs();
            getFileFromResources(path, "lorem_ipsum2.md");

            /**
             * cenário: novo prontuário
             * Cliente 1 java (usuário já criado) obtém novos atributos: atributo2 e
             * atributo3. O atributo1 já foi obtido no milestone1. Cliente 1 java envia
             * prontuario2 (i.e., pdf2 encriptado) com atributo2 AND atributo3 Cliente 2
             * java (só com atributo1) tenta mas não consegue obter o prontuário2 (nem
             * decodificá-lo) pois não tem atributos.
             */
            if (choice[1] == 1) {
                runMilestone(new int[]{1});
                multiArgs.add("--load Alice-04b41".split(" "));
                multiArgs.add("--get-attributes CRM-04170 atributo2 atributo3".split(" "));
                String[] specialArgs = {"--encrypt", "lorem_ipsum2.md", "and atributo2 atributo3", "CRM-04170"};
                multiArgs.add(specialArgs);
                multiArgs.add("--send lorem_ipsum2.md".split(" "));
                multiArgs.add("--load Bob-04206".split(" "));
                multiArgs.add("--get-recordings Alice-04b41 lorem_ipsum2.md".split(" "));
                multiArgs.add("--decrypt lorem_ipsum2.md".split(" "));
            }
            /**
             * cenário: atualiza somente prontuário
             * Cliente 1 java (usuário já criado) envia prontuário1, mesmo atributo1, novo
             * pdf1 Classe Prontuario tem atributo dataEnvio, portanto atualizar atributo.
             * Cliente 2 java obtém prontuário1 codificado e o decodifica (pois possui
             * atributo1 do milestone1).
             */
            if (choice[1] == 2) {
                runMilestone(new int[] { 1 });
                getFileFromResources(path, "lorem_ipsum-edit.md", "lorem_ipsum.md");
                multiArgs.add("--load Alice-04b41".split(" "));
                multiArgs.add("--encrypt lorem_ipsum.md atributo1 CRM-04170".split(" "));
                multiArgs.add("--send lorem_ipsum.md".split(" "));
                multiArgs.add("--load Bob-04206".split(" "));
                multiArgs.add("--get-recordings Alice-04b41 lorem_ipsum.md".split(" "));
                multiArgs.add("--decrypt lorem_ipsum.md".split(" "));
            }
            if (choice[1] == 3) {
                multiArgs.add("--load Bob-04206".split(" "));
                multiArgs.add("--decrypt lorem_ipsum.md".split(" "));
            }
        }

        if (choice[0] > 2) {
            System.out.println("Milestones ainda não implementadas");
            System.exit(-1);
        }

        // call all args
        for (String[] args : multiArgs) {
            main(args);
        }
    }

    private static void getFileFromResources(String path, String fileName) {
        getFileFromResources(path, fileName, fileName);
    }

    private static void getFileFromResources(String path, String inputfileName, String outputFileName) {
        ClassLoader classLoader = CommandLine.class.getClassLoader();

        URL resource = classLoader.getResource(inputfileName);
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
}
