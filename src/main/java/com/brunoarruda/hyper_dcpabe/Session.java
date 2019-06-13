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
        String msg;
        String input;

        Console console = new Console();
        console.init();
        console.showMenu();
    }

    public static void main(String[] args) {
        Session s = new Session(new BlockchainConnection());
        s.runClient();
    }
}