package com.brunoarruda.hyper_dcpabe;

import java.util.List;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyper_dcpabe.io.Console;

public class Session {

    private Client client;
    private BlockchainConnection blockchain;

    public Session(BlockchainConnection blockchain) {
        this.client = new Client();
        this.blockchain = blockchain;
    }

    public void runClient() {
        runOnConsole();
    }
    
    private void runOnConsole() {
        List<String> options;
        String msg;
        String input;

        Console console = new Console();
        console.display("Bem Vindo ao Hyper-DCPABE\n");
        msg = "Você aceita a geração de novas chaves para acesso à rede?";
        input = console.input(msg, "sim", "não");
        console.display("");
        if (input.equals("sim")) {
            client.generateECKeys();
            Map<String, String> keys = client.getKeyAsString();
            msg = String.format("chave privada: %s", keys.get("private"));
            console.display(msg);
            msg = String.format("chave pública: %s", keys.get("public"));
            console.display(msg);
        }
        console.display("Para publicar seu endereço na Blockchain, precisamos de seus dados pessoais.");
        String name = console.input("Digite seu nome:");
        String email = console.input("Digite seu e-mail:");
        client.publishECKeys(blockchain, name, email);
    }

    public static void main(String[] args) {
        Session s = new Session(new BlockchainConnection());
        s.runClient();
    }
}