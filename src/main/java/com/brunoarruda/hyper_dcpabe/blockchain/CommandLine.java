package com.brunoarruda.hyper_dcpabe.blockchain;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.Client;

/**
 * CommandLine
 */
public class CommandLine {

    private static final Map<String, String> COMMAND_ALIAS = new Hashtable<>();

    static {
        populateCommands();
    }

    private static void populateCommands() {
        COMMAND_ALIAS.put("-u", "--create-user");
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
        switch (args[0]) {
            case "-m":
            case "--milestone":
                runMilestone(Integer.parseInt(args[1]));
                break;
            case "-c":
            case "--create-user":
                Client c = new Client(new BlockchainConnection());
                for (String v : args) {
                    System.out.println(v);
                }
                // TODO: add/create functions in Client in order to complete every part of milestone 1
        }
    }

    public static void runMilestone(int milestone) {
        List<String []> multiArgs = new ArrayList<String []>();
        /**
         * Milestone 1
         * cenário: novo prontuário
         * Cliente 1 java usa o código do ABE
         * (cria usuário, obtém atributo1, envia prontuário1 - i.e., pdf1 encriptado)
         * Cliente 2 java usa o código do ABE
         * (supondo que possui o atributo1, obtém prontuário1 codificado e o decodifica)
         */
        if (milestone == 1) {
            multiArgs.add("-c Alice alice@email.com".split(" "));
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