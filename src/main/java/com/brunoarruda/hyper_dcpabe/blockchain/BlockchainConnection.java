package com.brunoarruda.hyper_dcpabe.blockchain;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

public class BlockchainConnection {

    // TODO: create socket as field

    public Transaction createTransaction(ECKey userKeys, JSONObject content) {
        return null;
    }

	public JSONObject getABEPublicKey(String attribute) {
        // TODO: new socket
        // TODO:connect to TEST_PORT
        // Send request
        // Get result
        return null;
	}

	public ECKey generateKeys() {
		return new ECKey();
	}
}