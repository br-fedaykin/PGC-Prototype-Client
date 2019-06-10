package com.brunoarruda.hyper_dcpabe;
import sg.edu.ntu.sce.sands.crypto.DCPABETool;

public final class Client {
    private Client() {
    }

    public static void main(String[] args) {
        String[] my_args = {"help"};
        DCPABETool.main(my_args);
    }
}
