package com.brunoarruda.hyper_dcpabe;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.InputStream;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import utils.*;

public class ClientTest {

    private Client client;

    private InputStream stdin;
    private static ConsoleOutputCapturer  systemOutput;

    @BeforeClass
    public static void beforeAll() {
        systemOutput = new ConsoleOutputCapturer();
    }

    @Before
    public void setUp() throws Exception {
        client = new Client();
        stdin = System.in;
        systemOutput.start();
    }

    @After
    public void tearDown() {
        System.setIn(stdin);
    }

    @Test
    public void testCanStartConsoleInterface() {
        client.runOnConsole();
        String output = systemOutput.stop();        
        assertThat(output, CoreMatchers.containsString("Bem Vindo"));
    }
}
