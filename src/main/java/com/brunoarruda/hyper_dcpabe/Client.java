package com.brunoarruda.hyper_dcpabe;

import java.util.Scanner;

import org.bitcoinj.core.ECKey;

import sg.edu.ntu.sce.sands.crypto.DCPABETool;

public final class Client {

    public static final int OUTPUT_AS_OBJECT = 0;

    public Client() {
    }

    public static void main(String[] args) {
        Client client = new Client();        
    }

    public void runOnConsole() {        
        StringBuilder msg = new StringBuilder();
        Scanner scn = new Scanner(System.in);

        System.out.println("Bem Vindo ao Hyper-DCPABE\n");
        System.out.println("Você aceita a geração de novas chaves para acesso à rede?\n (sim/não):");
        
    }

    public ECKey generateECKeys(int outputMode) {
        return new ECKey();
    }
}
