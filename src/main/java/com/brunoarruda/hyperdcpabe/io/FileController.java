package com.brunoarruda.hyperdcpabe.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * FileController
 */
public final class FileController {

    private static final FileController INSTANCE = new FileController();
    private static final ObjectMapper mapper = new ObjectMapper();

    private boolean printErrorsFlag;

    private FileController() {}

    public static FileController getInstance() {
        return INSTANCE;
    }

    public FileController configure(String path, boolean printErrorsFlag) {
        this.printErrorsFlag = printErrorsFlag;
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

    public <T> void writeToDir(String path, String fileName, T obj) {
        File folder = new File(path);
        folder.mkdirs();
        try {
            mapper.writeValue(new File(path, fileName), obj);
        } catch (IOException e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        }
    }

    public <T extends Object> List<T> readAsList(String path, String file, Class<T> classReference) {
        return readAsList(path, file,"", classReference);
    }

    public <T extends Object> List<T> readAsList(String path, String file, String internalPath, Class<T> classReference) {
        File f = new File(path, file);
        List<T> list = new ArrayList<T>();
        try {
            JsonNode json = mapper.readTree(f);
            if (!internalPath.equals("")) {
                for (String key : internalPath.split("\\.")) {
                    json = json.get(key);
                }
            }
            Iterator<JsonNode> nodes = json.elements();
            while (nodes.hasNext()) {
                String value = nodes.next().toString();
                list.add(mapper.readValue(value, classReference));
            }
        } catch (Exception e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public <K extends Object, V extends Object> Map<K, V> readAsMap(String path, String file, String internalPath, Class<K> keyClass,
    Class<V> valueClass) {
        File f = new File(path, file);
        Map<K, V> map = new HashMap<K, V>();
        try {
            ObjectNode json = (ObjectNode) mapper.readTree(f);
            if (!internalPath.equals("")) {
                for (String key : internalPath.split("\\.")) {
                    json = (ObjectNode) json.get(key);
                }
            }
            Iterator<Entry<String, JsonNode>> nodes = json.fields();
            while (nodes.hasNext()) {
                Entry<String, JsonNode> entry = nodes.next();
                String value = entry.getValue().toString();
                map.put((K) entry.getKey(), mapper.readValue(value, valueClass));
            }
        } catch (Exception e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public <K extends Object, V extends Object> Map<K, V> readAsMap(String path, String file, Class<K> keyClass,
            Class<V> valueClass) {
        return readAsMap(path, file, "", keyClass, valueClass);
    }

    public <T> T readFromDir(String path, String fileName, String internalPath, Class<T> typeReference) {
        T result = null;
        try {
            ObjectNode data = (ObjectNode) mapper.readTree(new File(path, fileName));
            if (!internalPath.equals("")) {
                for (String key : internalPath.split("\\.")) {
                    data = (ObjectNode) data.get(key);
                }
            }
            result  = mapper.readValue(mapper.writeValueAsString(data), typeReference);
        } catch (JsonProcessingException e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public <T> T readFromDir(String path, String fileName, Class<T> typeReference) {
        return readFromDir(path, fileName, "", typeReference);
    }

    public <K, V> JsonNode getJSONFromMap(Map<K, V> map) {
        JsonNode obj = null;
        try {
            String jsonStr = mapper.writeValueAsString(map);
            obj = mapper.readTree(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public JsonNode loadAsJSON(String path, String file) {
        JsonNode obj = null;
        try {
            obj = mapper.readTree(new File(path, file));
        } catch (IOException e) {
            if (printErrorsFlag) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
