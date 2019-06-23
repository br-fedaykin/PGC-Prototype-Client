package com.brunoarruda.hyper_dcpabe;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.bitcoinj.core.ECKey;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;

/**
 * Certifier
 */
public class Certifier extends User {

    private AuthorityKeys authorityABEKeys;

    public Certifier(String name, String email, ECKey keys) {
        super(name, email, keys);
    }

    @JsonProperty("AuthorityKeys")
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