package com.brunoarruda.hyper_dcpabe.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.brunoarruda.hyper_dcpabe.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

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
        String dir = String.format("%s%s-%s", getDataDirectory(), user.getName(),
                user.getPublicECKey().substring(0, 8));
        return dir + "\\" + postfix;
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

    public <T> T readFromDir(String path, String fileName, Class<T> typeReference) {
        File f = new File(path + fileName);
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

    public void writeUser(User user) {
        String userFileName = user.getClass().getSimpleName() + ".json";
        writeToDir(getUserDirectory(user), userFileName, user);
    }

    public User readUser(User user) {
        String userFileName = user.getClass().getSimpleName() + ".json";
        return readFromDir(getUserDirectory(user), userFileName, user.getClass());
    }

    public void writeAllPublicKeys(User user) {
        for (Entry<String, Map<String, PublicKey>> auth : user.getAllPulicABEKeys().entrySet()) {
            String path = getUserDirectory(user, "PublicABEKeys");
            writeToDir(path, auth.getKey() + ".json", auth.getValue());
            try {
                Utility.writePublicKeys(path + auth.getKey(), auth.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}