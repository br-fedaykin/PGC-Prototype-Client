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

import com.brunoarruda.hyperdcpabe.Recording;
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
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

public class BlockchainConnection {

    // TODO: send message to logger instead of System.out
    private static final Logger log = LoggerFactory.getLogger(BlockchainConnection.class);

    private String dataPath = "blockchain";
    private FileController fc;

    static private final byte[] seed = "Honk Honk".getBytes();
    static private final SecureRandom random = new SecureRandom(seed);
    private final String networkURL;
    /*
     * TODO: get contracts addresses together in a Map, or do that with contract
     * objects, as they have the addresses in them
     */
    private String contractUsersAddress;
    private String contractAuthorityAddress;
    private String contractFilesAddress;
    private String contractKeysAddress;
    private SmartDCPABEUsers contractUsers;
    private SmartDCPABEAuthority contractAuthority;
    private SmartDCPABEFiles contractFiles;
    private SmartDCPABEKeys contractKeys;
    private final Web3j web3j;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    // TODO: refactor URL as a POM field or command line/file config
    // POM field seems better, as it would allow different value for deploy/test
    // cycles
    public BlockchainConnection(String networkURL, String contractUsersAddress, String contractAuthorityAddress,
            String contractFilesAddress, String contractKeysAddress) {
        this.networkURL = networkURL;
        this.contractUsersAddress = contractUsersAddress;
        this.contractAuthorityAddress = contractAuthorityAddress;
        this.contractFilesAddress = contractFilesAddress;
        this.contractKeysAddress = contractKeysAddress;
        web3j = Web3j.build(new HttpService(networkURL));
        fc = FileController.getInstance();
    }

    public void deployContracts(Credentials credentials) {
        ContractGasProvider cgp = new DefaultGasProvider();
        if (contractUsersAddress != null) {
            contractUsers = SmartDCPABEUsers.load(contractUsersAddress, web3j, credentials, cgp);
        }
        if (contractAuthorityAddress != null) {
            contractAuthority = SmartDCPABEAuthority.load(contractAuthorityAddress, web3j, credentials, cgp);
        }
        if (contractFilesAddress != null) {
            contractFiles = SmartDCPABEFiles.load(contractFilesAddress, web3j, credentials, cgp);
        }
        if (contractKeysAddress != null) {
            contractKeys = SmartDCPABEKeys.load(contractKeysAddress, web3j, credentials, cgp);
        }
    }

    // TODO: add similar methods to get events about all editable objects on
    // blockchain
    public boolean hasCiphertextChanged() {
        // Events enable us to log specific events happening during the execution of our
        // smart
        // contract to the blockchain. Index events cannot be logged in their entirety.
        // For Strings and arrays, the hash of values is provided, not the original
        // value.
        // For further information, refer to
        // https://docs.web3j.io/filters.html#filters-and-events
        // for (Greeter.ModifiedEventResponse event :
        // contract.getModifiedEvents(transactionReceipt)) {
        // log.info("Modify event fired, previous value: " + event.oldGreeting + ", new
        // value: " + event.newGreeting);
        // log.info("Indexed event previous value: " +
        // Numeric.toHexString(event.oldGreetingIdx) + ", new value: "
        // + Numeric.toHexString(event.newGreetingIdx));
        // }
        return false;
    }

    @Deprecated
    public ECKey generateECKeys(String privateKey) {
        ECKey keys = null;
        if (privateKey != null) {
            keys = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        } else {
            keys = new ECKey(random);
        }
        return keys;
    }

    @Deprecated
    public Transaction createTransaction(ECKey userKeys, ObjectNode content) {
        return null;
    }

    public Map<String, PublicKey> getABEPublicKeys(String authority, String[] attributes) {
        // TODO: alternar isso para um JSON
        String authName = authority.split("-")[0];
        String address = authority.split("-")[1];
        try {
            Tuple4<String, String, String, BigInteger> certifier = contractAuthority.getCertifier(address).send();
            if (certifier.getValue4().equals(BigInteger.ZERO)) {
                System.out.println("A autoridade " + authName + " não publicou nenhum atributo.");
                return null;
            } else {
                Map<String, PublicKey> keys = new HashMap<String, PublicKey>();
                for (String attr : attributes) {
                    Tuple3<String, byte[], byte[]> keyData = contractKeys.getPublicKey(address, attr).send();
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
        String path = obj.get("path").asText();
        BigInteger port = obj.get("port").bigIntegerValue();
        BigInteger serverID = null;
        try {
            serverID = contractFiles.getServerID(domain).send();
            if (serverID.longValue() == -1) {
                contractFiles.addServer(domain, path, port).send();
                serverID = contractFiles.getServerID(domain).send();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String address = obj.get("address").asText();
        BigInteger timestamp = obj.get("timestamp").bigIntegerValue();
        String c0 = obj.get("ciphertext").get("c0").asText();
        String c1 = obj.get("ciphertext").get("c1").asText();
        String c2 = obj.get("ciphertext").get("c2").asText();
        String c3 = obj.get("ciphertext").get("c3").asText();
        String fileName = obj.get("fileName").asText();
        byte[] key = Base64.getDecoder().decode(obj.get("key").asText());
        byte[] hash = Base64.getDecoder().decode(obj.get("hash").asText());
        int numRecording = -1;
        try {
            BigInteger numRecording_ = contractFiles.getFileIndex(address).send();
            contractFiles.addRecording(address, fileName.getBytes(), serverID, key, hash, timestamp, c0, c1, c2, c3)
                    .send();
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
                contractKeys.addPublicKey(address, attrName, eg1g1ai, g1yi).send();
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
            contractAuthority.addCertifier(address, name, email).send();

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
            contractUsers.addUser(address, name, email).send();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Blockchain - User published: " + name);
    }

    public Recording getRecording(String userID, String fileName) {
        Recording r = null;
        String path = getBlockchainDataPath() + "Files\\" + userID + "\\";
        String fileNameEdited = fileName.split("\\..+?$")[0] + ".json";
        if (new File(path + fileName).exists()) {
            r = fc.readFromDir(path, fileName, Recording.class);
            System.out.println("Blockchain - Data found: " + fileName);
        } else if (new File(path + fileNameEdited).exists()) {
            r = fc.readFromDir(path, fileNameEdited, Recording.class);
            System.out.println("Blockchain - Data found: " + fileName);
        } else {
            System.out.println("Blockchain - File " + fileName + " not found on blockchain");
        }
        return r;
    }

    public ArrayNode getAttributeRequests(String authority, String address, int listSizeLocal) {
        ArrayNode requests = fc.getMapper().createArrayNode();
        try {
            for (int i = 0; i < listSizeLocal; i++) {
                BigInteger status = contractAuthority.getRequestStatus(authority, address, BigInteger.valueOf(i))
                        .send();
                requests.add(status);
            }
            BigInteger numRequests = contractAuthority.getPendingListSize(authority, address).send();
            for (int i = listSizeLocal; i < numRequests.intValue(); i++) {
                Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>> requestTuple;
                requestTuple = contractAuthority.getPendingRequest(authority, address, BigInteger.valueOf(i)).send();
                ObjectNode request = fc.getMapper().createObjectNode();
                request.put("status", requestTuple.getValue1().intValue());
                request.put("timestamp", requestTuple.getValue2());
                request.put("responseTimestamp", requestTuple.getValue3());
                ArrayNode attributes = request.putArray("attributes");
                for (byte[] attrName_ : requestTuple.getValue4()) {
                    String attrName = new String(attrName_, "UTF-8").replaceFirst("\u0000+$", "");
                    attributes.add(attrName);
                }
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ObjectNode publishAttributeRequest(String authority, String address, List<String> attributes) {
        List<byte[]> attributes_ = new ArrayList<byte[]>();
        long timestamp = System.currentTimeMillis();
        attributes.forEach(s -> {
            try {
                attributes_.add(Arrays.copyOf(s.getBytes("UTF-8"), 32));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        ObjectNode request = null;
        try {
            contractAuthority.addRequest(authority, address, BigInteger.valueOf(timestamp), attributes_).send();
            request = fc.getMapper().createObjectNode();
            // check if pending corresponds to zero, and check if enum values on solidity can be enforced explicitly
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
            user = contractUsers.getUser(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user != null && !user.getValue1().equals("");
    }

    public ArrayNode getAttributeRequestsForRequester(String userID, String status) {
        // TODO: store last timestamp of checking to allow early exit of loop in smart contract

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
            while(iter.hasNext()) {
                JsonNode element = iter.next();
                if (!element.get("status").asText().equals(status)) {
                    iter.remove();
                }
            }
            allRequests.addAll(userRequests);
        }
        return allRequests;
	}

	public void publishAttributeRequestUpdate(String certifierID, String userID, String[] attributes, String newStatus) {
        String path = getBlockchainDataPath() + "AttributeRequest\\" + certifierID + "\\";
        ArrayNode requests = (ArrayNode) fc.loadAsJSON(path, userID + ".json");
        for (JsonNode node : requests) {
            String nodeAttributes = node.get("attributes").toString().replace("\"", "");
            if (nodeAttributes.equals(Arrays.toString(attributes))) {
                ((ObjectNode) node).put("status", newStatus);
                break;
            }
        }
        fc.writeToDir(path, userID + ".json", requests);
        System.out.println("Blockchain - Attribute request processed by " + certifierID + ". Result: " + newStatus);
	}

	public String getNetworkURL() {
		return networkURL;
	}

	public String getContractAddress() {
		return contractUsersAddress;
	}

	public int getAttributeRequestListSize(ObjectNode msg) {
        String authority = msg.get("authority").asText();
        String address = msg.get("address").asText();
        int numRequests = -1;
        try {
            BigInteger numRequests_ = contractAuthority.getPendingListSize(authority, address).send();
            numRequests = numRequests_.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return numRequests;
	}

	public void updateAttributeRequestCache(String address, Map<String, ArrayNode> requestCache) {
        for (String authority : requestCache.keySet()) {
            syncAttributeRequestCache(address, requestCache, authority);
        }
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
                message = String.format(message, cachedRequest.get("timestamp").asInt(), cachedRequest.get("status").asInt(),
                        status.asInt());
                System.out.println(message);
                cachedRequest.replace("status", status);
            }
        }
        cache.addAll(requests);
	}
}