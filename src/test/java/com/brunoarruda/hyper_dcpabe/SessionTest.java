package com.brunoarruda.hyper_dcpabe;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;

import org.junit.Before;

import utils.StubBlockChain;

/**
 * SessionTest
 */
public class SessionTest {
    
    private Session session;
    private StubBlockChain blockchain;

    @Before
    public void setUp() {
        blockchain = new StubBlockChain();
        session = new Session(blockchain);
    }

    public void test() {
        
    }
}