package com.brunoarruda.hyper_dcpabe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.Client;
import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;

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
        COMMAND_ALIAS.put("-u", "--create-user");
        COMMAND_ALIAS.put("-l", "--load");
        COMMAND_ALIAS.put("-c", "--create-certifier");
        COMMAND_ALIAS.put("-a", "--create-attributes");
        COMMAND_ALIAS.put("-y", "--yield-attributes");
        COMMAND_ALIAS.put("-r", "--request-attributes");
        COMMAND_ALIAS.put("-g", "--get-attributes");
        COMMAND_ALIAS.put("-e", "--encript");
        COMMAND_ALIAS.put("-s", "--send");
        COMMAND_ALIAS.put("-p", "--publish");
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
        case "load":
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
        case "-g":
        case "-get-attributes":
            attributes = new String[args.length - 2];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = args[i + 2];
            }
            client.getAttributes(args[1], attributes);
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
            // multiArgs.add("-u Bob bob@email.com".split(" "));
            // multiArgs.add("-c".split(" "));
            // multiArgs.add("-a atributo1".split(" "));
            // multiArgs.add("-p user certifier attributes".split(" "));
            // multiArgs.add("-u Alice alice@email.com".split(" "));
            multiArgs.add("-l Bob-029b5e51".split(" "));
            multiArgs.add("-g Bob atributo1".split(" "));
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
