package com.brunoarruda.hyperdcpabe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public enum FileMode {
        OriginalFile,
        EncryptedFile,
    }

    private String domain;
    private String serverPath;
    private String key;
    private int port;
    private String originalFileName;
    private String encryptedFileName;
    private Message AESKey;
    private CiphertextJSON ct;
    private String recordingFileName;
    private int BUFFER_SIZE = 1024;
    private long timestamp;
    private String hash;
    private String filePath;
    private boolean originalFileChanged = false;
    private int recordingID;

    public Recording(String filePath, String fileName, CiphertextJSON ct) {
        this.filePath = filePath;
        this.originalFileName = fileName;
        this.encryptedFileName = "(enc)" + fileName;
        // gets only file name without extension
        this.setRecordingFileName(fileName.split("\\.\\w+?$")[0]);
        this.ct = ct;
        digestData();
    }

    @JsonCreator
    public Recording(@JsonProperty("fileName") String fileName, @JsonProperty("ciphertext") CiphertextJSON ct,
            @JsonProperty("domain") String domain, @JsonProperty("serverPath") String serverPath,
            @JsonProperty("port") int port, @JsonProperty("key") String key,
            @JsonProperty("AESKey") Message AESKey, @JsonProperty("recordingFileName") String recordingName,
            @JsonProperty("timestamp") long timestamp, @JsonProperty("hash") String hash,
            @JsonProperty("path") String filePath) {
        this.originalFileName = fileName;
        this.encryptedFileName = "(enc)" + fileName;
        this.ct = ct;
        this.domain = domain;
        this.serverPath = serverPath;
        this.port = port;
        this.key = key;
        this.recordingFileName = recordingName;
        this.timestamp = timestamp;
        this.filePath = filePath;
        this.hash = hash;
        this.AESKey = AESKey;
    }

    /**
     * @return the recordingID
     */
    public int getRecordingIndex() {
        return recordingID;
    }

    /**
     * @param recordingIndex the recordingID to set
     */
    public void setRecordingIndex(int recordingIndex) {
        this.recordingID = recordingIndex;
    }

    /**
     * @return the path in the server
     */
    public String getServerPath() {
        return serverPath;
    }

    /**
     * @param serverPath the path in the server to set
     */
    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    // FIX: clear text storage of ciphertext. Must be encrypted in a release version
    @JsonProperty("AESKey")
    public Message getAESKey() {
        return this.AESKey;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    @JsonIgnore
    public boolean isOriginalFileChanged() {
        return originalFileChanged;
    }

    public void setOriginalFileChanged(boolean originalFileChanged) {
        this.originalFileChanged = originalFileChanged;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void decrypt(Message m) {
        this.AESKey = m;
        PaddedBufferedBlockCipher aes = Utility.initializeAES(AESKey.getM(), false);
        File f = new File(filePath + originalFileName);
        f.delete();
        processDataWithBlockCipher(aes, filePath, encryptedFileName, originalFileName);
        System.out.println("Client - File decrypted: " + originalFileName);
    }

    public void encryptFile(Message m) {
        this.AESKey = m;
        PaddedBufferedBlockCipher aes = Utility.initializeAES(AESKey.getM(), true);
        File f = new File(filePath + encryptedFileName);
        f.delete();
        processDataWithBlockCipher(aes, filePath, originalFileName, encryptedFileName);
        System.out.println("Client - File encrypted: " + originalFileName);
    }

    private void processDataWithBlockCipher(PaddedBufferedBlockCipher aes, String path, String inputFileName,
            String outputFileName) {
        try (FileOutputStream fos = new FileOutputStream(path + outputFileName);
                FileInputStream fis = new FileInputStream(path + inputFileName);
                BufferedInputStream bis = new BufferedInputStream(fis)) {

            byte[] inBuff = new byte[aes.getBlockSize()];
            byte[] outBuff = new byte[aes.getOutputSize(inBuff.length)];
            int nbytes;
            while ((nbytes = bis.read(inBuff, 0, inBuff.length)) != -1) {
                int length1 = aes.processBytes(inBuff, 0, nbytes, outBuff, 0);
                fos.write(outBuff, 0, length1);
            }
            nbytes = aes.doFinal(outBuff, 0);
            fos.write(outBuff, 0, nbytes);
        } catch (IOException | DataLengthException | InvalidCipherTextException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void writeData(List<byte[]> data, FileMode mode) {
        String file = null;
        if (mode == FileMode.OriginalFile) {
            file = originalFileName;
        }
        if (mode == FileMode.EncryptedFile) {
            file = encryptedFileName;
        }
        writeData(data, file);
    }

    private void writeData(List<byte[]> data, String file) {
        File f = new File(filePath, file);
        f.delete();
        try (FileOutputStream fos = new FileOutputStream(filePath + file)) {
            for (byte[] buff : data) {
                fos.write(buff);
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public boolean hasFileChanged() {
        String lastHash = this.hash;
        digestData();
        boolean isDifferent = !lastHash.equals(this.hash);
        this.setOriginalFileChanged(isDifferent);
        return isDifferent;
    }

    private void digestData() {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            List<byte[]> dataArray = readData(FileMode.OriginalFile);
            int dataSize = 0;
            for (byte[] d : dataArray) {
                dataSize = dataSize + d.length;
            }
            byte[] data = new byte[dataSize];

            int bytesRead = 0;
            for (byte[] d : dataArray) {
                for (int i = 0; i < d.length; i++) {
                    data[bytesRead + i] = d[i];
                }
                bytesRead = bytesRead + d.length;
            }
            byte[] hash = sha256.digest(data);
            this.hash = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public List<byte[]> readData(FileMode mode) {
        String file = null;
        if (mode == FileMode.OriginalFile) {
            file = originalFileName;
        }
        if (mode == FileMode.EncryptedFile) {
            file = encryptedFileName;
        }
        return readData(file);
    }

    private List<byte[]> readData(String file) {
        List<byte[]> data = null;
        try (FileInputStream fis = new FileInputStream(filePath + file);
        BufferedInputStream bis = new BufferedInputStream(fis)) {
            data = new ArrayList<byte[]>();
            byte[] buff = new byte[BUFFER_SIZE];
            int readBytes;
            while((readBytes = bis.read(buff)) != -1) {
                if (readBytes != BUFFER_SIZE) {
                    data.add(Arrays.copyOf(buff, readBytes));
                } else {
                    data.add(buff);
                    buff = new byte[BUFFER_SIZE];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}