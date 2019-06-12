package com.brunoarruda.hyper_dcpabe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.io.Console;

public class Session {

    private Client client;

    public Session() {
        this.client = new Client();
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
        options = new ArrayList<String>();
        options.add("sim");
        options.add("não");
        msg = "Você aceita a geração de novas chaves para acesso à rede?";
        input = console.input(msg, options);
        console.display("");
        if (input.equals("sim")) {
            client.generateECKeys();
            Map<String, String> keys = client.getKeyAsString();
            msg = String.format("chave privada: %s", keys.get("private"));
            console.display(msg);
            msg = String.format("chave pública: %s", keys.get("public"));
            console.display(msg);
        }
    }

    public static void main(String[] args) {
        Session s = new Session();
        s.runClient();
    }
}