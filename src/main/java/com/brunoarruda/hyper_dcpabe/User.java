package com.brunoarruda.hyper_dcpabe;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.bitcoinj.core.ECKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.PersonalKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

/**
 * User
 */
public class User {

    private String name;
    private String email;
    private ECKey keys;
    private Map<String, String> keysPlainText;
    private PersonalKeys ABEKeys;
    private Map<String, Map<String, PublicKey>> publishedABEKeys;

    public User(String name, String email, ECKey ecKey) {
        this.setName(name);
        this.setEmail(email);
        this.setECKeys(ecKey);

        keysPlainText = new HashMap<String, String>();
        keysPlainText.put("private", ecKey.getPrivateKeyAsHex());
        keysPlainText.put("public", ecKey.getPublicKeyAsHex());
    }

    @JsonProperty("PersonalABEKeys")
    public PersonalKeys getABEKeys() {
        return ABEKeys;
    }

    public Map<String, PublicKey> getPublicABEKeys(String authority) {
        return publishedABEKeys.get(authority);
    }

    public Map<String, Map<String, PublicKey>> getAllPulicABEKeys() {
        return publishedABEKeys;
    }

    public void setABEKeys(PersonalKeys ABEKeys) {
        this.ABEKeys = ABEKeys;
    }

    public String getPublicECKey() {
        return this.keysPlainText.get("public");
    }

    @JsonProperty("ECKeys")
    public Map<String, String> getECKeysAsString() {
        return keysPlainText;
    }

    public String getPrivateECKey() {
        return keysPlainText.get("private");
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

	public void addPublicKeys(String authority, Map<String, PublicKey> keys) {
        this.publishedABEKeys.put(authority, keys);
	}

	public boolean hasPublicKeysOfAuthority(String authority) {
		return publishedABEKeys.containsKey(authority);
	}
}