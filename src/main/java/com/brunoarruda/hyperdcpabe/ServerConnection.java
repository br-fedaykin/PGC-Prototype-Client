package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.io.FileController;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;

/**
 * ServerConnection
 */
public class ServerConnection {

    /**
     *
     */

    private static final String HOST = "127.0.0.1";
    private static final String dataPath = "server";
    private static final int port = 8081;
    private final int SERVER_PORT;
    private final FileController fc;
    private Map<String, String> serverKeys;
    private Socket serverConnection;
    private int BUFFER_SIZE = 1024;
    private PrintWriter serverSender;
    private BufferedReader serverReceiver;

    public ServerConnection(int serverPort) {
        this.SERVER_PORT = serverPort;
        this.serverKeys = new HashMap<String, String>();
        fc = FileController.getInstance();
        this.serverKeys = loadKeys();
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    private Map<String, String> loadKeys() {
        String path = getServerDataPath();
        return fc.readAsMap(path, "serverKeys.json", String.class, String.class);
    }

    /**
     * @return the host
     */
    public String getHost() {
        return HOST;
    }

    private void connectToServer(int serverPort) {
        try {
            serverConnection = new Socket(HOST, SERVER_PORT);
            OutputStream os = serverConnection.getOutputStream();
            serverSender = new PrintWriter(os, true);
            serverReceiver = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private String createKeyOnServer(String userID) {
       String code = null;
        try {
            byte[] random = new byte[32];
            SecureRandom.getInstanceStrong().nextBytes(random);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(random);
            code = Base64.getEncoder().encodeToString(hash);
            serverKeys.put(userID, code.replace("/", "-"));
            return code;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("Server Connection - Reserved path inside server to " + userID);
        return code;
    }

    public String reserveSpace(ObjectNode message) {
        String userID = message.get("userID").asText();
        if (message.get("tx") == null) {
            if (serverKeys.get(userID) == null) {
                return createKeyOnServer(userID);
            }
        }
        return serverKeys.get(userID).replace("-", "/");
    }

    public String getServerDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

	public void sendFile(String userID, String fileName, List<byte[]> data) {
        String path = getServerDataPath() + serverKeys.get(userID) + "\\";
        File f = new File(path);
        f.mkdirs();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
                for (byte[] buff : data) {
                    fos.write(buff);
                }
            // after allocating space, key must be preserved
            fc.writeToDir(getServerDataPath(), "serverKeys.json", serverKeys);
            System.out.println("Server Connection - " + userID + " sent file " + fileName);
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
	}

	public List<byte[]> getFile(String key, String fileName) {
        List<byte[]> data = new ArrayList<byte[]>();
        String path = getServerDataPath() + key + "\\";
        try (FileInputStream fis = new FileInputStream(path + fileName);
        BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buff = new byte[BUFFER_SIZE];
            int readBytes;
            while ((readBytes = bis.read(buff)) != -1) {
                if (readBytes != BUFFER_SIZE) {
                    data.add(Arrays.copyOf(buff, readBytes));
                } else {
                    data.add(buff);
                    buff = new byte[BUFFER_SIZE];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Connection - delivered following file: " + fileName);
        return data;
    }

	public boolean hasContent(String userID, String content) {
        boolean isOnServer = false;
        if (serverKeys.get(userID) != null) {
            String path = getServerDataPath() + serverKeys.get(userID) + "\\";
            isOnServer = new File(path + content).exists();
        }
		return isOnServer;
	}

	public void sendKeys(String userID, ArrayNode personalKeys) {
        String path = getServerDataPath() + "Temporary Key Storage\\";
        File f = new File(path);
        f.mkdirs();
        fc.writeToDir(path, userID + "-pks.json", personalKeys);
        System.out.println("Server Connection - Storing temporary the personal keys of " + userID + ".");
	}

	public List<PersonalKey> getPersonalKeys(String userID) {
        List<PersonalKey> pks;
        String path = getServerDataPath() + "Temporary Key Storage\\";
        String file = userID + "-pks.json";
        pks = fc.readAsList(path, file, PersonalKey.class);
        File f = new File(path, file);
        try {
            f.delete();
        } catch (Exception e) {
            System.out.println("ServerConnection: file " + file + " not found." );
        }
        System.out.println("Server Connection - user " + userID + " redeemed his personal keys.");
        return pks;
	}

	public String getPath(String fileType) {
        // TODO: Create enum ou mapa
        if (fileType.equals("file")) {
            return "file";
        } else {
            return "";
        }
	}
}