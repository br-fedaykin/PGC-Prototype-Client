package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import sg.edu.ntu.sce.sands.crypto.dcpabe.Message;
import sg.edu.ntu.sce.sands.crypto.utility.Utility;

/**
 * Recording
 */
public class Recording {

    private String url;
    private String key;
    private String originalFileName;
    private String encryptedFileName;
    private String decryptedFileName;
    private Message AESKey;
    private CiphertextJSON ct;
    private String recordingFileName;
    private int BUFFER_SIZE = 1024;
    private long timestamp;
    private String signature;

    public Recording(String fileName, CiphertextJSON ct) {
        this.originalFileName = fileName;
        this.encryptedFileName = "(enc)" + fileName;
        this.decryptedFileName = "(dec)" + fileName;
        // gets only file name without extension
        this.setRecordingFileName(fileName.split("\\.\\w+?$")[0]);
        this.ct = ct;
        this.setSignature("Signature to proof ownership of id (EC public key)");
    }

    @JsonCreator
    public Recording(@JsonProperty("fileName") String fileName,
                     @JsonProperty("ciphertext") CiphertextJSON ct,
                     @JsonProperty("url") String url,
                     @JsonProperty("key") String key,
                     @JsonProperty("RecordingFileName") String recordingName,
                     @JsonProperty("timestamp") long timestamp) {
        this.originalFileName = fileName;
        this.encryptedFileName = "(enc)" + fileName;
        this.decryptedFileName = "(dec)" + fileName;
        this.ct = ct;
        this.url = url;
        this.key = key;
        this.recordingFileName = recordingName;
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRecordingFileName() {
        return recordingFileName;
    }

    public void setRecordingFileName(String recordingFileName) {
        this.recordingFileName = recordingFileName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public CiphertextJSON getCiphertext() {
        return ct;
    }

    public void setCiphertext(CiphertextJSON ct) {
        this.ct = ct;
    }

    public String getFileName() {
        return originalFileName;
    }

    public void setFileName(String fileName) {
        this.originalFileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void decrypt(Message m, String path) {
        this.AESKey = m;
        PaddedBufferedBlockCipher aes = Utility.initializeAES(AESKey.getM(), false);
        processDataWithBlockCipher(aes, path, encryptedFileName, originalFileName);
    }

    public void encryptFile(Message m, String path) {
        this.AESKey = m;
        PaddedBufferedBlockCipher aes = Utility.initializeAES(AESKey.getM(), true);
        processDataWithBlockCipher(aes, path, originalFileName, encryptedFileName);
    }

    private void processDataWithBlockCipher(PaddedBufferedBlockCipher aes, String path, String inputFileName, String outputFileName) {
        try (FileOutputStream fos = new FileOutputStream(path + outputFileName);
            FileInputStream fis = new FileInputStream(path + inputFileName);
            BufferedInputStream bis = new BufferedInputStream(fis)) {

            byte[] inBuff = new byte[aes.getBlockSize()];
            byte[] outBuff = new byte[aes.getOutputSize(inBuff.length)];
            int nbytes;
            while (-1 != (nbytes = bis.read(inBuff, 0, inBuff.length))) {
                int length1 = aes.processBytes(inBuff, 0, nbytes, outBuff, 0);
                fos.write(outBuff, 0, length1);
            }
            nbytes = aes.doFinal(outBuff, 0);
            fos.write(outBuff, 0, nbytes);
        } catch (IOException | DataLengthException | InvalidCipherTextException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void writeData(List<byte[]> data, String path) {
        try (FileOutputStream fos = new FileOutputStream(path + encryptedFileName)) {
            for (byte[] buff : data) {
                fos.write(buff);
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public List<byte[]> readData(String path) {
        List<byte[]> data = null;
        try (FileInputStream fis = new FileInputStream(path + encryptedFileName);
        BufferedInputStream bis = new BufferedInputStream(fis)) {
            data = new ArrayList<byte[]>();
            byte[] buff = new byte[BUFFER_SIZE];
            int readBytes;
            while((readBytes = bis.read(buff)) != -1) {
                if (readBytes != BUFFER_SIZE) {
                    data.add(Arrays.copyOf(buff, readBytes));
                } else {
                    data.add(buff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}