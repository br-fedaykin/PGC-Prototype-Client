package com.brunoarruda.hyper_dcpabe;

import static org.junit.Assert.assertThat;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.ConsoleInputFake;
import utils.ConsoleOutputCapturer;
import utils.StubBlockChain;

/**
 * SessionTest
 */
public class SessionTest {
    
    private Session session;
    private StubBlockChain blockchain;
    private static ConsoleInputFake systemInput;
    private static ConsoleOutputCapturer systemOutput;

    @BeforeClass
    public static void BeforeAll() {
        systemInput = new ConsoleInputFake();
        systemOutput = new ConsoleOutputCapturer();
    }
    
    @Before
    public void setUp() {
        blockchain = new StubBlockChain();
        session = new Session(blockchain);
        systemInput.start();
        systemOutput.start();
    }

    @After
    public void tearUp() {
        systemInput.stop();
    }

    @Test
    public void testCanRunOnConsoleAndExits() {
        systemInput.send("0");
        session.runClient("console");
    }

    @Test
    public void testOutputsECKeys() {
        systemInput.sendMany("1","0");
        session.runClient("console");
        String output = systemOutput.stop();
        assertThat(output, CoreMatchers.containsString("chave pública: "));
        assertThat(output, CoreMatchers.containsString("chave privada: "));
    }

    @Test
    public void testConfirmsPublicationOnBlockchain() {
        systemInput.sendMany("1", "2","test", "test@email.com", "0");
        session.runClient("console");
        String output = systemOutput.stop();
        assertThat(output, CoreMatchers.containsString("usuário publicado na blockchain"));
    }
    
    @Test
    public void testConfirmsReceivingOfAttributes() {
        systemInput.sendMany("1", "2","test", "test@email.com", "3", "0");
        session.runClient("console");
        String output = systemOutput.stop();
        assertThat(output, CoreMatchers.containsString("atributos recebidos"));
    }
}