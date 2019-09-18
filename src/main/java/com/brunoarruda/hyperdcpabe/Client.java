package com.brunoarruda.hyperdcpabe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import com.brunoarruda.hyperdcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyperdcpabe.CiphertextJSON;
import com.brunoarruda.hyperdcpabe.Recording.FileMode;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.Message;
import sg.edu.ntu.sce.sands.crypto.dcpabe.PublicKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

public final class Client {

    private static final int SERVER_PORT = 8080;

    private final FileController fc;

    private GlobalParameters gp;
    private User user;
    private Certifier certifier;

    private BlockchainConnection blockchain;
    private ServerConnection server;
    private static final String DATA_PATH = "data";

    private Map<String, Map<String, PublicKey>> publishedAttributes;

    public Client() {
        fc = FileController.getInstance().configure(DATA_PATH);
        ObjectNode clientData = (ObjectNode) fc.loadAsJSON(getClientDirectory(), "clientData.json");
        if (clientData != null) {
            loadUserData(clientData.get("userID").asText());
            String networkURL = clientData.get("networkURL").asText();
            if (networkURL.equals("null")) {
                throw new RuntimeException(
                        "Execute o comando --init informando o endereço de rede" + " para conexão com a blockchain");
            }
            String contractUsersAddress = clientData.get("contractUsersAddress").asText();
            String contractAuthorityAddress = clientData.get("contractAuthorityAddress").asText();
            String contractFilesAddress = clientData.get("contractFilesAddress").asText();
            String contractKeysAddress = clientData.get("contractKeysAddress").asText();
            contractFilesAddress = (contractFilesAddress.equals("null")) ? null : contractFilesAddress;
            contractAuthorityAddress = (contractAuthorityAddress.equals("null")) ? null : contractAuthorityAddress;
            this.blockchain = new BlockchainConnection(networkURL, contractUsersAddress, contractAuthorityAddress,
                    contractFilesAddress, contractKeysAddress);
            fc.writeToDir(getClientDirectory(), "clientData.json", clientData);
        } else {
            throw new RuntimeException(
                    "Execute o comando --init informando o endereço de rede para conexão com a blockchain");
        }
    }

    public Client(String url) {
        this(url, null, null, null, null);
    }

    public Client(String networkURL, String contractUsersAddress, String contractAuthorityAddress,
            String contractFilesAddress, String contractKeysAddress) {
        fc = FileController.getInstance();
        ObjectNode clientData = (ObjectNode) fc.loadAsJSON(getClientDirectory(), "clientData.json");
        if (clientData != null && clientData.get("userID") != null) {
            loadUserData(clientData.get("userID").asText());
        }
        this.blockchain = new BlockchainConnection(networkURL, contractUsersAddress, contractAuthorityAddress,
                contractFilesAddress, contractKeysAddress);
        loadAttributes();
        gp = DCPABE.globalSetup(160);
        fc.writeToDir(fc.getDataDirectory(), "globalParameters.json", gp);
        clientData = (ObjectNode) fc.getMapper().createObjectNode();
        clientData.put("networkURL", networkURL);
        clientData.put("contractUsersAddress", contractUsersAddress);
        clientData.put("contractAuthorityAddress", contractAuthorityAddress);
        clientData.put("contractFilesAddress", contractFilesAddress);
        clientData.put("contractKeysAddress", contractKeysAddress);
        fc.writeToDir(getClientDirectory(), "clientData.json", clientData);
        this.server = new ServerConnection(SERVER_PORT);
    }

    public void changeUser(String userID) {
        ObjectNode clientData = fc.getMapper().createObjectNode();
        clientData.put("userID", userID);
        fc.writeToDir(getClientDirectory(), "clientData.json", clientData);
        loadUserData(userID);
    }

    public void createUser(String name, String email, String privateKey) {
        ECKey keys = this.blockchain.generateECKeys(privateKey);
        user = new User(name, email, keys);
        fc.writeToDir(fc.getUserDirectory(user), "user.json", user);
    }

    private String getClientDirectory() {
        return fc.getDataDirectory() + "client\\";
    }

    public void getAttributes(String authority, String[] attributes) {
        Map<String, PublicKey> keys = null;
        if (!hasPublicKeyOfAuthority(authority)) {
            publishedAttributes.put(authority, new HashMap<String, PublicKey>());
        }
        keys = blockchain.getABEPublicKeys(authority, attributes);
        if (keys != null) {
            for (String attr : attributes) {
                if (hasPublicKeyOfAuthority(authority, attr)) {
                    publishedAttributes.get(authority).replace(attr, keys.get(attr));
                } else {
                    publishedAttributes.get(authority).put(attr, keys.get(attr));
                }
            }
            String path = getClientDirectory() + "PublicKeys\\";
            fc.writeToDir(path, authority + ".json", keys);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCertifier(String name, String email, String privateKey) {
        ECKey keys = this.blockchain.generateECKeys(privateKey);
        certifier = new Certifier(name, email, keys);
        fc.writeToDir(fc.getUserDirectory(user), "Certifier.json", certifier);
    }

    public void createCertifier() {
        if (user != null) {
            certifier = new Certifier(user);
        } else {
            System.out.println("Crie um usuário ou informe nome e e-mail");
        }
        fc.writeToDir(fc.getUserDirectory(user), "Certifier.json", certifier);
    }

    public void publish(String content) {
        // TODO: create Message factory to build json objects
        String path = fc.getUserDirectory(user);
        ObjectNode obj;
        System.out.println("Client - publishing content: " + content);
        if (content.equals("user")) {
            obj = (ObjectNode) fc.loadAsJSON(path, "User.json");
            obj = removeFieldFromJSON(obj, "ECKeys.private", "personalABEKeys");
            blockchain.publishUser(obj);
        } else if (content.equals("certifier")) {
            obj = (ObjectNode) fc.loadAsJSON(path, "Certifier.json");
            obj = removeFieldFromJSON(obj, "ECKeys.private", "authorityKeys.authorityID", "authorityKeys.secretKeys");
            blockchain.publishAuthority(obj);
        } else if (content.equals("attributes")) {
            obj = (ObjectNode) fc.loadAsJSON(path, "authorityPublicKeys.json");
            obj.put("address", certifier.getAddress());
            blockchain.publishABEKeys(obj);
        } else if (content.equals(".")) {
            // TODO: publicar todos os arquivos prontos disponíveis
        } else {
            Recording r = user.getRecordingByFile(content);
            r.setTimestamp(System.currentTimeMillis());
            obj = fc.getMapper().convertValue(r, ObjectNode.class);
            obj.put("address", user.getAddress());
            obj.remove("filePath");
            r.setRecordingIndex(blockchain.publishData(user.getID(), obj));
            // TODO: modificar recording para obter informação da transação
            send(r.getFileName());
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

    public boolean hasPublicKeyOfAuthority(String authority) {
        if (publishedAttributes == null || !publishedAttributes.containsKey(authority)) {
            return false;
        }
        return true;
    }

    public boolean hasPublicKeyOfAuthority(String authority, String attribute) {
        boolean authorityExists = hasPublicKeyOfAuthority(authority);
        return authorityExists && publishedAttributes.get(authority).containsKey(attribute);
    }

    /**
     * Getters and Setters
     */
    public static String getDataPath() {
        return DATA_PATH;
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
        user.setRecordings(fc.readAsList(path, "recordings.json", Recording.class));
        PersonalKeysJSON ABEKeys = fc.readFromDir(path, "personalKeys.json", PersonalKeysJSON.class);
        if (ABEKeys != null) {
            user.setABEKeys(ABEKeys);
        }
        System.out.println("Client - user data loaded: " + userID);
    }

    public void encrypt(String file, String policy, String[] authorities) {
        Recording r = user.getRecordingByFile(file);
        if (r == null || r.hasFileChanged()) {
            PublicKeys pks = new PublicKeys();
            for (String auth : authorities) {
                pks.subscribeAuthority(publishedAttributes.get(auth));
            }
            AccessStructure as = AccessStructure.buildFromPolicy(policy);
            Message m = DCPABE.generateRandomMessage(gp);
            CiphertextJSON ct = new CiphertextJSON(DCPABE.encrypt(m, as, gp, pks));
            String path = fc.getUserDirectory(user);
            r = new Recording(path, file, ct);
            r.encryptFile(m);
            user.removeRecordByFileName(file);
            user.addRecording(r);
            fc.writeToDir(path, "recordings.json", user.getRecordings());
        } else {
            System.out.println("Client - Info: " + file + " - File already encrypted");
        }
    }

    // dec <username> <ciphertext> <resource file> <gpfile> <keyfile 1> <keyfile 2>
    public void decrypt(String file) {
        Recording r = user.getRecordingByFile(file);
        Message m = null;
        try {
            m = DCPABE.decrypt(r.getCiphertext(), user.getABEKeys(), gp);
        } catch (IllegalArgumentException e) {
            String msg = "Client - Could not decrypt the file %s. Attributes not Satisfying Policy Access.";
            System.out.println(String.format(msg, file));
            return;
        }
        r.decrypt(m);
    }

    public void send(String content) {
        Recording r = user.getRecordingByFile(content);
        if (r == null) {
            System.out.println("Unencrypted content. Aborting");
        } else if (r.getKey() == null || r.hasFileChanged()) {
            ObjectNode message = fc.getMapper().createObjectNode();
            message.put("name", user.getName());
            message.put("userID", user.getID());
            String key = server.reserveSpace(message);
            r.setDomain(server.getHost());
            r.setPath(server.getPath("file"));
            r.setPort(server.getPort());
            r.setKey(key);
            publish(content);
        } else {
            String userID = user.getID();
            List<byte[]> data = r.readData(FileMode.EncryptedFile);
            if (data != null) {
                System.out.println("Client - uploading file to server: " + content);
                server.sendFile(userID, content, data);
            }
        }
    }

    public void getRecordings(String userID, String[] recordings) {
        List<Recording> r = new ArrayList<Recording>();
        Recording oneRecord;
        for (String fileName : recordings) {
            oneRecord = blockchain.getRecording(userID, fileName);
            if (oneRecord != null) {
                oneRecord.setFilePath(fc.getUserDirectory(user));
                List<byte[]> data = server.getFile(oneRecord.getKey(), fileName);
                oneRecord.writeData(data, FileMode.EncryptedFile);
                r.add(oneRecord);
                user.removeRecordByFileName(fileName);
                System.out.println("Client - encrypted file received: " + fileName);
            } else {
                System.out.println(fileName + " not found in blockchain");
            }
        }
        user.addAllRecordings(r);
        fc.writeToDir(fc.getUserDirectory(user), "recordings.json", user.getRecordings());
    }

    public void checkAttributeRequests(String status) {
        ArrayNode requests = fc.getMapper().createArrayNode();
        if (certifier != null) {
            requests = this.blockchain.getAttributeRequestsForCertifier(certifier.getID(), status);
        } else if (user != null) {
            requests = this.blockchain.getAttributeRequestsForUser(user.getID(), status);
            if (requests.size() == 0) {
                System.out.println("No attributes requests with status: " + status);
                return;
            }
        } else {
            System.out.println("No user/certifier loaded in client");
            return;
        }
        System.out.println("Client - Attributes Requests with status: " + status);
        for (JsonNode r : requests) {
            String attr = r.get("attributes").toString();
            String msg = "\tRequest %s %s asking atributes: %s";
            if (certifier != null) {
                String userID = r.get("userID").asText();
                msg = String.format(msg, "from", userID, attr);
            } else {
                String auth = r.get("authority").asText();
                msg = String.format(msg, "to", auth, attr);
            }
            System.out.println(msg);
        }
        // TODO: update local requests
    }

    private Map<String, ArrayNode> syncAttributeRequestsCache(String authority, String address) {
        String path = fc.getUserDirectory(user);
        Map<String, ArrayNode> requestCache = fc.readAsMap(path, "attributeRequests.json", String.class,
                ArrayNode.class);
        this.blockchain.syncAttributeRequestCache(address, requestCache, authority);
        fc.writeToDir(path, "attributeRequests.json", requestCache);
        return requestCache;
    }

    private void saveAttributeRequestInCache(String authority, String address, ObjectNode request) {
        String path = fc.getUserDirectory(user);
        Map<String, ArrayNode> requestCache = fc.readAsMap(path, "attributeRequests.json", String.class,
                ArrayNode.class);
        requestCache.get(authority).add(request);
        fc.writeToDir(path, "attributeRequests.json", requestCache);
    }

    private List<Integer> hasRequestForAttributes(String authority, String address, String[] attributes) {
        List<Integer> alreadyAsked = new ArrayList<Integer>();
        Map<String, ArrayNode> requestCache = syncAttributeRequestsCache(authority, address);
        if (requestCache != null) {
            for (JsonNode r : requestCache.get(authority)) {
                String requestAttributes;
                try {
                    requestAttributes = fc.getMapper().writeValueAsString(r.get("attributes"));
                    int status = r.get("status").asInt();
                    IntStream.range(0, attributes.length).filter(i -> requestAttributes.contains(attributes[i]) && status == 0).forEach(i -> alreadyAsked.add(i));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if (alreadyAsked.size() > 0) {
                StringJoiner message = new StringJoiner(", ");
                alreadyAsked.forEach(i -> message.add(attributes[i]));
                System.out.println("Already asked attributes: " + message.toString() + ".");
            }
        }
        return alreadyAsked;
    }

    public void requestAttribute(String authority, String[] attributes) {
        String[] temp = authority.split("-");
        authority = temp[temp.length - 1];
        List<Integer> alreadyOwned = new ArrayList<Integer>();
        IntStream.range(0, attributes.length).filter(i -> user.getABEKeys().getKey(attributes[i]) != null).forEach(i -> alreadyOwned.add(i));
        if (alreadyOwned.size() > 0) {
            StringJoiner message = new StringJoiner(", ");
            alreadyOwned.forEach(i -> message.add(attributes[i]));
            System.out.println("Already owned attributes: " + message.toString() + ".");
        }
        List<Integer> alreadyAsked = hasRequestForAttributes(authority, user.getAddress(), attributes);
        List<String> requests = new ArrayList<String>();
        IntStream.range(0, attributes.length).filter(i -> !alreadyAsked.contains(i) && !alreadyOwned.contains(i)).forEach(i -> requests.add(attributes[i]));
        if (requests.size() > 0) {
            ObjectNode request = this.blockchain.publishAttributeRequest(authority, user.getAddress(), requests);
            saveAttributeRequestInCache(authority, user.getAddress(), request);
        }
    }

    public void yieldAttribute(String userID, String[] attributes) {
        String path = fc.getUserDirectory(user);
        ArrayList<PersonalKey> pks = new ArrayList<PersonalKey>();
        Map<String, SecretKey> skeys = null;
        try {
            skeys = Utility.readSecretKeys(path + "authoritySecretKey");
            for (String attr : attributes) {
                SecretKey sk = skeys.get(attr);
                if (null == sk) {
                    System.err.println("Attribute not handled");
                    blockchain.publishAttributeRequestUpdate(certifier.getID(), userID, attributes, "failed");
                    return;
                }
                pks.add(DCPABE.keyGen(userID, attr, sk, gp));
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        fc.writeToDir(path, userID + "-pks.json", pks);
        blockchain.publishAttributeRequestUpdate(certifier.getID(), userID, attributes, "ok");
	}

	public void sendAttributes(String userID) {
        // TODO: elliptic encrypting of Personal Keys using secp256k-1 curve (Bitcoin key curve)
        // see: http://bit.ly/2RWWes1 (Java) ,http://bit.ly/2RK0zyk (C#, but may be util)
        String path = fc.getUserDirectory(user);

        ArrayNode pks = (ArrayNode) fc.loadAsJSON(path, userID + "-pks.json");
        server.sendKeys(userID, pks);
    }

	public void getPersonalKeys() {
        List<PersonalKey> pks = server.getPersonalKeys(user.getID());
        if (pks != null) {
            int size = user.getABEKeys().size();
            pks.stream()
                .filter(pk -> user.getABEKeys().getKey(pk.getAttribute()) == null)
                .forEach(pk -> user.getABEKeys().addKey(pk));
            int newSize = user.getABEKeys().size();
            if (size != newSize) {
                fc.writeToDir(fc.getUserDirectory(user), "personalKeys.json", user.getABEKeys());
            } else {
                System.out.println("All keys found in server already had local copies.");
            }
        } else {
            System.out.println("No personal ABE Keys available for download.");
        }
	}

	public void deploy() {
        this.blockchain.deployContracts(user.getCredentials());
	}
}
