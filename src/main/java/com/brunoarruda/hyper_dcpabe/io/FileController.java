package com.brunoarruda.hyper_dcpabe.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.brunoarruda.hyper_dcpabe.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

/**
 * FileController
 */
public final class FileController {

    private static final FileController INSTANCE = new FileController();
    private static final ObjectMapper mapper = new ObjectMapper();

    private String dataFolder;

    private FileController() {
    }

    public static FileController getInstance() {
        return INSTANCE;
    }

    public FileController configure(String path) {
        this.dataFolder = path;
        File dirFile = new File(path);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
        // to enable standard indentation ("pretty-printing"):
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // to allow serialization of "empty" POJOs (no properties to serialize)
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return this;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public String getDataDirectory() {
        return dataFolder + "\\";
    }

    public String getUserDirectory(User user) {
        return getUserDirectory(user, "");
    }

    public String getUserDirectory(User user, String subDirectory) {
        String postfix = "";
        if (!subDirectory.equals("")) {
            postfix = subDirectory + "\\";
        }
        return getDataDirectory() + "client\\" + user.getUserID() + "\\" + postfix;
    }

    public <T> void writeToDir(String path, String fileName, T obj) {
        File folder = new File(path);
        folder.mkdirs();
        path = path + fileName;
        try {
            mapper.writeValue(new File(path), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <K extends Object, V extends Object> Map<K, V> readAsMap(String path, String file, Class<K> keyClass,
        Class<V> valueClass) {
        File f = new File(path, file);
        Map<K, V> map = new HashMap<K, V>();
        try {
            ObjectNode json = (ObjectNode) mapper.readTree(f);
            Iterator<Entry<String, JsonNode>> nodes = json.fields();
            while(nodes.hasNext()) {
                Entry<String, JsonNode> entry = nodes.next();
                String value = entry.getValue().toString();
                map.put((K) entry.getKey(), mapper.readValue(value, valueClass));
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Couldnt parse json:" + file, e);
        }
    }

    public <T> T readFromDir(String path, String fileName, Class<T> typeReference) {
        File f = new File(path, fileName);
        T obj = null;
        try {
            obj = mapper.readValue(f, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ObjectNode loadAsJSON(String path, String file) {
        ObjectNode obj = null;
        try {
            obj = (ObjectNode) mapper.readTree(new File(path, file));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

	public void writeEncrypted(PaddedBufferedBlockCipher aes, String path, String file) {
        try (FileOutputStream fos = new FileOutputStream(path + "(enc)" + file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                FileInputStream fis = new FileInputStream(path + file);
                BufferedInputStream bis = new BufferedInputStream(fis);
            ) {

            byte[] inBuff = new byte[aes.getBlockSize()];
            byte[] outBuff = new byte[aes.getOutputSize(inBuff.length)];
            int nBytes;
            while (-1 != (nBytes = bis.read(inBuff, 0, inBuff.length))) {
                int length1 = aes.processBytes(inBuff, 0, nBytes, outBuff, 0);
                oos.write(outBuff, 0, length1);
            }
            nBytes = aes.doFinal(outBuff, 0);
            oos.write(outBuff, 0, nBytes);
        } catch (DataLengthException | IllegalStateException | IOException | InvalidCipherTextException e) {
            e.printStackTrace();
        }
    }
}