package com.brunoarruda.hyper_dcpabe.io;

import java.util.List;
import java.util.Scanner;

public class Console {

    private String input = "";

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    public void display(String msg) {
        System.out.println(msg);
    }

    public void processUserInput() {

    }

    public void input(String msg, List<String> options) {
        Scanner scn = new Scanner(System.in);
        System.out.println(msg);
        writeOptions(options);
        String input = scn.nextLine();
        while (!options.contains(input)) {
            System.out.println("Entrada invalida");
            writeOptions(options);
            if (scn.hasNext()) {
                input = scn.nextLine();
            }
        }
        
        this.input = input;
    }

    private void writeOptions(List<String> options) {
        boolean reduceOptions = false;
        for (String s : options) {
            if (s.contains(" ")){
                reduceOptions  = true;
            }
        }
        if (reduceOptions) {
            System.out.println(String.join(".\n", options));
            System.out.println("digite uma opção:");
        } else {
            System.out.printf("(%s): ", String.join("/", options));
        }
    }
}