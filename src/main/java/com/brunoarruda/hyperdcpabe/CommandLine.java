package com.brunoarruda.hyperdcpabe;

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
        COMMAND_ALIAS.put("-e", "--encript");
        COMMAND_ALIAS.put("-d", "--decrypt");

        // blockchain / server commands
        COMMAND_ALIAS.put("-r", "--request-attributes");
        COMMAND_ALIAS.put("-ga", "--get-attributes");
        COMMAND_ALIAS.put("-gr", "--get-recordings");
        COMMAND_ALIAS.put("-s", "--send");
        COMMAND_ALIAS.put("-p", "--publish");

        // testing command
        COMMAND_ALIAS.put("-m", "--milestone");
        COMMAND_ALIAS.put("-h", "--help");
    }

    public static void main(String[] args) {
        if (client == null) {
            client = new Client(new BlockchainConnection());
        }
        String[] attributes;
        switch (args[0]) {
        case "-m":
        case "--milestone":
            runMilestone(Integer.parseInt(args[1]));
            break;
        case "-l":
        case "--load":
            client.loadUserData(args[1]);
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
        case "-p":
        case "--publish":
            for (int i = 1; i < args.length; i++) {
                client.publish(args[i]);
            }
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
            for (int i = 1; i < args.length; i++) {
                client.send(args[i]);
            }
            break;
        }
    }

    public static void runMilestone(int milestone) {
        List<String[]> multiArgs = new ArrayList<String[]>();
        /**
         * Milestone 1 cenário: novo prontuário Cliente 1 java usa o código do ABE (cria
         * usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado) Cliente
         * 2 java usa o código do ABE (supondo que possui o atributo1, obtém prontuário1
         * codificado e o decodifica)
         */
        if (milestone == 1) {
            // adicionar certificador para dar acesso ao bob
            // multiArgs.add("-u Bob bob@email.com".split(" "));
            // multiArgs.add("-c".split(" "));
            // multiArgs.add("-a atributo1".split(" "));
            // multiArgs.add("-p user certifier attributes".split(" "));
            // multiArgs.add("-u Alice alice@email.com".split(" "));
            multiArgs.add("-l Alice-04206da4".split(" "));
            multiArgs.add("-ga Bob-041702dd atributo1".split(" "));
            multiArgs.add("-e lorem_ipsum.pdf atributo1 Bob-041702dd".split(" "));
            multiArgs.add("-s lorem_ipsum.pdf".split(" "));
            multiArgs.add("-l Bob-041702dd".split(" "));
            multiArgs.add("-gr Alice-04206da4 lorem_ipsum.pdf".split(" "));
        }

        if (milestone == 2) {
            multiArgs.add("-u CRM crm@email.com".split(" "));
            multiArgs.add("-c".split(" "));
            multiArgs.add("-a atributo1 atributo2 atributo3".split(" "));
            multiArgs.add("-p user certifier attributes".split(" "));
            multiArgs.add("-l Alice-04206da4".split(" "));
            // Alice já existe no #m1 com atributo1
            multiArgs.add("-ga CRM atributo2 atributo3".split(" "));
            multiArgs.add("-e lorem_ipsum2.pdf \"and atributo2 atributo3\" CRM".split(" "));
            // já existe no #m1 o atributo1
        }

        if (milestone > 1) {
            System.out.println("Milestones ainda não implementadas");
            System.exit(-1);
        }

        // call all args
        for (String[] args : multiArgs) {
            main(args);
        }
    }
}
