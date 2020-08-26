package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.CiphertextJSON;
import com.brunoarruda.hyperdcpabe.Recording;
import com.brunoarruda.hyperdcpabe.Client.RequestStatus;
import com.brunoarruda.hyperdcpabe.blockchain.SmartDCPABERequests.PendingRequestIndexChangedEventResponse;
import com.brunoarruda.hyperdcpabe.blockchain.SmartDCPABERequests.PendingRequesterIndexChangedEventResponse;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

public class BlockchainConnection {

    // TODO: send message to logger instead of System.out
    private static final Logger log = LoggerFactory.getLogger(BlockchainConnection.class);

    private String dataPath = "blockchain";
    private FileController fc;

    static private final byte[] seed = "Honk Honk".getBytes();
    static private final SecureRandom random = new SecureRandom(seed);
    private final String networkURL;

    private SmartDCPABERoot scRoot;
    private SmartDCPABEAuthority scAuthority;
    private SmartDCPABEFiles scFiles;
    private SmartDCPABEKeys scKeys;
    private SmartDCPABERequests scRequests;
    private SmartDCPABEUsers scUsers;
    private SmartDCPABEUtility scUtility;
    private Map<String, String> contractAddress;
    private final Web3j web3j;
    private ContractGasProvider dgp;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public BlockchainConnection(String networkURL) {
        this(networkURL, null);
    }

    // TODO: refactor URL as a POM field or command line/file config
    // POM field seems better, as it would allow different value for deploy/test
    // cycles
    public BlockchainConnection(String networkURL, Map<String, String> contractAddress) {
        this.networkURL = networkURL;
        web3j = Web3j.build(new HttpService(networkURL));
        fc = FileController.getInstance();
        dgp = new DefaultGasProvider();
        if (contractAddress == null) {
            this.contractAddress = new HashMap<String, String>();
        } else {
            this.contractAddress = contractAddress;
        }
	}

	public Map<String, String> deployContracts(Credentials credentials) {
        try {
            scRoot = SmartDCPABERoot.deploy(web3j, credentials, dgp).send();
            String rootAddress = scRoot.getContractAddress();

            scAuthority = SmartDCPABEAuthority.deploy(web3j, credentials, dgp, rootAddress).send();
            scFiles = SmartDCPABEFiles.deploy(web3j, credentials, dgp, rootAddress).send();
            scKeys = SmartDCPABEKeys.deploy(web3j, credentials, dgp, rootAddress).send();
            scRequests = SmartDCPABERequests.deploy(web3j, credentials, dgp, rootAddress).send();
            scUsers = SmartDCPABEUsers.deploy(web3j, credentials, dgp, rootAddress).send();
            scUtility = SmartDCPABEUtility.deploy(web3j, credentials, dgp, rootAddress).send();

            contractAddress.put("Root", rootAddress);
            contractAddress.put("Authority", scAuthority.getContractAddress());
            contractAddress.put("Files", scFiles.getContractAddress());
            contractAddress.put("Keys", scKeys.getContractAddress());
            contractAddress.put("Requests", scRequests.getContractAddress());
            contractAddress.put("Users", scUsers.getContractAddress());
            contractAddress.put("Utility", scUtility.getContractAddress());

            List<String> addressList = new ArrayList<String>();
            List<BigInteger> indexes = new ArrayList<BigInteger>();
            String[] keys = {"Authority", "Files", "Keys", "Requests", "Users", "Utility"};
            for (int i = 0; i < keys.length; i++) {
                addressList.add(contractAddress.get(keys[i]));
                indexes.add(BigInteger.valueOf(i));
            }
            scRoot.setAllContracts(indexes, addressList).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractAddress;
    }

    public BlockchainConnection loadContracts(Credentials credentials) {
        // TODO: check local addresses against the addresses returned by getAddresses() function from scRoot
        String address = contractAddress.get("Authority");
        scAuthority = SmartDCPABEAuthority.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Files");
        scFiles = SmartDCPABEFiles.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Keys");
        scKeys = SmartDCPABEKeys.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Requests");
        scRequests = SmartDCPABERequests.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Users");
        scUsers = SmartDCPABEUsers.load(address, web3j, credentials, dgp);
        return this;
    }

    public ECKey generateECKeys(String privateKey) {
        ECKey keys = null;
        if (privateKey != null) {
            keys = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        } else {
            keys = new ECKey(random);
        }
        return keys;
    }
    public Map<String, PublicKey> getABEPublicKeys(String authority, String[] attributes) {
        // TODO: alternar isso para um JSON
        String authName = authority.split("-")[0];
        String address = authority.split("-")[1];
        try {
            Tuple4<String, String, String, BigInteger> certifier = scAuthority.getCertifier(address).send();
            if (certifier.getValue4().equals(BigInteger.ZERO)) {
                System.out.println("A autoridade " + authName + " não publicou nenhum atributo.");
                return null;
            } else {
                Map<String, PublicKey> keys = new HashMap<String, PublicKey>();
                for (String attr : attributes) {
                    Tuple3<String, byte[], byte[]> keyData = scKeys.getPublicKey(address, attr).send();
                    keys.put(attr, new PublicKey(keyData.getValue2(), keyData.getValue3()));
                }
                return keys;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public int publishData(String userID, ObjectNode obj) {
        String domain = obj.get("domain").asText();
        String serverPath = obj.get("serverPath").asText();
        BigInteger port = obj.get("port").bigIntegerValue();
        BigInteger serverID = null;
        try {
            serverID = scFiles.getServerID(domain).send();
            if (serverID.longValue() == -1) {
                scFiles.addServer(domain, serverPath, port).send();
                serverID = scFiles.getServerID(domain).send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String address = obj.get("address").asText();
        BigInteger timestamp = obj.get("timestamp").bigIntegerValue();
        String fileName = obj.get("fileName").asText();
        int numRecording = -1;
        String policy = obj.get("ciphertext").get("accessStructure").get("policy").asText();
        /* BUG: decoded binary format becomes corrupt in blockchain, changing c0 to string binary format.
         * The problem could be in negative byte value, as string binary format only have positive bytes
         */

        byte[] c0 = obj.get("ciphertext").get("c0").asText().getBytes();
        byte[] c1 = obj.get("ciphertext").get("c1").toString().getBytes();
        byte[] c2 = obj.get("ciphertext").get("c2").toString().getBytes();
        byte[] c3 = obj.get("ciphertext").get("c3").toString().getBytes();
        try {
            BigInteger numRecording_ = scFiles.getFileCounting(address).send();
            byte[] key = obj.get("key").binaryValue();
            byte[] hash = obj.get("hash").binaryValue();
            scFiles.addRecording(address, fileName, serverID, key, hash, timestamp).send();
            scFiles.addRecordingCiphertext(address, fileName, policy, c0, c1, c2, c3).send();
            System.out.println("Blockchain - data published: " + fileName);
            numRecording = numRecording_.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numRecording;
    }

    public void publishABEKeys(ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo
        // TODO: checar existência de certificador com o endereço fornecido
        String address = obj.remove("address").asText();
        Iterator<String> it = obj.fieldNames();
        while (it.hasNext()) {
            String attrName = it.next();
            JsonNode attrNode = obj.get(attrName);
            byte[] eg1g1ai = Base64.getDecoder().decode(attrNode.get("eg1g1ai").asText());
            byte[] g1yi = Base64.getDecoder().decode(attrNode.get("g1yi").asText());
            if (eg1g1ai.length < 97 || eg1g1ai.length > 127) {
                throw new RuntimeException("Key error: eg1g1ai does not fit in four sized words");
            }
            if (g1yi.length < 97 || g1yi.length > 127) {
                throw new RuntimeException("Key error: g1yi does not fit in four sized words");
            }
            try {
                scKeys.addPublicKey(address, attrName, eg1g1ai, g1yi).send();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Blockchain - Public Keys of attribute " + attrName + " published");
        }
    }

    public void publishAuthority(ObjectNode obj) {
        try {
            String address = obj.get("address").asText();
            String name = obj.get("name").asText();
            String email = obj.get("email").asText();
            scAuthority.addCertifier(address, name, email).send();

            System.out.println("Blockchain - Authority published: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publishUser(ObjectNode obj) {
        String address = obj.get("address").asText();
        String name = obj.get("name").asText();
        String email = obj.get("email").asText();
        try {
            scUsers.addUser(address, name, email).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Blockchain - User published: " + name);
    }

    private CiphertextJSON getCiphertext(String user, String fileName) throws Exception {
        CiphertextJSON ct = null;
        Tuple5<String, byte[], byte[], byte[], byte[]> ciphertextData;
        ciphertextData = scFiles.getCiphertext(user, fileName).send();
        if (!ciphertextData.getValue1().equals("")) {
            AccessStructure as = AccessStructure.buildFromPolicy(ciphertextData.getValue1());
            String c0_ = new String(ciphertextData.getValue2(), "UTF-8");
            byte[] c0 = Base64.getDecoder().decode(c0_);
            String c1x_ = new String(ciphertextData.getValue3(), "UTF-8");
            List<byte[]> c1x = new ArrayList<>();
            for (String x : c1x_.replace("[", "").replace("]", "").split(",")) {
                c1x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            String c2x_ = new String(ciphertextData.getValue4(), "UTF-8");
            List<byte[]> c2x = new ArrayList<>();
            for (String x : c2x_.replace("[", "").replace("]", "").split(",")) {
                c2x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            String c3x_ = new String(ciphertextData.getValue5(), "UTF-8");
            List<byte[]> c3x = new ArrayList<>();
            for (String x : c3x_.replace("[", "").replace("]", "").split(",")) {
                c3x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            ct = new CiphertextJSON(c0, c1x, c2x, c3x, as);
        }
        return ct;
    }

    public Recording getRecording(String user, String fileName) {
        Recording r = null;
        try {
            Tuple5<String, BigInteger, byte[], byte[], BigInteger> recordingData;
            recordingData = scFiles.getRecording(user, fileName).send();
            if (recordingData.getValue5().intValue() != 0) {
                String key = Base64.getEncoder().encodeToString(recordingData.getValue3());
                String hash = Base64.getEncoder().encodeToString(recordingData.getValue4());
                long timestamp = recordingData.getValue5().longValue();
                Tuple3<String, String, BigInteger> serverData = scFiles.getServer(recordingData.getValue2()).send();
                String domain = serverData.getValue1();
                String serverPath = serverData.getValue2();
                int port = serverData.getValue3().intValue();
                String recordingFN = fileName.split("\\.")[0];
                CiphertextJSON ct = getCiphertext(user, fileName);
                if (ct != null) {
                    r = new Recording(fileName, ct, domain, serverPath, port, key, null, recordingFN, timestamp, hash, null, false, false);
                } else {
                    System.out.printf("File %s found but ciphertext is not published.\n", fileName);
                }
            } else {
                System.out.printf("File %s not found.\n", fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public ArrayNode getAttributeRequests(String authority, String address, int listSizeLocal) {
        ArrayNode requests = fc.getMapper().createArrayNode();
        try {
            for (int i = 0; i < listSizeLocal; i++) {
                /*
                 * NOTE: this could lead to error, since it is expected to always have the
                 * request on blockchain, which could not be true if the contract is a new one.
                 * Needs more work to stablish a migration model that does not break the client
                 */
                BigInteger status = scRequests.getRequestStatus(authority, address, BigInteger.valueOf(i)).send();
                requests.add(status);
            }
            BigInteger numRequests = scRequests.getRequestListSize(authority, address).send();
            for (int i = listSizeLocal; i < numRequests.intValue(); i++) {
                Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>> requestTuple;
                requestTuple = scRequests.getRequest(authority, address, BigInteger.valueOf(i)).send();
                ObjectNode request = fc.getMapper().createObjectNode();
                request.put("status", requestTuple.getValue1().intValue());
                request.put("index", requestTuple.getValue2().intValue());
                request.put("timestamp", requestTuple.getValue3());
                request.put("responseTimestamp", requestTuple.getValue4());
                ArrayNode attributes = request.putArray("attributes");
                for (byte[] attrName : requestTuple.getValue5()) {
                    attributes.add(trimmedToString(attrName));
                }
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    // NOTE: check if this code and others could be confined in a util class
    private String trimmedToString(byte[] data) throws UnsupportedEncodingException {
        return new String(data, "UTF-8").replaceFirst("\u0000+$", "");
    }

    public ObjectNode publishAttributeRequest(String authority, String address, List<String> attributes) {
        List<byte[]> attributes_ = new ArrayList<byte[]>();
        attributes.forEach(s -> {
            try {
                attributes_.add(Arrays.copyOf(s.getBytes("UTF-8"), 32));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        ObjectNode request = null;
        try {
            BigInteger index = scRequests.getRequestListSize(authority, address).send();
            long timestamp = System.currentTimeMillis();
            scRequests.addRequest(authority, address, BigInteger.valueOf(timestamp), attributes_).send();
            request = fc.getMapper().createObjectNode();
            request.put("index", index);
            request.put("status", BigInteger.ZERO);
            request.put("timestamp", timestamp);
            ArrayNode attributesNode = request.putArray("attributes");
            attributes.forEach(s -> attributesNode.add(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public boolean userExists(String address) {
        Tuple3<String, String, String> user = null;
        try {
            user = scUsers.getUser(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user != null && !user.getValue1().equals("");
    }

    public ArrayNode getAttributeRequestsForUser(String userID, String status) {
        // TODO: store last timestamp of checking to allow early exit of loop in smart
        // contract

        String path = getBlockchainDataPath() + "AttributeRequest\\";
        String[] authorities = new File(path).list();
        ArrayNode allRequests = fc.getMapper().createArrayNode();
        for (String auth : authorities) {
            String authPath = path + auth + "\\";
            ArrayNode userRequests = (ArrayNode) fc.loadAsJSON(authPath, userID + ".json");
            {
                for (Iterator<JsonNode> iter = userRequests.elements(); iter.hasNext();) {
                    JsonNode r = iter.next();
                    if (!r.get("status").asText().equals(status)) {
                        iter.remove();
                    }
                }
            }
            allRequests.addAll(userRequests);
        }
        return allRequests;
    }

    public ArrayNode getAttributeRequestsForCertifier(String authority, String status) {
        String path = getBlockchainDataPath() + "AttributeRequest\\" + authority + "\\";
        ArrayNode allRequests = fc.getMapper().createArrayNode();
        File f = new File(path);
        for (String user : f.list()) {
            ArrayNode userRequests = (ArrayNode) fc.loadAsJSON(path, user);
            Iterator<JsonNode> iter = userRequests.elements();
            while (iter.hasNext()) {
                JsonNode element = iter.next();
                if (!element.get("status").asText().equals(status)) {
                    iter.remove();
                }
            }
            allRequests.addAll(userRequests);
        }
        return allRequests;
    }

    public Map<String, int[]> publishAttributeRequestUpdate(String authority, String user, BigInteger pendingRequesterIndex,
            BigInteger pendingRequestIndex, RequestStatus newStatus) {
        TransactionReceipt tx = null;
        Map<String, int[]> changedIndexes = new HashMap<>();
        try {
            tx = scRequests.processRequest(authority, pendingRequesterIndex,
                    pendingRequestIndex, BigInteger.valueOf(newStatus.getValue())).send();
            System.out.printf("Blockchain - Attribute request processed by %s.... Result: %s\n", authority.substring(0, 6), newStatus);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Blockchain - Attribute request not processed by %s....", authority.substring(0, 6));
        }
        if (tx != null) {
            List<PendingRequesterIndexChangedEventResponse> changedRequesterIndexes;
            changedRequesterIndexes = scRequests.getPendingRequesterIndexChangedEvents(tx);
            if (changedRequesterIndexes.size() > 0) {
                int[] requesterChanges = { changedRequesterIndexes.get(0).oldIndex.intValue(),
                        changedRequesterIndexes.get(0).newIndex.intValue() };
                changedIndexes.put("requester", requesterChanges);
            }
            List<PendingRequestIndexChangedEventResponse> changedRequestIndexes;
            changedRequestIndexes = scRequests.getPendingRequestIndexChangedEvents(tx);
            if (changedRequestIndexes.size() > 0) {
                int[] requestChanges = {changedRequestIndexes.get(0).oldIndex.intValue(),
                    changedRequestIndexes.get(0).newIndex.intValue()};
                changedIndexes.put("request", requestChanges);
            }
        }
        return changedIndexes;
    }


    public String getNetworkURL() {
        return networkURL;
    }

    // NOTE: function not used
    public int getAttributeRequestListSize(ObjectNode msg) {
        String authority = msg.get("authority").asText();
        String address = msg.get("address").asText();
        int numRequests = -1;
        try {
            BigInteger numRequests_ = scRequests.getRequestListSize(authority, address).send();
            numRequests = numRequests_.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numRequests;
    }

    public void syncAttributeRequestCache(String address, Map<String, ArrayNode> requestCache, String authority) {
        if (!userExists(address)) {
            System.out.println("Blockchain - User does not exist in blockchain");
            return;
        }
        ArrayNode cache = requestCache.get(authority);
        if (cache == null) {
            cache = fc.getMapper().createArrayNode();
            requestCache.put(authority, cache);
        }
        int listSizeLocal = cache.size();
        ArrayNode requests = getAttributeRequests(authority, address, listSizeLocal);

        for (int i = 0; i < listSizeLocal; i++) {
            ObjectNode cachedRequest = (ObjectNode) cache.get(i);
            JsonNode status = requests.remove(0);
            if (cachedRequest.get("status").asInt() != status.asInt()) {
                String message = "Blockchain - request with timestamp %s changed status from: %s to %s.";
                message = String.format(message, cachedRequest.get("timestamp").asInt(),
                        RequestStatus.valueOf(cachedRequest.get("status").asInt()), RequestStatus.valueOf(status.asInt()));
                System.out.println(message);
                cachedRequest.replace("status", status);
            }
        }
        cache.addAll(requests);
    }

    public ArrayNode getPendingAttributeRequests(String authority, String address, List<BigInteger> pendingRequests) {
        ArrayNode requests = fc.getMapper().createArrayNode();
        try {
            for (int i = 0; i < pendingRequests.size(); i++) {
                Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>> requestTuple;
                requestTuple = scRequests.getRequest(authority, address, pendingRequests.get(i)).send();
                ObjectNode request = fc.getMapper().createObjectNode();
                request.put("pendingIndex", i);
                request.put("status", requestTuple.getValue1().intValue());
                request.put("index", requestTuple.getValue2().intValue());
                request.put("timestamp", requestTuple.getValue3());
                request.put("responseTimestamp", requestTuple.getValue4());
                ArrayNode attributes = request.putArray("attributes");
                for (byte[] attrName : requestTuple.getValue5()) {
                    attributes.add(trimmedToString(attrName));
                }
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    @SuppressWarnings("unchecked")
    public void syncPendingAttributeRequests(String authority, Map<String, ObjectNode> requestCache) {
        if (!certifierExists(authority)) {
            System.out.println("Blockchain - Certifier does not exist in blockchain");
            return;
        }
        BigInteger numRequesters;
        try {
            numRequesters = scRequests.getPendingRequesterListSize(authority).send();
            for (int i = 0; i < numRequesters.intValue(); i++) {
                // FIX: return proper address with valid Checksum. Currently the address is all lowercase
                String address = scRequests.getPendingRequesterAddress(authority, BigInteger.valueOf(i)).send();
                ObjectNode cacheWrapper = requestCache.get(address);
                if (cacheWrapper == null) {
                    cacheWrapper = fc.getMapper().createObjectNode();
                    requestCache.put(address, cacheWrapper);
                    cacheWrapper.put("index", i);
                    cacheWrapper.put("address", address);
                }
                List<BigInteger> pendingRequestsIndexes = scRequests.getPendingList(authority, address).send();
                ArrayNode pendingRequests = getPendingAttributeRequests(authority, address, pendingRequestsIndexes);
                cacheWrapper.withArray("requests").removeAll().addAll(pendingRequests);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean certifierExists(String address) {
        Tuple4<String, String, String, BigInteger> certifier = null;
        try {
            certifier = scAuthority.getCertifier(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certifier != null && !certifier.getValue1().equals("");
    }
}
