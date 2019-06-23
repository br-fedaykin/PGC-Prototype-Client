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

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

public final class Client {

    private String gpPath;
    private final FileController fc;
    private File dataFolder;

    private GlobalParameters gp;
    private User user;
    private Certifier certifier;

    private BlockchainConnection blockchain;
    private String dataPath;

    public Client(BlockchainConnection blockchain) {
        this(blockchain, "data");
    }

    public Client(BlockchainConnection blockchain, String dataPath) {
        this.blockchain = blockchain;
        fc = FileController.getInstance().configure(dataPath);
        blockchain.init();
        init();
    }

    private void init() {
        gpPath = fc.getDataDirectory() + "globalParameters";
        gp = DCPABE.globalSetup(160);
        fc.writeToDir(fc.getDataDirectory(), "globalParameters.json", gp);
        try {
            Utility.writeGlobalParameters(gpPath, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.blockchain.init();
    }

    public void createUser(String name, String email) {
        ECKey newKeys = this.blockchain.generateKeys();
        user = new User(name, email, newKeys);
        fc.writeUser(user);
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

    @Deprecated
    public boolean writeKeyOnFile() {
        return writeKeyOnFile(fc.getDataDirectory());
    }

    @Deprecated
    public boolean writeKeyOnFile(String path) {
        return false;
    }

    @Deprecated
	public void publishECKeys(String name, String email) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);
        Transaction tx = blockchain.createTransaction(this.user.getECKeys(), json);
        tx.send();
	}

    public void getAttributes(String authority, String[] attributes) {
        if (user.hasPublicKeysOfAuthority(authority)) {
            Map<String, PublicKey> ABEKeys = blockchain.getABEPublicKeys(authority);
            if (ABEKeys.size() != 0) {
                user.addPublicKeys(authority, ABEKeys);
            }
        }
    }

    @Deprecated
	public void getABEPublicKeys(String string, String string2) {
        System.out.println("Not Implemented");
	}

	public void createABEKeys(String[] attributes) {
        String name = certifier.getPrivateECKey();
        AuthorityKeys ak = DCPABE.authoritySetup(name, gp, attributes);
        certifier.setAuthorityABEKeys(ak);
        String path = fc.getUserDirectory(certifier);
        fc.writeToDir(path, "authorityPublicKeys.json", ak.getPublicKeys());
        fc.writeUser(certifier);
        try {
            Utility.writeSecretKeys(path + "authoritySecretKey", ak.getSecretKeys());
            Utility.writePublicKeys(path + "authorityPublicKey", ak.getPublicKeys());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void createCertifier(String name, String email) {
        certifier = new Certifier(name, email, new ECKey());
	}

	public void createCertifier() {
        if (user != null) {
            certifier = new Certifier(user);
        } else {
            System.out.println("Crie um usuário ou informe nome e e-mail");
        }
	}
}
