package com.brunoarruda.hyper_dcpabe.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.brunoarruda.hyper_dcpabe.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public <K extends Object, V extends Object> Map<K, V> readAsMap(String path, String file, Class<K> key, Class<V> value) {
        File f = new File(path, file);
        try {
            TypeReference<Map<K, V>> typeRef = new TypeReference<Map<K, V>>() {
            };
            return mapper.readValue(f, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("Couldnt parse json:" + file, e);
        }
    }

    public <T> T readFromDir(String path, String fileName, Class<T> typeReference) {
        File f = new File(path, fileName);
        T obj = null;
        try {
            obj = mapper.readValue(f, typeReference);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
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
}