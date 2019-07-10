package com.brunoarruda.hyperdcpabe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import sg.edu.ntu.sce.sands.crypto.dcpabe.PersonalKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;

/**
 * PersonalKeysJSON
 */
public class PersonalKeysJSON extends PersonalKeys {
    private Map<String, PersonalKey> personalKeys;

    public PersonalKeysJSON(String userID) {
        super(userID);
        personalKeys = new HashMap<String, PersonalKey>();
    }

    @JsonCreator
    public PersonalKeysJSON(@JsonProperty("userID") String userID,
    @JsonProperty("personalKeys") Map<String, PersonalKey> personalKeys) {
        super(userID);
        this.personalKeys = personalKeys;
    }

    @Override
    public String getUserID() {
        return super.getUserID();
    }

    @JsonProperty
    public Map<String, PersonalKey> getPersonalKeys() {
        return personalKeys;
    }

    @Override
    public void addKey(PersonalKey pkey) {
        personalKeys.put(pkey.getAttribute(), pkey);
    }

    @JsonIgnore
    public int size() {
        return personalKeys.size();
    }

    @Override
    @JsonIgnore
    public Collection<String> getAttributes() {
        return personalKeys.keySet();
    }

    @Override
    @JsonIgnore
    public PersonalKey getKey(String attribute) {
        return personalKeys.get(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonalKeysJSON that = (PersonalKeysJSON) o;
        return Objects.equals(getUserID(), that.getUserID()) && Objects.equals(personalKeys, that.personalKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), personalKeys);
    }
}