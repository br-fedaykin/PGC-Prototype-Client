package com.brunoarruda.hyper_dcpabe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyper_dcpabe.blockchain.Transaction;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

import sg.edu.ntu.sce.sands.crypto.DCPABETool;

public final class Client {
    private String dataPath = "data";
    private File dataFolder = null;

    private ECKey userECKeys;
    
    public void generateECKeys() {
        this.userECKeys = new ECKey();
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataPath() {        
        return dataPath;
    }

    public ECKey getKey() {
        return userECKeys;
    }

    public Map<String, String> getKeyAsString() {
        Map<String, String> keys = new HashMap<String, String>();
        keys.put("private", userECKeys.getPrivateKeyAsHex());
        keys.put("public", userECKeys.getPublicKeyAsHex());
        return keys;
    }

    public boolean writeKeyOnFile() {
        return writeKeyOnFile(dataPath);
    }

    public boolean writeKeyOnFile(String path) {
        dataFolder = new File(dataPath);
        if (!dataFolder.exists() && !dataFolder.isDirectory()) {
            dataFolder.mkdirs();
        }
        String privateKeyFileName = dataPath + "\\userPrivateKey";
        String publicKeyFileName = dataPath + "\\userPublicKey";
        Map<String, String> keys = getKeyAsString();
        try (FileOutputStream privateStream = new FileOutputStream(new File(privateKeyFileName));
                FileOutputStream publicStream = new FileOutputStream(new File(publicKeyFileName))) {
            privateStream.write(keys.get("private").getBytes());
            publicStream.write(keys.get("public").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	public void publishECKeys(BlockchainConnection blockchain, String name, String email) {
        
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        Transaction tx = blockchain.createTransaction(this.userECKeys, json);
        tx.send();
	}
}
