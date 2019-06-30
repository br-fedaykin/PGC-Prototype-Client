package com.brunoarruda.hyperdcpabe.io;

import java.util.Scanner;
import java.util.SortedMap;

public class Console {

    public Scanner scn;

    @Override
    protected void finalize() throws Throwable {
        if (scn != null) {
            scn.close();
        }
        super.finalize();
    }

    public void display(String msg) {
        display(msg, 1);
    }

    public void display(String msg, int lineSeparations) {
        String lineBreaks = "";
        for (int i = 0; i < lineSeparations; i++) {
            lineBreaks = lineBreaks + System.lineSeparator();
        }
        System.out.print(msg + lineBreaks);
    }

	public void init() {
        display("Bem vindo ao Hyper-DCPABE", 2);
    }

    public String showMenu(String menuName, SortedMap<String, String> menu) {
        if (scn == null) {
            scn = new Scanner(System.in);
        }

        display(menuName);
        for (String key : menu.keySet()) {
            String line = String.format("%s. %s", key, menu.get(key));
            display(line);
        }
        display("Opção:", 2);
        String response = scn.nextLine();
        if (!menu.containsKey(response)) {
            display("Entrada inválida");
            return showMenu(menuName, menu);
        }
        return response;
	}

	public String input(String message) {
        display(message, 0);
        String response = scn.nextLine();
        display("", 1);
        return response;
	}

	public void showOutput(String string) {
        display(string, 2);
	}

	public String[] inputMany(String ... messages) {
        String[] result = new String[messages.length];
        for (int i = 0; i < messages.length; i++) {
            result[i] = input(messages[i]);
        }
        return result;
    }
}