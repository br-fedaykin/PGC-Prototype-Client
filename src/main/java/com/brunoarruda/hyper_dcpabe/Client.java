package com.brunoarruda.hyper_dcpabe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyper_dcpabe.blockchain.Transaction;
import com.brunoarruda.hyper_dcpabe.io.FileController;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

import sg.edu.ntu.sce.sands.crypto.DCPABETool;

public final class Client {
    private String dataPath = "data";
    private File dataFolder = null;

    private User user;

    private BlockchainConnection blockchain;

    public Client(BlockchainConnection blockchain) {
        this.blockchain = blockchain;
    }

    public void createUser(String name, String email) {
        ECKey newKeys = this.blockchain.generateKeys();
        user = new User(name, email, newKeys);
        FileController fc = FileController.getInstance();
        fc.writeUser(dataPath, user);
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public ECKey getKey() {
        return user.getECKeys();
    }

    public Map<String, String> getECKeysAsString() {
        return user.getECKeysAsString();
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
        Map<String, String> keys = user.getECKeysAsString();
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

	public void publishECKeys(String name, String email) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        Transaction tx = blockchain.createTransaction(this.user.getECKeys(), json);
        tx.send();
	}

	public void getABEPublicKeys(String ... attributes) {
        for (String attribute : attributes) {
            if(user.getABEKeys().containsKey(attribute)) {
                JSONObject ABEKey = blockchain.getABEPublicKey(attribute);
                if (ABEKey != null) {
                    user.getABEKeys().put(attribute, ABEKey);
                }
            }
        }
    }

	public void getAttributes(String authorityName, String[] attributes) {

	}
}
