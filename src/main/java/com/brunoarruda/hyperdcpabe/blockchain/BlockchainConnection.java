package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.Recording;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
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
    private String contractAddress;
    private final Web3j web3j;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public BlockchainConnection(String networkURL, String contractAddress) {
        // TODO: refactor URL as a POM field or command line/file config
        // POM field seems better, as it would allow different value for deploy/test cycles
        this.networkURL = networkURL;
        this.contractAddress = contractAddress;
        web3j = Web3j.build(new HttpService(networkURL));
    }

    public void deployContract(Credentials credentials) {
        if (contractAddress == null) {
            try {
                ContractGasProvider contractGasProvider = new DefaultGasProvider();
                SmartDCPABE contract = SmartDCPABE.deploy(web3j, credentials, contractGasProvider).send();
                contractAddress = contract.getContractAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Contract already deployed at address " + contractAddress);
        }
    }

    // TODO: add similar methods to get events about all editable objects on blockchain
    public boolean hasCiphertextChanged() {
        // Events enable us to log specific events happening during the execution of our
        // smart
        // contract to the blockchain. Index events cannot be logged in their entirety.
        // For Strings and arrays, the hash of values is provided, not the original
        // value.
        // For further information, refer to
        // https://docs.web3j.io/filters.html#filters-and-events
        // for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
        //     log.info("Modify event fired, previous value: " + event.oldGreeting + ", new value: " + event.newGreeting);
        //     log.info("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx) + ", new value: "
        //             + Numeric.toHexString(event.newGreetingIdx));
        // }
        return false;
    }

    public ECKey generateECKeys(String privateKey) {
        ECKey keys = null;
        if (privateKey != null) {
            keys = ECKey.fromPrivate(new BigInteger(privateKey, 16));
            byte[] pubKey = keys.getPubKey();
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
        String fileName = obj.get("recordingFileName").asText() + ".json";
        dir.mkdirs();
        fc.writeToDir(path, fileName, obj);
        System.out.println("Blockchain Interface - data published: " + fileName);
    }

    public void publishABEKeys(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "PublicABEKeys");
        dir.mkdirs();
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
        System.out.println("Blockchain Interface - Public Keys of authority " + label + " published");
    }

	public void publishAuthority(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "Authority");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
        System.out.println("Blockchain Interface - Authority published: " + label);
	}

	public void publishUser(String label, ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo

        File dir = new File(getBlockchainDataPath() + "User");
        dir.mkdirs();
        obj.get("name");
        fc.writeToDir(dir.getPath() + "\\", label + ".json", obj);
        System.out.println("Blockchain Interface - User published: " + label);
	}

	public Recording getRecording(String userID, String fileName) {
        Recording r = null;
        String path = getBlockchainDataPath() + "Files\\" + userID + "\\";
        String fileNameEdited = fileName.split("\\..+?$")[0] + ".json";
        if (new File(path + fileName).exists()) {
            r = fc.readFromDir(path, fileName, Recording.class);
            System.out.println("Blockchain Interface - Data found: " + fileName);
        } else if (new File(path + fileNameEdited).exists()) {
            r = fc.readFromDir(path, fileNameEdited, Recording.class);
            System.out.println("Blockchain Interface - Data found: " + fileName);
        } else {
            System.out.println("Blockchain Interface - File " + fileName + " not found on blockchain");
        }
        return r;
	}

	public void publishAttributeRequest(ObjectNode msg) {
        String userID = msg.get("userID").asText();
        if (!userExists(userID)) {
            System.out.println("Blockchain Interface - User does not exist in blockchain");
        } else {
            String authority = msg.get("authority").asText();
            String path = getBlockchainDataPath() + "AttributeRequest\\" + authority + "\\";
            ArrayNode allRequests = (ArrayNode) fc.loadAsJSON(path, userID + ".json");
            if (allRequests != null) {
                boolean hadAlreadyDone = false;
                for (JsonNode r : allRequests) {
                    if (r.get("authority").equals(msg.get("authority")) &&
                        r.get("attributes").equals(msg.get("attributes"))) {
                        System.out.println("Blockchain Interface - Request already made.");
                        hadAlreadyDone = true;
                        break;
                    }
                }
                if (!hadAlreadyDone) {
                    allRequests.add(msg);
                    fc.writeToDir(path, userID + ".json", allRequests);
                    System.out.println("Blockchain Interface - Attribute Request published: " + userID);
                }
            } else {
                allRequests = fc.getMapper().createArrayNode().add(msg);
                fc.writeToDir(path, userID + ".json", allRequests);
                System.out.println("Blockchain Interface - Attribute Request published: " + userID);
            }

        }
	}

    public boolean userExists(String userID) {
        String path = getBlockchainDataPath() + "User\\";
        return new File(path + userID + ".json").exists();
    }

    public ArrayNode getAttributeRequestsForRequester(String userID, String status) {
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
        System.out.println("Blockchain Interface - Attribute request processed by " + certifierID + ". Result: " + newStatus);
	}

	public String getNetworkURL() {
		return networkURL;
	}

	public String getContractAddress() {
		return contractAddress;
	}
}