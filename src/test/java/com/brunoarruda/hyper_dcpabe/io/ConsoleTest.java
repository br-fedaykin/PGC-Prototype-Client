package com.brunoarruda.hyper_dcpabe.io;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.SortedMap;
import java.util.TreeMap;

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
        console.init();
        String output = systemOutput.stop();
        assertThat(output, containsString("Bem vindo ao Hyper-DCPABE" + System.lineSeparator()));
    }

    @Test
    public void testClientRejectsWrongInputs() {
        systemInput.sendMany("foo","1");
        SortedMap<String, String> menu = new TreeMap<String, String>();
        menu.put("1", "única opção");
        console.showMenu("menu teste", menu);
        String output = systemOutput.stop();
        assertThat(output, containsString("Entrada inválida"));
    }
}