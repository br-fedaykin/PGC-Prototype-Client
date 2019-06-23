package com.brunoarruda.hyper_dcpabe.blockchain;

import java.io.File;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.io.FileController;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

public class BlockchainConnection {
    private String dataPath = "blockchain";
    private FileController fc;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public void init() {
        fc = FileController.getInstance();
        File dataFolder = new File(fc.getDataDirectory() + dataPath);
        dataFolder.mkdirs();
    }

    public ECKey generateKeys() {
        return new ECKey();
    }

    @Deprecated
    public Transaction createTransaction(ECKey userKeys, JSONObject content) {
        return null;
    }

    public Map<String, PublicKey> getABEPublicKeys(String authority) {
        File dir = new File(getBlockchainDataPath() + authority);
        if (!dir.exists()) {
            System.out.println("A autoridade " + authority + "não publicou nenhum atributo.");
            return null;
        } else {
            for (String s : dir.list()) {
                if (s.equals(authority)) {
                    try {
                        return Utility.readPublicKeys(s);
                    } catch (Exception e) {
                    }
                }
            }
            return null;
        }
    }

    public void publishABEKeys(String label, ObjectNode obj) {
        File dir = new File(getBlockchainDataPath() + "PublicABEKeys");
        dir.mkdirs();
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
    }

	public void publishAuthority(String label, ObjectNode obj) {
        File dir = new File(getBlockchainDataPath() + "Authority");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
	}

	public void publishUser(String label, ObjectNode obj) {
        File dir = new File(getBlockchainDataPath() + "User");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
	}
}