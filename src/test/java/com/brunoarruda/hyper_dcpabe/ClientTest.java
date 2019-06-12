package com.brunoarruda.hyper_dcpabe;

import org.junit.Test;

import static org.junit.Assert.*;

import org.bitcoinj.core.ECKey;
import org.junit.Before;

public class ClientTest {

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = new Client();
    }

    @Test
    public void testGenerateValidBlockchainKeys() {
        ECKey keys = client.generateECKeys(Client.OUTPUT_AS_OBJECT);
        
        assertNotNull(keys);
        assertNotNull(keys.getPrivateKeyAsHex());
        assertNotNull(keys.getPublicKeyAsHex());
    }
}
