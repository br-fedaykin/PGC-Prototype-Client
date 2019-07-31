package com.brunoarruda.hyperdcpabe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.stop.Stop.stopQuietly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import static org.junit.Assert.*;

import java.io.File;
import com.brunoarruda.hyperdcpabe.blockchain.BlockchainConnection;

import org.junit.After;
import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    private static final int TEST_PORT = 8080;
    private Client client;
    private String testDataPath;

    @Mock
    private ClientAndServer mockServer;

    @Before
    public void setUp() {
        client = new Client();
        testDataPath = client.getDataPath() + "\\test";
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
        fail("Test should be remade. Change it to test user creation.");
        // client.generateECKeys();
        // ECKey keys = client.getKey();
        // assertNotNull(keys);

        // // private key are compressed, its hex size is 64
        // assertEquals(64, keys.getPrivateKeyAsHex().length());

        // // public key are compressed, its hex size is 66
        // assertEquals(66, keys.getPublicKeyAsHex().length());
    }

    @Test
    public void testOutputsECKeysAstStringOrFileOrObject() {
        fail("Test should be moved to FileController or User");
        // client.generateECKeys();
        // assertThat(client.getKey(), CoreMatchers.instanceOf(ECKey.class));
        // assertThat(client.getECKeysAsString(), CoreMatchers.isA(Map.class));
        // assertTrue(client.writeKeyOnFile());
    }

    @Test
    public void testGetABEKeys() {
        // TODO: usar StubBlockchain para obter as chaves aqui
        fail("Finish this test");
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
        fail("Finish this test");
    }

    @Test
    public void testCanSendAndReceiveDataFromBlockchain() {
        fail("Finish this test");
    }

    @Test
    public void testCanConnectWithSockets() {
        fail("Finish this test");
    }
}
