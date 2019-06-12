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

        String inputLines = "opção invalida" + System.lineSeparator();
        inputLines = inputLines + "sim"+ System.lineSeparator();
        systemInput.send(inputLines);
        String received = console.input("Escreva sim", options);

        assertThat(received, is("sim"));

        // make sure that input will be requested again
        assertThat(systemOutput.stop(), endsWith("(sim): "));
    }
}