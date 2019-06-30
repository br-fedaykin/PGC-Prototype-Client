package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
import java.security.SecureRandom;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.Recording;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

public class BlockchainConnection {
    private String dataPath = "blockchain";
    private FileController fc;

    static private final byte[] seed = "Honk Honk".getBytes();
    static private final SecureRandom random = new SecureRandom(seed);

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
        return new ECKey(random);
    }

    @Deprecated
    public Transaction createTransaction(ECKey userKeys, ObjectNode content) {
        return null;
    }

    public Map<String, PublicKey> getABEPublicKeys(String authority, String[] attributes) {
        String path = getBlockchainDataPath() + "PublicABEKeys\\";
        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("A autoridade " + authority + " não publicou nenhum atributo.");
            return null;
        } else {
            for (String json : dir.list()) {
                if (json.startsWith(authority)) {
                    try {
                        return fc.readAsMap(path, json, String.class, PublicKey.class);
                    } catch (Exception e) {
                    }
                }
            }
            return null;
        }
    }

    public void publishData(String userID, ObjectNode obj) {
        String path = getBlockchainDataPath() + "Files\\" + userID + "\\";
        File dir = new File(path);
        String fileName = obj.get("name").asText() + ".json";
        dir.mkdirs();
        fc.writeToDir(path, fileName, obj);
    }

    public void publishABEKeys(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "PublicABEKeys");
        dir.mkdirs();
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
    }

	public void publishAuthority(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "Authority");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
	}

	public void publishUser(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "User");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
	}

	public Recording getRecording(String userID, String fileName) {
        Recording r = null;
        String path = getBlockchainDataPath() + "Files\\" + userID + "\\";
        String fileNameEdited = fileName.split("\\..+?$")[0] + ".json";
        if (new File(path + fileName).exists()) {
            r = fc.readFromDir(path, fileName, Recording.class);
        } else if (new File(path + fileNameEdited).exists()) {
            r = fc.readFromDir(path, fileNameEdited, Recording.class);
        } else {
            System.out.println("File " + fileName + " not found on blockchain");
        }
        return r;
	}
}