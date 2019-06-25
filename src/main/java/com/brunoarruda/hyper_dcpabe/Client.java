package com.brunoarruda.hyper_dcpabe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyper_dcpabe.blockchain.CiphertextJSON;
import com.brunoarruda.hyper_dcpabe.io.FileController;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.Ciphertext;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.Message;
import sg.edu.ntu.sce.sands.crypto.dcpabe.PublicKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

public final class Client {

    private String gpPath;
    private final FileController fc;

    private GlobalParameters gp;
    private User user;
    private Certifier certifier;

    private BlockchainConnection blockchain;
    private String dataPath;

    private Map<String, Map<String, PublicKey>> publishedAttributes;

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
        loadAttributes();
        this.blockchain.init();

        gpPath = fc.getDataDirectory() + "globalParameters";
        gp = DCPABE.globalSetup(160);
        fc.writeToDir(fc.getDataDirectory(), "globalParameters.json", gp);
        try {
            Utility.writeGlobalParameters(gpPath, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUser(String name, String email) {
        ECKey newKeys = this.blockchain.generateKeys();
        user = new User(name, email, newKeys);
        fc.writeToDir(fc.getUserDirectory(user), "user.json", user);
    }

    private String getClientDirectory() {
        return fc.getDataDirectory() + "client\\";
    }

    public void getAttributes(String authority, String[] attributes) {
        Map<String, PublicKey> keys = null;
        if (!hasPublicKeysOfAuthority(authority)) {
            keys = blockchain.getABEPublicKeys(authority, attributes);
            if (keys != null) {
                if (hasPublicKeysOfAuthority(authority)) {
                    publishedAttributes.replace(authority, keys);
                } else {
                    publishedAttributes.put(authority, keys);
                }
                String path = getClientDirectory() + "PublicKeys\\";
                fc.writeToDir(path, authority + ".json", keys);
            }
        }
    }

    public void createABEKeys(String[] attributes) {
        String name = certifier.getPrivateECKey();
        AuthorityKeys ak = DCPABE.authoritySetup(name, gp, attributes);
        certifier.setAuthorityABEKeys(ak);
        String path = fc.getUserDirectory(certifier);
        fc.writeToDir(path, "authorityPublicKeys.json", ak.getPublicKeys());
        fc.writeToDir(path, "Certifier.json", certifier);
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

    public void publish(String content) {
        String path = fc.getUserDirectory(user);
        ObjectNode obj;
        if (content.equals("user")) {
            obj = fc.loadAsJSON(path, "User.json");
            obj = removeFieldFromJSON(obj, "ECKeys.private", "personalABEKeys");
            blockchain.publishUser(user.getUserID(), obj);
        }
        if (content.equals("certifier")) {
            obj = fc.loadAsJSON(path, "Certifier.json");
            obj = removeFieldFromJSON(obj, "ECKeys.private", "authorityKeys.authorityID", "authorityKeys.secretKeys");
            blockchain.publishAuthority(certifier.getUserID(), obj);
        }
        if (content.equals("attributes")) {
            obj = fc.loadAsJSON(path, "authorityPublicKeys.json");
            blockchain.publishABEKeys(certifier.getUserID(), obj);
        }
    }

    private ObjectNode removeFieldFromJSON(ObjectNode obj, String... fieldSequences) {
        ObjectNode rootObj = obj;
        for (String fieldSequence : fieldSequences) {
            String[] fields = fieldSequence.split("\\.");
            for (int i = 0; i < fields.length - 1; i++) {
                obj = (ObjectNode) obj.get(fields[i]);
            }
            obj.remove(fields[fields.length - 1]);
            obj = rootObj;
        }
        return obj;
    }

    public void loadAttributes() {
        publishedAttributes = new Hashtable<String, Map<String, PublicKey>>();
        String path = getClientDirectory() + "PublicKeys";
        File folder = new File(path);

        Map<String, PublicKey> attributes = new HashMap<String, PublicKey>();
        if (folder.exists()) {
            for (String json : folder.list()) {
                attributes = fc.readAsMap(path, json, String.class, PublicKey.class);
                System.out.println(attributes.get("atributo1").getClass().getSimpleName());
                String authority = json.split("\\.")[0];
                publishedAttributes.put(authority, attributes);
            }
        }
    }

    public Map<String, Map<String, PublicKey>> getAllPublishedAttributes() {
        return publishedAttributes;
    }

    public Map<String, PublicKey> getPublishedAttributes(String authority) {
        return publishedAttributes.get(authority);
    }

    public boolean hasPublicKeysOfAuthority(String authority) {
        if (publishedAttributes == null) {
            return false;
        }
        return publishedAttributes.containsKey(authority);
    }

    /**
     * Getters and Setters
     */
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
        System.out.println("Not implemented");
    }

    @Deprecated
    public void getABEPublicKeys(String string, String string2) {
        System.out.println("Not Implemented");
    }

    public void loadUserData(String userID) {
        String path = getClientDirectory() + userID;
        user = fc.readFromDir(path, "user.json", User.class);
        certifier = fc.readFromDir(path, "Certifier.json", Certifier.class);
    }

    public void encrypt(String file, String policy, String[] authorities) {
        PublicKeys pks = new PublicKeys();
        for (String auth : authorities) {
            pks.subscribeAuthority(publishedAttributes.get(auth));
        }
        AccessStructure as = AccessStructure.buildFromPolicy(policy);
        Message m = DCPABE.generateRandomMessage(gp);
        Ciphertext ct = DCPABE.encrypt(m, as, gp, pks);
        CiphertextJSON ctJson = new CiphertextJSON(ct);
        String path = fc.getUserDirectory(user);
        fc.writeToDir(path, "(cipher)" + file + ".json", ctJson);
        PaddedBufferedBlockCipher aes = Utility.initializeAES(m.getM(), true);
        fc.writeEncrypted(aes, path, file);
    }
}
