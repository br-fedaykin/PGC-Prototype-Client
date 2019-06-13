package com.brunoarruda.hyper_dcpabe.io;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {

    private Scanner scn;

    public void display(String msg) {
        System.out.println(msg);
    }

    public String input(String msg, String ... options) {
        return input(msg, Arrays.asList(options));
    }

    public String input(String msg, List<String> options) {
        if (scn == null) {
            scn = new Scanner(System.in);
        }
        System.out.println(msg);
        writeOptions(options);
        String input = scn.nextLine();
        if (options.size() > 0) {
            while (!options.contains(input)) {
                System.out.println("Entrada invalida");
                writeOptions(options);
                if (scn.hasNext()) {
                    input = scn.nextLine();
                }
            }
        }
        return input;
    }
    
    private void writeOptions(List<String> options) {
        if (options.size() > 0) {
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

    @Override
    protected void finalize() throws Throwable {
        if (scn != null) {
            scn.close();
        }
        super.finalize();
    }
}