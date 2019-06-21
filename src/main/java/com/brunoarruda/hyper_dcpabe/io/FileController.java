package com.brunoarruda.hyper_dcpabe.io;

import java.io.File;
import java.io.IOException;

import com.brunoarruda.hyper_dcpabe.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * FileController
 */
public final class FileController {

    private static final FileController INSTANCE = new FileController();
    private static final ObjectMapper mapper = new ObjectMapper();

    private File dataFolder;

    private FileController() {}

    public static FileController getInstance() {
        return INSTANCE;
    }

    public void configure(String path) {
        dataFolder = new File(path);
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            dataFolder.mkdirs();
        }

        // to enable standard indentation ("pretty-printing"):
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // to allow serialization of "empty" POJOs (no properties to serialize)
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public File getDataDirectory(String path) {
        return dataFolder;
    }

    public void writeUser(String directoryPath, User user) {
        if (dataFolder == null) {
            configure(directoryPath);
        }
        String dir = String.format("%s%s%s-%s", dataFolder.getPath(), File.separator, user.getName(),
                user.getPublicECKey().substring(0, 8));
        File userDir = new File(dir);
        userDir.mkdir();
        try {
            mapper.writeValue(new File(dir + File.separator + "user.json"), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User readUser(String publicKeysPath) {
        return null;
    }
}