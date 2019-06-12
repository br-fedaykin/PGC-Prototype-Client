package com.brunoarruda.hyper_dcpabe.blockchain;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

public class BlockchainConnection {

    public Transaction createTransaction(ECKey userKeys, JSONObject content) {
        return new Transaction();
    }
}