package com.brunoarruda.hyperdcpabe;

import com.brunoarruda.hyperdcpabe.Client.RequestStatus;
import com.brunoarruda.hyperdcpabe.monitor.ExecutionProfiler;

import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * CommandLine
 */
@Command(name = "", subcommands = {
    CommandLine.CheckRequests.class,
    CommandLine.CreateAttributes.class,
    CommandLine.CreateCertifier.class,
    CommandLine.CreateUser.class,
    CommandLine.Decrypt.class,
    CommandLine.Encrypt.class,
    CommandLine.GetPersonalKeys.class,
    CommandLine.GetRecordings.class,
    CommandLine.Help.class,
    CommandLine.Init.class,
    CommandLine.Load.class,
    CommandLine.Demonstration.class,
    CommandLine.Publish.class,
    CommandLine.RequestAttributes.class,
    CommandLine.Send.class,
    CommandLine.YieldAttributes.class,
    CommandLine.GetAttributes.class},
    footer = {"","type SmartDCPABE COMMAND --help for more information on a command."}
    )

public class CommandLine implements Runnable {

    private static final ExecutionProfiler profiler = ExecutionProfiler.getInstance();
    private static picocli.CommandLine cmd;
    public static void main(String[] args) {
        cmd = new picocli.CommandLine(new com.brunoarruda.hyperdcpabe.CommandLine());
        cmd.execute(args);
        profiler.writeToFile();
    }

    @Override
    public void run() {
        cmd.usage(System.out);
    }

    @Command
    abstract static class BasicCommand implements Runnable {
        static Client client;

        @Option(names = "--help", usageHelp = true, description = "display this help and exit")
        boolean help;

        @Option(names = {"--enable-profile", "--profile"}, description = "activate profiling of time and gas consumption.")
        boolean enableProfile;

        @Option(names = {"--finish-profile"}, description = "keep profiling active on this command and deactivate it after finishing.")
        boolean finishProfile;

        @Option(names = {"--disable-profile", "--no-profile"}, description = "deactivate profiling")
        boolean disableProfile;

        @Override
        public void run() {
            Class<?> command = getCommandClass();
            profiler.setIsLastCommand(finishProfile);
            if (enableProfile) {
                profiler.enablePersistentProfiling();
            } else if (disableProfile) {
                profiler.disablePersistentProfiling();
            }
            profiler.start(command, command.getSimpleName());
            if (command != Init.class) {
                client = new Client();
            }
            commandBody();
            profiler.end();
        }

        public Class<?> getCommandClass() {
            return this.getClass();
        }

        abstract public void commandBody();
    }

    @Command(name = "init")
    static class Init extends BasicCommand {
        @Parameters(index = "0", description = "administrator name")
        private String adminName;

        @Parameters(index = "1", description = "administrator e-mail")
        private String adminEmail;

        @Parameters(index = "2", description = "administrator's wallet private key")
        private String adminPrivateKey;

        @Option(names = {"--network", "-n"}, defaultValue = "http://127.0.0.1:7545", description = "network address of Ethereum provider. Defaults to ${DEFAULT VALUE}")
        private String url;

        @Override
        public void commandBody () {
            client = new Client(url, adminName, adminEmail, adminPrivateKey);
        }
    }

    // /*
    //  * USER MANAGEMENT USERS COMMANDS
    //  */
    @Command(name = "create-user")
    static class CreateUser extends BasicCommand {
        @Parameters(index="0", description = "user name")
        String name;

        @Parameters(index="1", description = "user e-mail")
        String email;

        @Parameters(index="2", description = "user's wallet private key")
        String privateKey;

        @Override
        public void commandBody() {
            client.createUser(name, email, privateKey);
        }
    }

    @Command(name = "create-certifier")
    static class CreateCertifier extends BasicCommand {

        @Parameters(index = "0", defaultValue = "", description = "certifier name")
        String name;

        @Parameters(index = "1", defaultValue = "", description = "certifier e-mail")
        String email;

        @Parameters(index = "2", defaultValue = "", description = "certifier's wallet private key")
        String privateKey;
        @Override
        public void commandBody() {
            validate();
            if (name.equals("")) {
                client.createCertifier();
            } else {
                client.createCertifier(name, email, privateKey);
            }
        }

        public void validate() {
            if (name.equals("") || email.equals("") || privateKey.equals("")) {
                if (! (name.equals("") && email.equals("") && privateKey.equals(""))) {
                    throw new ParameterException(cmd.getSubcommands().get("create-certifier"), "Invalid combination of parameters. Must inform all or none of them.");
                }
            }
        }
    }

    @Command(name = "load")
    static class Load extends BasicCommand {

        @Parameters(index = "0", description = "user ID") String userID;
        @Override
        public void commandBody() {
            client.setActiveUser(userID);
        }
    }

    /*
     * DCPABE COMMANDS
     */
    @Command(name = "create-attributes")
    static class CreateAttributes extends BasicCommand {

        @Parameters(arity="1..*", description = "attributes") String[] attributes;
        @Override
        public void commandBody() {
            client.createABEKeys(attributes);
        }
    }

    @Command(name = "yield-attributes")
    static class YieldAttributes extends BasicCommand {

        @Parameters(index = "0", description = "requester user GID")
        String requester;

        @Parameters(index = "1", description = "decision about user request")
        int decision;
        @Override
        public void commandBody() {
            client.yieldAttribute(requester, decision);
        }
    }

    @Command(name = "encrypt")
    static class Encrypt extends BasicCommand {

        @Parameters(index = "0") String file;
        @Parameters(index = "1") String policy;
        @Parameters(index = "2", arity = "1..*", description = "certifier addresses of attributes used in policy")
        String[] certifiers;
        @Override
        public void commandBody() {
            client.encrypt(file, policy, certifiers);
        }
    }

    @Command(name = "decrypt")
    static class Decrypt extends BasicCommand {

        @Parameters(index= "0") String file;
        @Override
        public void commandBody() {
            client.decrypt(file);
        }
    }

    /*
     * BLOCKCHAIN COMMANDS
     */

    @Command(name = "request-attributes")
    static class RequestAttributes extends BasicCommand {
        @Parameters(index = "0", description = "certifier owner of the attributes")
        String certifier;

        @Parameters(index = "1", arity = "1..*") String[] attributes;
        @Override
        public void commandBody() {
            client.requestAttribute(certifier, attributes);
        }
    }

    @Command(name = "check-requests")
    static class CheckRequests extends BasicCommand {

        @Parameters(index = "0") String status;

        @Override
        public void commandBody() {
            client.checkAttributeRequests(RequestStatus.valueOf(status.toUpperCase()));
        }
    }

    @Command(name = "get-personal-keys")
    static class GetPersonalKeys extends BasicCommand {
        @Override
        public void commandBody() {
            client.getPersonalKeys();
        }
    }

    @Command(name = "get-attributes")
    static class GetAttributes extends BasicCommand {

        @Parameters(index = "0") String certifier;
        @Parameters(index = "1", arity = "1..*") String[] attributes;

        @Override
        public void commandBody() {
            client.getAttributes(certifier, attributes);
        }
    }

    @Command(name = "publish")
    static class Publish extends BasicCommand {
        @Parameters(index = "0", arity = "1..*", description = "objects to publish")
        String[] content;
        @Override
        public void commandBody() {
            for (String c : content) {
                client.publish(c);
            }
        }
    }

    /*
     * NETWORK (BLOCKCHAIN / FILE SERVER) COMMANDS
     */
    @Command(name = "send")
    static class Send extends BasicCommand {
        @Parameters(arity = "0..*", index = "0", description = "files or attributes to send, respectively, to server or Blockchain")
        String[] inputs;

        @Option(names = "attributes", description = "treat input as atributes")
        boolean attributes;
        @Override
        public void commandBody() {
            if (attributes) {
                for (String attribute : inputs) {
                    client.sendAttributes(attribute);
                }
            } else {
                for (String file : inputs) {
                    client.send(file);
                }
            }
        }
    }

    @Command(name = "get-recordings")
    static class GetRecordings extends BasicCommand {
        @Parameters(index = "0", description = "owner of the files") String userID;
        @Parameters(index = "1", arity = "1..*") String[] recordings;
        @Override
        public void commandBody() {
            client.getRecordings(userID, recordings);
        }
    }
    @Command(name = "demonstration", aliases = {"demo"})
    static class Demonstration extends BasicCommand {
        @Parameters(index = "0", description = "desired scenario")
        int scenario;

        @Parameters(index = "1", defaultValue = "0", description = "(optional) subscenario")
        int subscenario;
        @Override
        public void run() {
            commandBody();
        }

        @Override
        public void commandBody() {
            validate();
            SmartDCPABEDemonstration.runMilestone(scenario, subscenario, enableProfile);
        }

        private void validate() {
            boolean invalid;
            switch (scenario) {
                case 1:
                    invalid = subscenario != 0;
                    break;
                case 2:
                    invalid = subscenario < 1 || subscenario > 3;
                default:
                    invalid = false;
            }
            if (invalid) {
                throw new ParameterException(cmd.getSubcommands().get("demonstration"), "Invalid scenario");
            }
        }
    }

    @Command(name = "help")
    static class Help implements Runnable {

        @Parameters(index = "0..1",defaultValue = "") String command;

        @Override
        public void run() {
            System.out.println("test");
            if (command.equals("")) {
                cmd.usage(System.out);
            } else if (!cmd.getHelp().allSubcommands().containsKey(command)) {
                System.out.println("Unknown command: " + command);
            } else {
                cmd.getSubcommands().get(command).usage(System.out);
            }
        }
    }
}
