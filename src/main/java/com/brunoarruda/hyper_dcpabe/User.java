package com.brunoarruda.hyper_dcpabe;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.bitcoinj.core.ECKey;
import org.json.JSONObject;

/**
 * User
 */
public class User {

    private String name;
    private String email;
    @JsonIgnore
    private ECKey keys;
    private Map<String, String> keysPlainText;
    private Map<String, JSONObject> ABEKeys;

    public User(String name, String email, ECKey ecKey) {
        this.setName(name);
        this.setEmail(email);
        this.setECKeys(ecKey);

        keysPlainText = new HashMap<String, String>();
        keysPlainText.put("private", ecKey.getPrivateKeyAsHex());
        keysPlainText.put("public", ecKey.getPublicKeyAsHex());
    }

    @JsonProperty("ABEKeys")
    public Map<String, JSONObject> getABEKeys() {
        return ABEKeys;
    }

    public void setABEKeys(Map<String, JSONObject> aBEKeys) {
        this.ABEKeys = aBEKeys;
    }

    public String getPublicECKey() {
        return this.keysPlainText.get("public");
    }

    @JsonProperty("ECKeys")
    public Map<String, String> getECKeysAsString() {
        return keysPlainText;
    }

    public void setECKeyAsString(Map<String, String> keys) {
        this.keysPlainText = keys;
    }

    @JsonIgnore
    public ECKey getECKeys() {
        return keys;
    }

    public void setECKeys(ECKey pairKeys) {
        this.keys = pairKeys;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String format = "{\n" +
            "\tname: %s, \n"+
            "\temail: %s, \n"+
            "\tprivate ECKey: %s, \n"+
            "\tpublic ECKey: %s\n" +
            "}";
        return String.format(format,name, email, keysPlainText.get("private"),
            keysPlainText.get("public"));
    }
}