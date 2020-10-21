package com.brunoarruda.hyperdcpabe;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import com.brunoarruda.hyperdcpabe.blockchain.BlockchainConnection;
import com.brunoarruda.hyperdcpabe.Recording.FileMode;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.brunoarruda.hyperdcpabe.monitor.ExecutionProfiler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.Ciphertext;
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

    private static final String DATA_PATH = "data\\";
    private static final String CLIENT_PATH = DATA_PATH + "client\\";

    private final Logger log = LoggerFactory.getLogger(Client.class);
    private final FileController fc = FileController.getInstance().configure(DATA_PATH, false);
    private final ExecutionProfiler profiler = ExecutionProfiler.getInstance();

    private final int SERVER_PORT = 8080;
    private final Map<String, String> contractAddress;

    private GlobalParameters gp;
    private User user;
    private Certifier certifier;
    private BlockchainConnection blockchain;
    private ServerConnection server;
    private Map<String, Map<String, PublicKey>> publishedAttributes;

    public enum RequestStatus {
        PENDING("pending", 0), OK("ok", 1), REJECTED("rejected", 2);

        private final String label;
        private final int value;
        private static final Map<String, RequestStatus> labels = new HashMap<>();
        private static final Map<Integer, RequestStatus> values = new HashMap<>();

        RequestStatus(String label, int value) {
            this.label = label;
            this.value = value;
        }

        static {
            for (RequestStatus status : RequestStatus.values()) {
                labels.put(status.label, status);
                values.put(status.value, status);
            }
        }

        public static RequestStatus valueOf(int value) {
            return (RequestStatus) values.get(value);
        }

        public static RequestStatus labelOf(String label) {
            return (RequestStatus) labels.get(label.toLowerCase());
        }

        public int getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return label.toUpperCase();
        }
    }

    public Client() {
        profiler.start(this.getClass(), "constructor");
        this.server = new ServerConnection(SERVER_PORT);
        ObjectNode clientData = (ObjectNode) fc.loadAsJSON(CLIENT_PATH, "clientData.json");
        if (clientData != null) {
            gp = fc.readFromDir(CLIENT_PATH, "clientData.json", "globalParameters", GlobalParameters.class);
            contractAddress = fc.readAsMap(CLIENT_PATH, "clientData.json", "contractAddress", String.class, String.class);
            String networkURL = clientData.get("networkURL").asText();
            this.blockchain = new BlockchainConnection(networkURL, contractAddress);
            if (!clientData.get("currentUserID").asText().equals("")) {
                loadUserData(clientData.get("currentUserID").asText());
                this.blockchain.loadContracts(user.getCredentials());
            }
            loadAttributes();
        } else {
            throw new RuntimeException(
                    "Execute o comando --init informando o endereço de rede para a Blockchain e o endereço do contrato Root.");
        }
        log.debug("Módulo cliente inicializado.");
        profiler.end();
    }

    public Client(String networkURL, String adminName, String adminEmail, String adminPrivateKey) {
        ExecutionProfiler.getInstance();
        profiler.start(this.getClass(), "constructor");
        this.server = new ServerConnection(SERVER_PORT);
        this.blockchain = new BlockchainConnection(networkURL);
        profiler.start(DCPABE.class, "globalSetup");
        gp = DCPABE.globalSetup(160);
        profiler.end();
        contractAddress = deployContracts(adminName, adminEmail, adminPrivateKey);
        ObjectNode address = fc.getMapper().createObjectNode();
        contractAddress.forEach((key, val) -> address.put(key, val));
        ObjectNode gpJSON = fc.getMapper().convertValue(gp, ObjectNode.class);
        ObjectNode clientData = fc.getMapper().createObjectNode();
        clientData.put("currentUserID", "");
        clientData.put("networkURL", networkURL);
        clientData.set("globalParameters", gpJSON);
        clientData.set("contractAddress", address);
        fc.writeToDir(CLIENT_PATH, "clientData.json", clientData);
        log.debug("Módulo cliente configurado.");
        profiler.end();
    }

    public void setActiveUser(String userID) {
        profiler.start(this.getClass(), "setActiveUser");
        ObjectNode clientData = (ObjectNode) fc.loadAsJSON(CLIENT_PATH, "clientData.json");
        clientData.put("currentUserID", userID);
        fc.writeToDir(CLIENT_PATH, "clientData.json", clientData);
        profiler.end();
    }

    // NOTE: createUser generates a pubkey from privateKey. It's only for this that I'm using Ethereum Library. Maybe this class may migrate the data to the Web3j ECKeyPair.
    public void createUser(String name, String email, String privateKey) {
        profiler.start(this.getClass(), "createUser");
        ECKey keys = this.blockchain.generateECKeys(privateKey);
        user = new User(name, email, keys);
        fc.writeToDir(CLIENT_PATH + user.getID(), "user.json", user);
        setActiveUser(user.getID());
        this.blockchain.loadContracts(user.getCredentials());
        profiler.end();
    }

    public Map<String, String> deployContracts(String name, String email, String privateKey) {
        profiler.start(this.getClass(), "deployContracts");
        ECKey keys = this.blockchain.generateECKeys(privateKey);
        user = new User(name, email, keys);
        Map<String, String> contractAddress = this.blockchain.deployContracts(user.getCredentials());
        profiler.end();
        return contractAddress;
    }

    public static String getClientPath() {
        return CLIENT_PATH;
    }

    public void getAttributes(String authority, String[] attributes) {
        profiler.start(this.getClass(), "getAttributes");
        if (!hasPublicKeyOfAuthority(authority)) {
            publishedAttributes.put(authority, new HashMap<String, PublicKey>());
        }
        Map<String, PublicKey> keys = null;
        keys = blockchain.getABEPublicKeys(authority, attributes);
        if (keys != null) {
            for (String attr : attributes) {
                if (hasPublicKeyOfAuthority(authority, attr)) {
                    publishedAttributes.get(authority).replace(attr, keys.get(attr));
                } else {
                    publishedAttributes.get(authority).put(attr, keys.get(attr));
                }
            }
            ObjectNode keysJSON = fc.getMapper().createObjectNode();
            keysJSON.set("certifiers", fc.getMapper().convertValue(publishedAttributes.keySet(), ArrayNode.class));
            keysJSON.set("publicKeys", fc.getMapper().convertValue(publishedAttributes, ObjectNode.class));
            fc.writeToDir(CLIENT_PATH, "publicKeys.json", keysJSON);
        }
        profiler.end();
    }

    public void createABEKeys(String[] attributes) {
        profiler.start(this.getClass(), "createABEKeys");
        String name = certifier.getPrivateECKey();
        profiler.start(DCPABE.class, "authoritySetup");
        AuthorityKeys ak = DCPABE.authoritySetup(name, gp, attributes);
        profiler.end();
        certifier.setAuthorityABEKeys(ak);
        fc.writeToDir(CLIENT_PATH + certifier.getID(), "authorityPublicKeys.json", ak.getPublicKeys());
        fc.writeToDir(CLIENT_PATH + certifier.getID(), "Certifier.json", certifier);
        try {
            Utility.writeSecretKeys(CLIENT_PATH + certifier.getID() + "\\authoritySecretKeys", ak.getSecretKeys());
        } catch (IOException e) {
            log.error("Não foi salver em disco as chaves secretas dos atributos", e);
        }
        profiler.end();
    }

    public void createCertifier(String name, String email, String privateKey) {
        profiler.start(this.getClass(), "createCertifier");
        ECKey keys = this.blockchain.generateECKeys(privateKey);
        certifier = new Certifier(name, email, keys);
        fc.writeToDir(CLIENT_PATH + user.getID(), "Certifier.json", certifier);
        setActiveUser(user.getID());
        profiler.end();
    }

    public void createCertifier() {
        profiler.start(this.getClass(), "createCertifier");
        if (user != null) {
            setActiveUser(user.getID());
            certifier = new Certifier(user);
        } else {
            log.error("Crie um usuário ou informe nome e e-mail.");
        }
        fc.writeToDir(CLIENT_PATH + user.getID(), "Certifier.json", certifier);
        profiler.end();
    }

    public void publish(String content) {
        // TODO: create Message factory to build json objects
        profiler.start(this.getClass(), "publish");
        String path = CLIENT_PATH + user.getID();
        ObjectNode obj;
        log.info("Publicando conteúdo." + content);
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
            profiler.end();
            throw new RuntimeException("Option not implemented.");
        } else {
            Recording r = user.getRecordingByFile(content);
            r.setTimestamp(System.currentTimeMillis());
            obj = fc.getMapper().convertValue(r, ObjectNode.class);
            obj.put("address", user.getAddress());
            obj.remove("filePath");
            int resultIndex = blockchain.publishData(user.getID(), obj);
            if (resultIndex != -1) {
                r.setRecordingIndex(resultIndex);
                send(r.getFileName());
            } else {
                log.error("Publicação do arquivo {} falhou.", content);
            }
        }
        profiler.end();
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
        profiler.start(this.getClass(), "loadAttributes");
        publishedAttributes = new Hashtable<String, Map<String, PublicKey>>();
        List<String> certifiers = fc.readAsList(CLIENT_PATH, "publicKeys.json", "certifiers", String.class);
        for (String gid : certifiers) {
            publishedAttributes.put(gid, fc.readAsMap(CLIENT_PATH, "publicKeys.json", "publicKeys." + gid, String.class, PublicKey.class));
        }
        profiler.end();
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

    public void loadUserData(String userID) {
        profiler.start(this.getClass(), "loadUserData");
        String path = CLIENT_PATH + userID;
        user = fc.readFromDir(path, "user.json", User.class);
        certifier = fc.readFromDir(path, "Certifier.json", Certifier.class);
        user.setRecordings(fc.readAsList(path, "recordings.json", Recording.class));
        PersonalKeysJSON ABEKeys = fc.readFromDir(path, "personalKeys.json", PersonalKeysJSON.class);
        if (ABEKeys != null) {
            user.setABEKeys(ABEKeys);
        }
        log.info("Usuário logado: " + userID);
        profiler.end();
    }

    public void encrypt(String file, String policy, String[] authorities) {
        profiler.start(this.getClass(), "encrypt");
        Recording r = user.getRecordingByFile(file);
        if (r == null || r.hasFileChanged()) {
            PublicKeys pks = new PublicKeys();
            for (String auth : authorities) {
                pks.subscribeAuthority(publishedAttributes.get(auth));
            }
            AccessStructure as = AccessStructure.buildFromPolicy(policy);
            profiler.start(DCPABE.class, "generateRandomMessage");
            Message m = DCPABE.generateRandomMessage(gp);
            profiler.end();
            profiler.start(DCPABE.class, "encrypt");
            Ciphertext ct_ = DCPABE.encrypt(m, as, gp, pks);
            profiler.end();
            CiphertextJSON ct = new CiphertextJSON(ct_);
            String path = CLIENT_PATH + user.getID();
            r = new Recording(path, file, ct);
            r.encrypt(m);
            user.removeRecordByFileName(file);
            user.addRecording(r);
            fc.writeToDir(path, "recordings.json", user.getRecordings());
        } else {
            log.error("Arquivo já criptografado: " + file);
        }
        profiler.end();
    }

    // dec <username> <ciphertext> <resource file> <gpfile> <keyfile 1> <keyfile 2>
    public void decrypt(String file) {
        profiler.start(this.getClass(), "decrypt");
        Recording r = user.getRecordingByFile(file);
        Message m = null;
        profiler.start(DCPABE.class, "decrypt");
        try {
            m = DCPABE.decrypt(r.getCiphertext(), user.getABEKeys(), gp);
            profiler.end();
        } catch (IllegalArgumentException e) {
            String msg = "Não foi possível descriptografar {}. Atributos não satisfazem a política de acesso.";
            log.error(msg, file, e);
            profiler.end();
            return;
        }
        r.decrypt(m);
        profiler.end();
    }

    public void send(String content) {
        profiler.start(this.getClass(), "send");
        Recording r = user.getRecordingByFile(content);
        if (r == null) {
            log.error("Conteúdo não criptografado. Upload cancelado.");
        } else if (r.getKey() == null || r.hasFileChanged()) {
            ObjectNode message = fc.getMapper().createObjectNode();
            message.put("name", user.getName());
            message.put("userID", user.getID());
            String key = server.reserveSpace(message);
            r.setDomain(server.getHost());
            r.setServerPath(server.getPath("file"));
            r.setPort(server.getPort());
            r.setKey(key);
            publish(content);
        } else {
            String userID = user.getID();
            List<byte[]> data = r.readData(FileMode.EncryptedFile);
            if (data != null) {
                log.info("Enviando arquivo {} ao servidor", content);
                server.sendFile(userID, content, data);
                String path = CLIENT_PATH + user.getID();
                fc.writeToDir(path, "recordings.json", user.getRecordings());            }
        }
        profiler.end();
    }

    public void getRecordings(String userID, String[] recordings) {
        profiler.start(this.getClass(), "getRecordings");
        String address = userID.split("-")[1];
        List<Recording> r = new ArrayList<Recording>();
        Recording oneRecord;
        for (String fileName : recordings) {
            oneRecord = blockchain.getRecording(address, fileName);
            if (oneRecord != null) {
                oneRecord.setFolderPath(CLIENT_PATH + user.getID());
                List<byte[]> data = server.getFile(oneRecord.getKey(), fileName);
                oneRecord.writeData(data, FileMode.EncryptedFile);
                r.add(oneRecord);
                user.removeRecordByFileName(fileName);
                log.info("Arquivo criptografado recebido: " + fileName);
            } else {
                log.error("Aquivo não encontrado na Blockchain: " + fileName);
            }
        }
        user.addAllRecordings(r);
        fc.writeToDir(CLIENT_PATH + user.getID(), "recordings.json", user.getRecordings());
        profiler.end();
    }

    public void checkAttributeRequests(RequestStatus status) {
        profiler.start(this.getClass(), "checkAttributeRequests");
        if (certifier == null && user == null) {
            log.error("Nenhum usuário carregado no sistema.");
            profiler.end();
            return;
        }
        if (certifier != null) {
            Map<String, ObjectNode> requests = null;
            log.info("Requisições de atributo com o status: " + status + "\n");
            requests = syncPendingAttributeRequestsCache(certifier.getAddress(), requests);
            for (String user : requests.keySet()) {
                StringBuilder output = new StringBuilder(5);
                output.append(String.format("User %s:\n", user.substring(0, 6)));
                for (JsonNode node : requests.get(user).withArray("requests")) {
                    RequestStatus rs = RequestStatus.valueOf(node.get("status").asInt());
                    String base_str = "\trequisição #%d - timestamp %s - %s";
                    if (status.equals(rs)) {
                        output.append(String.format(base_str, node.get("index").asInt(),
                        node.get("timestamp").asText(), node.get("attributes").toString()));
                    }
                    log.info(output.toString());
                }
            }
        } else if (user != null) {
            Map<String, ArrayNode> requests = loadAttributeRequestsCache();
            /* FIX: this disjunction avoids an authority to check for attributes asked to
             * another authorities
             */
            StringBuilder output = new StringBuilder(6);
            output.append("Attributes Requests with status: " + status + "\n");
            for (String authority : requests.keySet()) {
                syncAttributeRequestsCache(authority, user.getAddress(), requests);
                output.append("Authority " + authority.substring(0, 6) + ":\n");
                for (JsonNode node : requests.get(authority)) {
                    RequestStatus rs = RequestStatus.valueOf(node.get("status").asInt());
                    String base_str = "\trequisição #%d - timestamp %s - %s";
                    if (status.equals(rs)) {
                        output.append(String.format(base_str, node.get("index").asInt(),
                        node.get("timestamp").asText(), node.get("attributes").toString()));
                    }
                    log.info(output.toString());
                }
            }
        }
        profiler.end();
    }

    private Map<String, ArrayNode> loadAttributeRequestsCache() {
        profiler.start(this.getClass(), "loadAttributeRequestsCache");
        String path = CLIENT_PATH + user.getID();
        Map<String, ArrayNode> requestCache = fc.readAsMap(path, "attributeRequests.json", String.class,
                ArrayNode.class);
        profiler.end();
        return requestCache;
    }

    private Map<String, ArrayNode> syncAttributeRequestsCache(String authority, String address,
            Map<String, ArrayNode> requestCache) {
        profiler.start(this.getClass(), "syncAttributeRequestsCache");
        if (requestCache == null) {
            requestCache = loadAttributeRequestsCache();
        }
        String path = CLIENT_PATH + user.getID();
        this.blockchain.syncAttributeRequestCache(address, requestCache, authority);
        fc.writeToDir(path, "attributeRequests.json", requestCache);
        profiler.end();
        return requestCache;
    }

    private Map<String, ObjectNode> loadPendingAttributeRequestsCache() {
        profiler.start(this.getClass(), "loadPendingAttributeRequestsCache");
        String path = CLIENT_PATH + user.getID();
        Map<String, ObjectNode> requestCache = fc.readAsMap(path, "pendingAttributeRequests.json", String.class,
                ObjectNode.class);
        profiler.end();
        return requestCache;
    }

    private Map<String, ObjectNode> syncPendingAttributeRequestsCache(String authority,
            Map<String, ObjectNode> pendingRequestCache) {
        profiler.start(this.getClass(), "syncPendingAttributeRequestsCache");
        if (pendingRequestCache == null) {
            pendingRequestCache = loadPendingAttributeRequestsCache();
        }
        String path = CLIENT_PATH + user.getID();
        this.blockchain.syncPendingAttributeRequests(authority, pendingRequestCache);
        fc.writeToDir(path, "pendingAttributeRequests.json", pendingRequestCache);
        profiler.end();
        return pendingRequestCache;
    }

    private void saveAttributeRequestInCache(String authority, String address, ObjectNode request) {
        profiler.start(this.getClass(), "saveAttributeRequestInCache");
        String path = CLIENT_PATH + user.getID();
        Map<String, ArrayNode> requestCache = fc.readAsMap(path, "attributeRequests.json", String.class,
                ArrayNode.class);
        requestCache.get(authority).add(request);
        fc.writeToDir(path, "attributeRequests.json", requestCache);
        profiler.end();
    }

    private List<Integer> hasRequestForAttributes(String authority, String address, String[] attributes) {
        profiler.start(this.getClass(), "hasRequestForAttributes");
        List<Integer> alreadyAsked = new ArrayList<Integer>();
        Map<String, ArrayNode> requestCache = null;
        requestCache = syncAttributeRequestsCache(authority, address, requestCache);
        if (requestCache != null) {
            for (JsonNode r : requestCache.get(authority)) {
                String requestAttributes;
                try {
                    requestAttributes = fc.getMapper().writeValueAsString(r.get("attributes"));
                    int status = r.get("status").asInt();
                    IntStream.range(0, attributes.length)
                            .filter(i -> requestAttributes.contains(attributes[i]) && status == 0)
                            .forEach(i -> alreadyAsked.add(i));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if (alreadyAsked.size() > 0) {
                StringJoiner message = new StringJoiner(", ");
                alreadyAsked.forEach(i -> message.add(attributes[i]));
                log.info("Already asked attributes: " + message.toString() + ".");
            }
        }
        profiler.end();
        return alreadyAsked;
    }

    public void requestAttribute(String authority, String[] attributes) {
        profiler.start(this.getClass(), "requestAttribute");
        String[] temp = authority.split("-");
        authority = temp[temp.length - 1];
        List<Integer> alreadyOwned = new ArrayList<Integer>();
        IntStream.range(0, attributes.length).filter(i -> user.getABEKeys().getKey(attributes[i]) != null)
                .forEach(i -> alreadyOwned.add(i));
        if (alreadyOwned.size() > 0) {
            StringJoiner message = new StringJoiner(", ");
            alreadyOwned.forEach(i -> message.add(attributes[i]));
            log.info("Already owned attributes: " + message.toString() + ".");
        }
        List<Integer> alreadyAsked = hasRequestForAttributes(authority, user.getAddress(), attributes);
        List<String> requests = new ArrayList<String>();
        IntStream.range(0, attributes.length).filter(i -> !alreadyAsked.contains(i) && !alreadyOwned.contains(i))
                .forEach(i -> requests.add(attributes[i]));
        if (requests.size() > 0) {
            ObjectNode request = this.blockchain.publishAttributeRequest(authority, user.getAddress(), requests);
            saveAttributeRequestInCache(authority, user.getAddress(), request);
        }
        profiler.end();
    }

    public void yieldAttribute(String userID, int requestIndex) {
        profiler.start(this.getClass(), "yieldAttribute");
        String address = userID.split("-")[1];
        String path = CLIENT_PATH + user.getID();
        List<PersonalKey> pks = new ArrayList<PersonalKey>();
        Map<String, SecretKey> skeys = null;
        try {
            skeys = Utility.readSecretKeys(path + "\\authoritySecretKeys");
            Map<String, ObjectNode> pendingRequests = loadPendingAttributeRequestsCache();
            // UGLY: applying lowercase to address because it was recorded that way as a key
            ObjectNode requesterData = pendingRequests.get(address.toLowerCase());
            if (requesterData == null) {
                log.error("Ainda não houver requisições de atributos feitas pelo usuário {}.", address.substring(0, 6));
                profiler.end();
                return;
            }
            JsonNode request = null;
            for (JsonNode r : requesterData.get("requests")) {
                if (r.get("index").asInt() == requestIndex) {
                    request = r;
                    break;
                }
            }
            if (request == null) {
                log.error("A requisição {} já foi processada ou não existe.", requestIndex);
                profiler.end();
                return;
            }
            BigInteger pendingRequesterIndex = BigInteger.valueOf(requesterData.get("index").asInt());
            BigInteger pendingRequestIndex = BigInteger.valueOf(request.get("pendingIndex").asInt());
            Map<String, int[]> changes = null;
            boolean keysWereGenerated = false;
            String userName = userID.split("-")[0];
            for (JsonNode attr_ : request.get("attributes")) {
                boolean alreadyGenerated = false;
                String attr = attr_.asText();
                // FIX: apparently pks is always empty. This behavior indicates some logic flaw
                for (PersonalKey pk : pks) {
                    if (pk.getAttribute().equals(attr)) {
                        log.info("Atributo {} já foi concedido ao usuário {}.", attr, userName);
                        alreadyGenerated = true;
                        break;
                    }
                }
                if (!alreadyGenerated) {
                    keysWereGenerated = true;
                    SecretKey sk = skeys.get(attr);
                    if (null == sk) {
                        log.info("O certificador não possui a chave privada referente ao atributo {}. Rejeitando o pedido.", attr);
                        changes = blockchain.publishAttributeRequestUpdate(certifier.getAddress(), address,
                                pendingRequesterIndex, pendingRequestIndex, RequestStatus.REJECTED);
                        profiler.end();
                        return;
                    }
                    /*
                     * HACK: personal key are generated from checksummed wallet address, but are
                     * stored lowercase in serialization. Partial fix is to lowercase address, but
                     * ideal fix is to generate and store checksummed wallet
                     */
                    profiler.start(DCPABE.class, "keyGen");
                    PersonalKey pk = DCPABE.keyGen(userName + "-" +  address.toLowerCase(), attr, sk, gp);
                    profiler.end();
                    pks.add(pk);
                }
            }
            if (keysWereGenerated) {
                fc.writeToDir(path, userID + "-pks.json", pks);
            }
            changes = blockchain.publishAttributeRequestUpdate(certifier.getAddress(), address, pendingRequesterIndex,
                    pendingRequestIndex, RequestStatus.OK);
            updateCertifierCache(pendingRequests, changes, address.toLowerCase());
        } catch (ClassNotFoundException | IOException e) {
            String base_str = "Houve um problema durante o processamento da requisição {} feita pelo usuário {} à autoridade {}.";
            log.error(base_str, requestIndex, address.substring(0, 6), certifier.getAddress(), e);
        }
        profiler.end();
    }

    private void updateCertifierCache(Map<String, ObjectNode> cache, Map<String, int[]> changes, String address) {
        profiler.start(this.getClass(), "updateCertifierCache");
        if (changes.get("requester") != null) {
            /*
             * if an index swap ocurred for requester, it means the original requester was
             * not the last one added to pending list, but he was removed from pending list
             * and there are no requests from them to process
             */
            int oldRequesterIndex = changes.get("requester")[0];
            for (ObjectNode requester : cache.values()) {
                if (requester.get("index").asInt() == oldRequesterIndex) {
                    requester.put("index", changes.get("requester")[1]);
                    break;
                }
            }
        } else if (changes.get("request") != null) {
            /*
            * if an index swap ocurred for request, it means that its requesters remains in
            * the pending list, but the processed request was not the last one in his list
            */
            int oldRequestIndex = changes.get("request")[0];
            for (JsonNode request : cache.get(address).withArray("requests")) {
                if (request.get("index").asInt() == oldRequestIndex) {
                    ((ObjectNode) request).put("index", changes.get("request")[1]);
                    break;
                }
            }
        } else {
            /*
            * if no swap ocurred in buffer, it means that the requester does not exist
            * anymore on pending list and he was the last in the list stored in the
            * blockchain
            */
            cache.remove(address);
        }
        String path = CLIENT_PATH + user.getID();
        fc.writeToDir(path, "pendingAttributeRequests.json", cache);
        profiler.end();
    }

    public void sendAttributes(String userID) {
        profiler.start(this.getClass(), "sendAttributes");
        /* TODO: elliptic encrypting of Personal Keys using secp256k-1 curve (Bitcoin
         * key curve) see: http://bit.ly/2RWWes1 (Java) ,http://bit.ly/2RK0zyk (C#, but
         * may be util)
         */
        String path = CLIENT_PATH + user.getID();

        ArrayNode pks = (ArrayNode) fc.loadAsJSON(path, userID + "-pks.json");
        server.sendKeys(userID, pks);
        profiler.end();
    }

    public void getPersonalKeys() {
        profiler.start(this.getClass(), "getPersonalKeys");
        List<PersonalKey> pks = server.getPersonalKeys(user.getID());
        if (pks != null) {
            int size = user.getABEKeys().size();
            pks.stream()
                .filter(pk -> user.getABEKeys().getKey(pk.getAttribute()) == null)
                .forEach(pk -> user.getABEKeys().addKey(pk));
            int newSize = user.getABEKeys().size();
            if (size != newSize) {
                fc.writeToDir(CLIENT_PATH + user.getID(), "personalKeys.json", user.getABEKeys());
            } else {
                log.info("All keys found in server already had local copies.");
            }
        } else {
            log.warn("No personal ABE Keys available for download.");
        }
        profiler.end();
    }
}
