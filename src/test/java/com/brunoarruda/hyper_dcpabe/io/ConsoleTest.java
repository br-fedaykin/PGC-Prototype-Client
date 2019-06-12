package com.brunoarruda.hyper_dcpabe.io;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import utils.*;


public class ConsoleTest {

    private Console console;

    private static ConsoleOutputCapturer systemOutput;
    private static ConsoleInputFake systemInput;

    @BeforeClass
    public static void beforeAll() {
        systemOutput = new ConsoleOutputCapturer();
        systemInput = new ConsoleInputFake();
    }

    @Before
    public void setUp() {
        console = new Console();
        systemOutput.start();
        systemInput.start();
    }

    @After
    public void tearDown() {
        systemInput.stop();
    }

    @Test
    public void testCanStartConsoleInterface() {
        console.display("Bem Vindo!");
        String output = systemOutput.stop();
        assertThat(output, is("Bem Vindo!" + System.lineSeparator()));
    }

    @Test
    public void testClientRejectsWrongInputs() {
        List<String> options = new ArrayList<String>();
        options.add("sim");

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                console.input("Escreva sim\n", options);
            }
        });
        t.start();
        String lines = "opção invalida" + System.lineSeparator();
        lines = lines + "sim"+ System.lineSeparator();
        systemInput.send(lines);
        
        try {
            t.join();    
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertThat(console.getInput(), containsString("sim"));

        // make sure that input will be requested again
        assertThat(systemOutput.stop(), endsWith("(sim): "));
    }
}