package com.brunoarruda.hyper_dcpabe;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyper_dcpabe.io.Console;

public class Session {

    private Client client;
    public final SortedMap<String, String> mainMenu;

    public Session(BlockchainConnection blockchain) {
        this.client = new Client(blockchain);

        mainMenu = new TreeMap<String, String>();
        mainMenu.put("1", "gerar chaves");
        mainMenu.put("2", "publicar chaves na Blockchain");
        mainMenu.put("3", "receber atributos para publicar documento");
        mainMenu.put("0", "sair");
    }

    public void runClient(String interfaceType) {
        switch (interfaceType.toLowerCase()) {
            case "console":
                runOnConsole();
                break;
            default:
                break;
        }
    }

    private void runOption(Console console, String option) {
        if (option.equals("1")) {
            client.createUser("Alice", "alice@email.com");
            Map<String, String> keys = client.getECKeysAsString();
            String output = "chave privada: %s"+ System.lineSeparator() + "chave pública: %s";
            console.showOutput(String.format(output, keys.get("private"), keys.get("public")));
        }

        if (option.equals("2")) {
            String name = console.input("Insira seu nome: ");
            String email = console.input("Insira seu email: ");
            client.publishECKeys(name, email);
            console.showOutput("usuário publicado na blockchain");
        }

        if (option.equals("3")) {
            client.getABEPublicKeys("CRM", "CFM");
            console.showOutput("atributos recebidos");
        }
    }

    private void runOnConsole() {
        String option = "";
        Console console = new Console();
        console.init();
        while(!option.equals("0")) {
            option = console.showMenu("Menu Principal:", mainMenu);
            runOption(console, option);
        }
    }

    public static void main(String[] args) {
        Session s = new Session(new BlockchainConnection());
        s.runClient("console");
    }
}