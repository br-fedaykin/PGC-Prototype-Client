package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.Client.RequestStatus;

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

        // system commands
        COMMAND_ALIAS.put("-i", "--init");

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
            System.out.println("Nenhum comando executado");
            return;
        }
        if (args[0].equals("-m") || args[0].equals("--milestone")) {
            milestone(args);
        } else if ((args[0].equals("-i") || args[0].equals("--init"))){
            init(args);
        } else {
            runCommand(args);
        }
    }

    public static void init(String[] args) {
        String networkURL = "http://127.0.0.1:7545";
        int index = 1;
        if (args.length == 5) {
            networkURL = args[index];
            index = 2;
        }
        String adminName = args[index];
        String adminEmail = args[index + 1];
        String adminPrivateKey = args[index + 2];
        client = new Client(networkURL, adminName, adminEmail, adminPrivateKey);
    }

    // user commands
    public static void createUser(String[] args){
        String name = args[1];
        String email = args[2];
        String privateKey = null;
        if (args.length == 4) {
            privateKey = args[3];
        }
        client.createUser(name, email, privateKey);
    }

    public static void createCertifier(String[] args){
        if (args.length == 4) {
            String name = args[1];
            String email = args[2];
            String privateKey = args[3];
            client.createCertifier(name, email, privateKey);
        } else {
            client.createCertifier();
        }
    }

    public static void load(String[] args){
        client.setActiveUser(args[1]);
    }

    // DCPABE commands

    public static void createAttributes(String[] args){
        String[] attributes = new String[args.length - 1];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 1];
            }
            client.createABEKeys(attributes);
    }

    public static void yieldAttributes(String[] args){
        for (int i = 2; i < args.length; i++) {
            client.yieldAttribute(args[1], Integer.parseInt(args[i]));
        }
    }


    public static void encrypt(String[] args){
        String[] authorities = new String[args.length - 3];
        for (int i = 0; i < authorities.length; i++) {
            authorities[i] = args[i + 3];
        }
        client.encrypt(args[1], args[2], authorities);
    }

    public static void decrypt(String[] args){

    }

    // blockchain commands

    public static void requestAttributes(String[] args){
        String[] attributes = new String[args.length - 2];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = args[i + 2];
        }
        client.requestAttribute(args[1], attributes);
    }

    public static void checkRequests(String[] args){
        if (args[1].equals("download")) {
            client.getPersonalKeys();
        } else {
            client.checkAttributeRequests(RequestStatus.valueOf(args[1].toUpperCase()));
        }
    }

    public static void getAttributes(String[] args){
        String[] attributes = new String[args.length - 2];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = args[i + 2];
        }
        client.getAttributes(args[1], attributes);
    }

    public static void publish(String[] args){
        for (int i = 1; i < args.length; i++) {
            client.publish(args[i]);
        }
    }

    // integrated blockchain / server commands
    public static void send(String[] args){
        if (args[1].equals("attributes")) {
            for (int i = 2; i < args.length; i++) {
                client.sendAttributes(args[i]);
            }
        } else {
            for (int i = 1; i < args.length; i++) {
                client.send(args[i]);
            }
        }
    }

    public static void getRecordings(String[] args){
        String[] recordings = new String[args.length - 2];
        for (int i = 0; i < recordings.length; i++) {
            recordings[i] = args[i + 2];
        }
        client.getRecordings(args[1], recordings);
    }

    // demonstration command
    public static void milestone(String[] args){
        // parsing necessary to navigate through milestone function
        String[] numberSplit = args[1].split("\\.");
        int[] choices = new int[numberSplit.length];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = Integer.parseInt(numberSplit[i]);
        }
        runMilestone(choices);
    }

    public static void help(String[] args){
        System.out.println("Menu de ajuda não implementada.");
    }

    public static void runCommand(String[] args) {
        client = new Client();
        switch (args[0]) {
        case "-h":
        case "--help":
            help(args);
            break;
        case "-l":
        case "--load":
            load(args);
            break;
        case "-u":
        case "--create-user":
            createUser(args);
            break;
        case "-c":
        case "--create-certifier":
            createCertifier(args);
            break;
        case "-a":
        case "--create-attributes":
            createAttributes(args);
            break;
        case "-r":
        case "--request-attribute":
            requestAttributes(args);
            break;
        case "-cr":
        case "--check-requests":
            checkRequests(args);
            break;
        case "-p":
        case "--publish":
            publish(args);
            break;
        case "-y":
        case "--yield-attributes":
            yieldAttributes(args);
            break;
        case "-ga":
        case "--get-attributes":
            getAttributes(args);
            break;
        case "-gr":
        case "--get-recordings":
            getRecordings(args);
            break;
        case "-e":
        case "--encrypt":
            encrypt(args);
            break;
        case "-s":
        case "--send":
            send(args);
            break;
        case "-d":
        case "--decrypt":
            client.decrypt(args[1]);
            break;
        default:
            System.out.println("Comando não reconhecido: " + String.join(" ", args));
        }
    }

    public static void runMilestone(int[] choice) {
        /**
         * Milestone 1 cenário: novo prontuário Cliente 1 java usa o código do ABE (cria
         * usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado) Cliente
         * 2 java usa o código do ABE (supondo que possui o atributo1, obtém prontuário1
         * codificado e o decodifica)
         */
        String args;
        if (choice[0] == 1) {
            // pre-setup to deliver file to be encrypted to user folder
            String path = "data/client/Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47/";
            new File(path).mkdirs();
            getFileFromResources(path, "test/lorem_ipsum.md", "lorem_ipsum.md");

            // admin inicia o sistema e cria os contratos
            args = "--init http://127.0.0.1:7545 admin admin@email.com ";
            args = args + "e4d8c81796894ea5bf202e3a3204948dddd62f4d709c278bf8096898957be241";
            init(args.split(" "));

            // certificador cria perfil e atributo, e os publica
            args = "--create-user CRM crm@email.com ";
            args = args + "e15b910f8c61580befebecff2d79abf38998035cbc317400a96c4736a424f6dc";
            runCommand(args.split(" "));
            runCommand("--create-certifier".split(" "));
            runCommand("--create-attributes atributo1 atributo2 atributo3".split(" "));
            runCommand("--publish user certifier attributes".split(" "));

            // usuário 1 - Bob, cria perfil e solicita concessão do atributo 1 (chave pessoal ABE)
            args = "--create-user Bob bob@email.com ";
            args = args + "ab0439882857ffb5859c1a3a6bf40a6848daeaab6605c873c3e425de53c2c4ab";
            runCommand(args.split(" "));
            runCommand("--publish user".split(" "));
            runCommand("--load Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));
            runCommand("--request-attribute CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0 atributo1".split(" "));

            // usuário 2 - Alice, cria perfil, recebe chaves públicas e criptografa um documento
            args = "--create-user Alice alice@email.com ";
            args = args + "4237a475aa6579f2a0fc85d90cbcda1fad3db70391315a6c37b51de3a8cb503a";
            runCommand(args.split(" "));
            runCommand("--publish user".split(" "));
            runCommand("--load Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47".split(" "));
            runCommand("--get-attributes CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0 atributo1".split(" "));
            runCommand("--encrypt lorem_ipsum.md atributo1 CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0".split(" "));
            runCommand("--send lorem_ipsum.md".split(" "));

            // certificador recebe requisição de atributo e o concede ao Bob
            runCommand("--load CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0".split(" "));
            runCommand("--check-requests pending".split(" "));
            runCommand("--yield-attributes Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc 0".split(" "));
            runCommand("--send attributes Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));

            // usuário 1 - Bob, de posse do atributo, o descriptografa
            runCommand("--load Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));
            runCommand("--check-requests ok".split(" "));
            runCommand("--check-requests download".split(" "));
            runCommand("--get-recordings Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47 lorem_ipsum.md".split(" "));
            runCommand("--decrypt lorem_ipsum.md".split(" "));
        }

        if (choice[0] == 2) {
            // pre-setup to deliver file to be encrypted to user folder
            String path = "data/client/Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47/";
            new File(path).mkdirs();
            getFileFromResources(path, "test/lorem_ipsum2.md", "lorem_ipsum2.md");

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
                runCommand("--load Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47".split(" "));
                runCommand("--get-attributes CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0 atributo2 atributo3".split(" "));
                String[] specialArgs = {"--encrypt", "lorem_ipsum2.md", "and atributo2 atributo3", "CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0"};
                runCommand(specialArgs);
                runCommand("--send lorem_ipsum2.md".split(" "));
                runCommand("--load Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));
                runCommand("--get-recordings Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47 lorem_ipsum2.md".split(" "));
                runCommand("--decrypt lorem_ipsum2.md".split(" "));
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
                getFileFromResources(path, "test/lorem_ipsum-edit.md", "lorem_ipsum.md");
                runCommand("--load Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47".split(" "));
                runCommand("--encrypt lorem_ipsum.md atributo1 CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0".split(" "));
                runCommand("--send lorem_ipsum.md".split(" "));
                runCommand("--load Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));
                runCommand("--get-recordings Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47 lorem_ipsum.md".split(" "));
                runCommand("--decrypt lorem_ipsum.md".split(" "));
            }
            /*
             * Cliente 1 java procura prontuario1 e atualiza: a) atributo1 por atributo1 AND
             * atributo2 e b) dataEnvio. Cliente 2 java (só com atributo1) tenta mas não
             * consegue obter o prontuário1 (nem decodificá-lo) pois não tem atributo2.
             */
            if (choice[1] == 3) {
                runMilestone(new int[] { 1 });
                runCommand("--load Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47".split(" "));
                runCommand("--get-attributes CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0 atributo1".split(" "));
                String[] encryptArgs = { "--encrypt", "lorem_ipsum.md", "and atributo1 atributo2",
                        "CRM-0xFB7EAfB7fBdaA775d0D52fAaEBC525C1cE173EE0" };
                runCommand(encryptArgs);
                runCommand("--send lorem_ipsum.md".split(" "));
                runCommand("--load Bob-0xF7908374b1a445cCf65F729887dbB695c918BEfc".split(" "));
                runCommand("--get-recordings Alice-0xb038476875480BCE0D0FCf0991B4BB108A3FCB47 lorem_ipsum.md".split(" "));
                runCommand("--decrypt lorem_ipsum.md".split(" "));
            }
        }

        if (choice[0] > 2) {
            System.out.println("Milestone not recognized.");
            System.exit(-1);
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
}
