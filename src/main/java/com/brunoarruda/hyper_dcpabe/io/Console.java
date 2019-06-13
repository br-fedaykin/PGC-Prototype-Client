package com.brunoarruda.hyper_dcpabe.io;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        System.out.println(msg);
    }

	public void init() {
        display("Bem vindo ao Hyper-DCPABE");
    }
    
    public void showMenu() {
        display("Menu Principal:");
	}

	public String input(String msg, String ... string) {
		return null;
	}

	public String input(String msg, List<String> options) {
		return null;
	}
}