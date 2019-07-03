package com.brunoarruda.hyperdcpabe;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import sg.edu.ntu.sce.sands.crypto.dcpabe.PersonalKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;

/**
 * PersonalKeysJSON
 */
public class PersonalKeysJSON extends PersonalKeys {
    @JsonProperty("userID")
    private String userID;
    @JsonProperty("personalKeys")
    private Map<String, PersonalKey> personalKeys;

    public PersonalKeysJSON(String userID) {
        super(userID);
        personalKeys = new HashMap<String, PersonalKey>();
    }

    @JsonCreator
    public PersonalKeysJSON(@JsonProperty("userID") String userID,
                            @JsonProperty("personalABEKeys") Map<String, PersonalKey> personalKeys) {
        super(userID);
        this.personalKeys = personalKeys;
    }

    @Override
    public String getUserID() {
        return super.getUserID();
    }

    @JsonProperty("personalABEKeys")
    public Map<String, PersonalKey> getPersonalKeys() {
        return personalKeys;
    }
}