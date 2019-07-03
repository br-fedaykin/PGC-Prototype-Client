package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
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
        String fileName = obj.get("recordingFileName").asText() + ".json";
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

	public void publishAttributeRequest(ObjectNode msg) {
        String userID = msg.get("userID").asText();
        if (!userExists(userID)) {
            System.out.println("User does not exist in blockchain");
        } else {
            String authority = msg.get("authority").asText();
            String path = getBlockchainDataPath() + "AttributeRequest\\" + authority + "\\";
            ArrayNode allRequests = (ArrayNode) fc.loadAsJSON(path, userID + ".json");
            if (allRequests != null) {
                boolean hadAlreadyDone = false;
                for (JsonNode r : allRequests) {
                    if (r.equals(msg)) {
                        System.out.println("Blockchain Interface: Request already made.");
                        hadAlreadyDone = true;
                        break;
                    }
                }
                if (!hadAlreadyDone) {
                    allRequests.add(msg);
                }
            } else {
                allRequests = fc.getMapper().createArrayNode().add(msg);
                fc.writeToDir(path, userID + ".json", allRequests);
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
                int index = 0;
                for (Iterator<JsonNode> iter = userRequests.elements(); iter.hasNext();) {
                    JsonNode r = iter.next();
                    if (!r.get("status").asText().equals(status)) {
                        iter.remove();
                    } else {
                        index++;
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
            int index = 0;
            while(iter.hasNext()) {
                JsonNode element = iter.next();
                if (!element.get("status").asText().equals(status)) {
                    userRequests.remove(index);
                } else {
                    index++;
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
	}
}