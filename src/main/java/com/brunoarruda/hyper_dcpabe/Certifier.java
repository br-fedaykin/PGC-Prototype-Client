package com.brunoarruda.hyper_dcpabe;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.bitcoinj.core.ECKey;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;

/**
 * Certifier
 */
public class Certifier extends User {

    private AuthorityKeys authorityABEKeys;

    @JsonCreator
    public Certifier(@JsonProperty("name") String name, @JsonProperty("userID") String userID,
        @JsonProperty("email") String email, @JsonProperty("ECKeys") Map<String, String> ECKeys,
        @JsonProperty("authorityKeys") AuthorityKeys authorityABEKeys) {
        super(name, userID, email, ECKeys);
        setAuthorityABEKeys(authorityABEKeys);
    }

    public Certifier(String name, String email, ECKey keys) {
        super(name, email, keys);
    }

    @JsonProperty("authorityKeys")
    public AuthorityKeys getAuthorityABEKeys() {
        return authorityABEKeys;
    }

    public void setAuthorityABEKeys(AuthorityKeys authorityABEKeys) {
        this.authorityABEKeys = authorityABEKeys;
    }

    public Certifier(User user) {
        this(user.getName(), user.getEmail(), user.getECKeys());
    }
}