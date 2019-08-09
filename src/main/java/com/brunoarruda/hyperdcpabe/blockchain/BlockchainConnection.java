package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
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
import org.spongycastle.util.encoders.Base64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
    private String contractFilesAddress;
    private String contractAuthorityAddress;
    private SmartDCPABEFiles contractFiles;
    private SmartDCPABEAuthority contractAuthority;
    private final Web3j web3j;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    // TODO: refactor URL as a POM field or command line/file config
    // POM field seems better, as it would allow different value for deploy/test cycles
    public BlockchainConnection(String networkURL, String contractAddress, String contractAuthorityAddress) {
        this.networkURL = networkURL;
        this.contractFilesAddress = contractAddress;
        this.contractAuthorityAddress = contractAuthorityAddress;
        web3j = Web3j.build(new HttpService(networkURL));
        fc = FileController.getInstance();
    }

    public void deployContracts(Credentials credentials) {
        ContractGasProvider cgp = new DefaultGasProvider();;
        if (contractFilesAddress == null) {
            RemoteCall<SmartDCPABEFiles> contractTX = SmartDCPABEFiles.deploy(web3j, credentials, cgp);
            // BUG: the send() method leads to a RuntimeException about an invalid opcode.
            try {
                contractFiles = contractTX.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
            contractFilesAddress = contractFiles.getContractAddress();
        } else {
            contractFiles = SmartDCPABEFiles.load(contractFilesAddress, web3j, credentials, cgp);
        }
        cgp = new DefaultGasProvider();
        if (contractAuthorityAddress == null) {
            RemoteCall<SmartDCPABEAuthority> contractTX = SmartDCPABEAuthority.deploy(web3j, credentials, cgp);
            // BUG: the send() method leads to a RuntimeException about an invalid opcode.
            try {
                contractAuthority = contractTX.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
            contractAuthorityAddress = contractFiles.getContractAddress();
        } else {
            contractAuthority = SmartDCPABEAuthority.load(contractAuthorityAddress, web3j, credentials, cgp);
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
                    Tuple3<String, byte[], byte[]> keyData = contractAuthority.getPublicKeyByName(address, attr).send();
                    keys.put(attr, new PublicKey(keyData.getValue2(), keyData.getValue3()));
                }
                return keys;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public void publishData(String userID, ObjectNode obj) {
        String path = getBlockchainDataPath() + "Files\\" + userID + "\\";
        File dir = new File(path);
        String fileName = obj.get("recordingFileName").asText() + ".json";
        dir.mkdirs();
        fc.writeToDir(path, fileName, obj);
        System.out.println("Blockchain - data published: " + fileName);
    }

    public void publishABEKeys(ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo
        // TODO: checar existência de certificador com o endereço fornecido
        String address = obj.remove("address").asText();
        Iterator<String> it = obj.fieldNames();
        while (it.hasNext()) {
            String attrName = it.next();
            JsonNode attrNode = obj.get(attrName);
            byte[] eg1g1ai = Base64.decode(attrNode.get("eg1g1ai").asText());
            byte[] g1yi = Base64.decode(attrNode.get("g1yi").asText());
            if (eg1g1ai.length < 97 || eg1g1ai.length > 127) {
                throw new RuntimeException("Key error: eg1g1ai does not fit in four sized words");
            }
            if (g1yi.length < 97 || g1yi.length > 127) {
                throw new RuntimeException("Key error: g1yi does not fit in four sized words");
            }
            try {
                contractAuthority.addPublicKey(address, attrName, eg1g1ai, g1yi).send();
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
            contractFiles.addUser(address, name, email).send();
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

	public void publishAttributeRequest(ObjectNode msg) {
        String userID = msg.get("userID").asText();
        if (!userExists(userID)) {
            System.out.println("Blockchain - User does not exist in blockchain");
        } else {
            String authority = msg.get("authority").asText();
            String path = getBlockchainDataPath() + "AttributeRequest\\" + authority + "\\";
            ArrayNode allRequests = (ArrayNode) fc.loadAsJSON(path, userID + ".json");
            if (allRequests != null) {
                boolean hadAlreadyDone = false;
                for (JsonNode r : allRequests) {
                    if (r.get("authority").equals(msg.get("authority")) &&
                        r.get("attributes").equals(msg.get("attributes"))) {
                        System.out.println("Blockchain - Request already made.");
                        hadAlreadyDone = true;
                        break;
                    }
                }
                if (!hadAlreadyDone) {
                    allRequests.add(msg);
                    fc.writeToDir(path, userID + ".json", allRequests);
                    System.out.println("Blockchain - Attribute Request published: " + userID);
                }
            } else {
                allRequests = fc.getMapper().createArrayNode().add(msg);
                fc.writeToDir(path, userID + ".json", allRequests);
                System.out.println("Blockchain - Attribute Request published: " + userID);
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
        System.out.println("Blockchain - Attribute request processed by " + certifierID + ". Result: " + newStatus);
	}

	public String getNetworkURL() {
		return networkURL;
	}

	public String getContractAddress() {
		return contractFilesAddress;
	}
}