package com.brunoarruda.hyperdcpabe;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import sg.edu.ntu.sce.sands.crypto.dcpabe.Ciphertext;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;

/**
 * CiphertextJSON
 */
public class CiphertextJSON extends Ciphertext {

    private static final long serialVersionUID = 1L;

    public CiphertextJSON(Ciphertext cipher) {
        super();
        setC0(cipher.getC0());

        boolean readAllBytes = false;

        // only way to recover data stored on a Ciphertext
        int i = 0;
        while(!readAllBytes) {
            try {
                setC1(cipher.getC1(i));
                setC2(cipher.getC2(i));
                setC3(cipher.getC3(i));
                i++;
            } catch (Exception e) {
                readAllBytes = true;
            }
        }
        setAccessStructure(cipher.getAccessStructure());
    }

    @JsonCreator
    public CiphertextJSON(@JsonProperty("c0") byte[] c0, @JsonProperty("c1") List<byte[]> c1x, @JsonProperty("c2") List<byte[]> c2x,
            @JsonProperty("c3") List<byte[]> c3x, @JsonProperty("accessStructure") AccessStructure accessStructure) {
        super();
        setC0(c0);
        setAllC1(c1x);
        setAllC2(c2x);
        setAllC3(c3x);
        setAccessStructure(accessStructure);
    }

    @Override
    @JsonProperty("c0")
    public byte[] getC0() {
        return super.getC0();
    }

    public void setC0(byte[] c0) {
        super.setC0(c0);
    }

    @JsonProperty("c1")
    public List<byte[]> getAllC1() {
        List<byte[]> c1 = new ArrayList<byte[]>();
        int i = 0;
        boolean readAllBytes = false;
        while (!readAllBytes) {
            try {
                c1.add(super.getC1(i));
                i++;
            } catch (Exception e) {
                readAllBytes = true;
            }
        }
        return c1;
    }

    public void setAllC1(List<byte[]> c1) {
        for (byte[] c1x : c1) {
            super.setC1(c1x);
        }
    }

    @JsonProperty("c2")
    public List<byte[]> getAllC2() {
        List<byte[]> c2 = new ArrayList<byte[]>();
        int i = 0;
        boolean readAllBytes = false;
        while (!readAllBytes) {
            try {
                c2.add(super.getC2(i));
                i++;
            } catch (Exception e) {
                readAllBytes = true;
            }
        }
        return c2;
    }

    public void setAllC2(List<byte[]> c2) {
        for (byte[] c2x : c2) {
            super.setC2(c2x);
        }
    }

    @JsonProperty("c3")
    public List<byte[]> getAllC3() {
        List<byte[]> c3 = new ArrayList<byte[]>();
        int i = 0;
        boolean readAllBytes = false;
        while (!readAllBytes) {
            try {
                c3.add(super.getC3(i));
                i++;
            } catch (Exception e) {
                readAllBytes = true;
            }
        }
        return c3;
    }

    public void setAllC3(List<byte[]> c3) {
        for (byte[] c3x : c3) {
            super.setC3(c3x);
        }
    }

    @Override
    @JsonProperty
    public AccessStructure getAccessStructure() {
        return super.getAccessStructure();
    }

    public void setAccessStructure(AccessStructure accessStructure) {
        super.setAccessStructure(accessStructure);
    }

}