package com.brunoarruda.hyper_dcpabe;

import org.junit.Test;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.stop.Stop.stopQuietly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;

import org.bitcoinj.core.ECKey;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;

public class ClientTest {

    private static final int TEST_PORT = 8080;
    private Client client;
    private String testDataPath;

    private ClientAndServer mockServer;

    @Before
    public void setUp() {
        client = new Client(new BlockchainConnection());
        testDataPath = client.getDataPath() + "\\test";
        client.setDataPath(testDataPath);
    }

    @Before
    public void startMockServer() {
        mockServer = startClientAndServer(TEST_PORT);
    }

    @After
    public void stopMockServer() {
        stopQuietly(mockServer);
    }

    @After
    public void tearDown() {
        File testPath = new File(testDataPath);
        recursiveDelete(testPath);
    }

    private static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

    @Test
    public void testGenerateECKeys() {
        client.generateECKeys();
        ECKey keys = client.getKey();
        assertNotNull(keys);

        // private key are compressed, its hex size is 64
        assertEquals(64, keys.getPrivateKeyAsHex().length());

        // public key are compressed, its hex size is 66
        assertEquals(66, keys.getPublicKeyAsHex().length());
    }

    @Test
    public void testOutputsECKeysAstStringOrFileOrObject() {
        client.generateECKeys();
        assertThat(client.getKey(), CoreMatchers.instanceOf(ECKey.class));
        assertThat(client.getECKeysAsString(), CoreMatchers.isA(Map.class));
        assertTrue(client.writeKeyOnFile());
    }

    public void testGetABEKeys() {
        // TODO: usar StubBlockchain para obter as chaves aqui
    }

    public void testSendFileToServer() {
        mockServer
                .when(
                    request()
                        .withMethod("POST")
                        .withPath("/path")
                        .withBody("{JSON}")
                )
                .respond(
                    response()
                        .withStatusCode(302));

        // TODO: call some Client.sendFiles() with params
    }
}
