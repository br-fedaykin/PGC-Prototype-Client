package com.brunoarruda.hyperdcpabe.blockchain;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.brunoarruda.hyperdcpabe.CiphertextJSON;
import com.brunoarruda.hyperdcpabe.Recording;
import com.brunoarruda.hyperdcpabe.Client.RequestStatus;
import com.brunoarruda.hyperdcpabe.blockchain.SmartDCPABERequests.PendingRequestIndexChangedEventResponse;
import com.brunoarruda.hyperdcpabe.blockchain.SmartDCPABERequests.PendingRequesterIndexChangedEventResponse;
import com.brunoarruda.hyperdcpabe.io.FileController;
import com.brunoarruda.hyperdcpabe.monitor.ExecutionProfiler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.ethereum.crypto.ECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

public class BlockchainConnection {

    // TODO: send message to logger instead of System.out
    private static final Logger log = LoggerFactory.getLogger(BlockchainConnection.class);
    private static final ExecutionProfiler profiler = ExecutionProfiler.getInstance();

    private String dataPath = "blockchain";
    private FileController fc;

    static private final byte[] seed = "Honk Honk".getBytes();
    static private final SecureRandom random = new SecureRandom(seed);
    private final String networkURL;

    private SmartDCPABERoot scRoot;
    private SmartDCPABEAuthority scAuthority;
    private SmartDCPABEFiles scFiles;
    private SmartDCPABEKeys scKeys;
    private SmartDCPABERequests scRequests;
    private SmartDCPABEUsers scUsers;
    private SmartDCPABEUtility scUtility;
    private Map<String, String> contractAddress;
    private final Web3j web3j;
    private ContractGasProvider dgp;

    public String getBlockchainDataPath() {
        return fc.getDataDirectory() + dataPath + "\\";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public BlockchainConnection(String networkURL) {
        this(networkURL, null);
    }

    // TODO: refactor URL as a POM field or command line/file config
    // POM field seems better, as it would allow different value for deploy/test
    // cycles
    public BlockchainConnection(String networkURL, Map<String, String> contractAddress) {
        profiler.start(this.getClass(), "constructor");
        this.networkURL = networkURL;
        web3j = Web3j.build(new HttpService(networkURL));
        fc = FileController.getInstance();
        dgp = new DefaultGasProvider();
        if (contractAddress == null) {
            this.contractAddress = new HashMap<String, String>();
        } else {
            this.contractAddress = contractAddress;
        }
        profiler.end();
	}

	public Map<String, String> deployContracts(Credentials credentials) {
        profiler.start(this.getClass(), "deployContracts");
        try {
            scRoot = SmartDCPABERoot.deploy(web3j, credentials, dgp).send();
            String rootAddress = scRoot.getContractAddress();
            scAuthority = SmartDCPABEAuthority.deploy(web3j, credentials, dgp, rootAddress).send();
            scFiles = SmartDCPABEFiles.deploy(web3j, credentials, dgp, rootAddress).send();
            scKeys = SmartDCPABEKeys.deploy(web3j, credentials, dgp, rootAddress).send();
            scRequests = SmartDCPABERequests.deploy(web3j, credentials, dgp, rootAddress).send();
            scUsers = SmartDCPABEUsers.deploy(web3j, credentials, dgp, rootAddress).send();
            scUtility = SmartDCPABEUtility.deploy(web3j, credentials, dgp, rootAddress).send();

            contractAddress.put("Root", rootAddress);
            contractAddress.put("Authority", scAuthority.getContractAddress());
            contractAddress.put("Files", scFiles.getContractAddress());
            contractAddress.put("Keys", scKeys.getContractAddress());
            contractAddress.put("Requests", scRequests.getContractAddress());
            contractAddress.put("Users", scUsers.getContractAddress());
            contractAddress.put("Utility", scUtility.getContractAddress());

            List<String> addressList = new ArrayList<String>();
            List<BigInteger> indexes = new ArrayList<BigInteger>();
            String[] keys = {"Authority", "Files", "Keys", "Requests", "Users", "Utility"};
            for (int i = 0; i < keys.length; i++) {
                addressList.add(contractAddress.get(keys[i]));
                indexes.add(BigInteger.valueOf(i));
            }
            TransactionReceipt tr = scRoot.setAllContracts(indexes, addressList).send();
            profiler.addGasCost(tr.getGasUsed());
        } catch (Exception e) {
            log.error("Não foi possível publicar os Smart Contracts na Blockchain.", e);
        }
        profiler.end();
        return contractAddress;
    }

    public BlockchainConnection loadContracts(Credentials credentials) {
        // TODO: check local addresses against the addresses returned by getAddresses() function from scRoot
        profiler.start(this.getClass(), "loadContracts");
        String address = contractAddress.get("Authority");
        scAuthority = SmartDCPABEAuthority.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Files");
        scFiles = SmartDCPABEFiles.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Keys");
        scKeys = SmartDCPABEKeys.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Requests");
        scRequests = SmartDCPABERequests.load(address, web3j, credentials, dgp);
        address = contractAddress.get("Users");
        scUsers = SmartDCPABEUsers.load(address, web3j, credentials, dgp);
        profiler.end();
        return this;
    }

    public ECKey generateECKeys(String privateKey) {
        profiler.start(this.getClass(), "generateECKeys");
        ECKey keys = null;
        if (privateKey != null) {
            keys = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        } else {
            keys = new ECKey(random);
        }
        profiler.end();
        return keys;
    }
    public Map<String, PublicKey> getABEPublicKeys(String authority, String[] attributes) {
        // TODO: alternar isso para um JSON
        profiler.start(this.getClass(), "getABEPublicKeys");
        String authName = authority.split("-")[0];
        String address = authority.split("-")[1];
        try {
            Tuple4<String, String, String, BigInteger> certifier = scAuthority.getCertifier(address).send();
            if (certifier.getValue4().equals(BigInteger.ZERO)) {
                log.info("A autoridade {} não publicou nenhum atributo.", authName);
                profiler.end();
                return null;
            } else {
                Map<String, PublicKey> keys = new HashMap<String, PublicKey>();
                for (String attr : attributes) {
                    Tuple3<String, byte[], byte[]> keyData = scKeys.getPublicKey(address, attr).send();
                    keys.put(attr, new PublicKey(keyData.getValue2(), keyData.getValue3()));
                }
                profiler.end();
                return keys;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        profiler.end();
        return null;
    }

    public int publishData(String userID, ObjectNode obj) {
        profiler.start(this.getClass(), "publishData");
        String domain = obj.get("domain").asText();
        String serverPath = obj.get("serverPath").asText();
        BigInteger port = obj.get("port").bigIntegerValue();
        BigInteger serverID = null;
        TransactionReceipt tr = null;
        try {
            serverID = scFiles.getServerID(domain).send();
            if (serverID.longValue() == -1) {
                profiler.start(this.getClass(), "addServer");
                tr = scFiles.addServer(domain, serverPath, port).send();
                profiler.addGasCost(tr.getGasUsed());
                profiler.end();
                serverID = scFiles.getServerID(domain).send();
            }
        } catch (Exception e) {
            log.error("Não foi possível adicionar {} à lista de servidores na Blockchain", domain, e);
        }
        String address = obj.get("address").asText();
        BigInteger timestamp = obj.get("timestamp").bigIntegerValue();
        String fileName = obj.get("fileName").asText();
        int numRecording = -1;
        String policy = obj.get("ciphertext").get("accessStructure").get("policy").asText();
        /* BUG: decoded binary format becomes corrupt in blockchain, changing c0 to string binary format.
         * The problem could be in negative byte value, as string binary format only have positive bytes
         */

        byte[] c0 = obj.get("ciphertext").get("c0").asText().getBytes();
        byte[] c1 = obj.get("ciphertext").get("c1").toString().getBytes();
        byte[] c2 = obj.get("ciphertext").get("c2").toString().getBytes();
        byte[] c3 = obj.get("ciphertext").get("c3").toString().getBytes();
        try {
            BigInteger numRecording_ = scFiles.getFileCounting(address).send();
            byte[] key = obj.get("key").binaryValue();
            byte[] hash = obj.get("hash").binaryValue();
            profiler.start(this.getClass(), "addRecording");
            tr = scFiles.addRecording(address, fileName, serverID, key, hash, timestamp).send();
            profiler.addGasCost(tr.getGasUsed());
            profiler.end();
            profiler.start(this.getClass(), "addCiphertext");
            scFiles.addRecordingCiphertext(address, fileName, policy, c0, c1, c2, c3).send();
            profiler.addGasCost(tr.getGasUsed());
            profiler.end();
            log.info("Arquivo publicado: " + fileName);
            numRecording = numRecording_.intValue();
        } catch (Exception e) {
            log.error("Não foi possível publicar o arquivo {}", fileName, e);
        }
        profiler.end();
        return numRecording;
    }

    public void publishABEKeys(ObjectNode obj) {
        // TODO: criar transação ao invés de salvar arquivo
        // TODO: checar existência de certificador com o endereço fornecido
        profiler.start(this.getClass(), "publishABEKeys");
        String address = obj.remove("address").asText();
        Iterator<String> it = obj.fieldNames();
        while (it.hasNext()) {
            String attrName = it.next();
            JsonNode attrNode = obj.get(attrName);
            byte[] eg1g1ai = Base64.getDecoder().decode(attrNode.get("eg1g1ai").asText());
            byte[] g1yi = Base64.getDecoder().decode(attrNode.get("g1yi").asText());
            try {
                if (eg1g1ai.length < 97 || eg1g1ai.length > 127) {
                   throw new RuntimeException("Key error: eg1g1ai does not fit in four sized words");
                }
                if (g1yi.length < 97 || g1yi.length > 127) {
                    throw new RuntimeException("Key error: g1yi does not fit in four sized words");
                }
                TransactionReceipt tr = scKeys.addPublicKey(address, attrName, eg1g1ai, g1yi).send();
                profiler.addGasCost(tr.getGasUsed());
                log.info("Chave pública do atributo {} publicado: ", attrName);
            } catch (Exception e) {
                log.error("erro durante publicação de chave ABE", e);
            }
        }
        profiler.end();
    }

    public void publishAuthority(ObjectNode obj) {
        profiler.start(this.getClass(), "publishAuthority");
        String address = obj.get("address").asText();
        String name = obj.get("name").asText();
        String email = obj.get("email").asText();
        try {
            TransactionReceipt tr = scAuthority.addCertifier(address, name, email).send();
            profiler.addGasCost(tr.getGasUsed());
            log.info("Autoridade publicada: {}.", name);
        } catch (Exception e) {
            log.error("Não foi possível publicar o certificador {} na Blockchain.", name, e);
        }
        profiler.end();
    }

    public void publishUser(ObjectNode obj) {
        profiler.start(this.getClass(), "publishUser");
        String address = obj.get("address").asText();
        String name = obj.get("name").asText();
        String email = obj.get("email").asText();
        try {
            TransactionReceipt tr = scUsers.addUser(address, name, email).send();
            profiler.addGasCost(tr.getGasUsed());
            log.info("Usuário publicado: {}.", name);
        } catch (Exception e) {
            log.error("Não foi possível publicar o usuário {} na Blockchain.", name, e);
        }
        profiler.end();
    }

    private CiphertextJSON getCiphertext(String user, String fileName) throws Exception {
        profiler.start(this.getClass(), "getCiphertext");
        CiphertextJSON ct = null;
        Tuple5<String, byte[], byte[], byte[], byte[]> ciphertextData;
        ciphertextData = scFiles.getCiphertext(user, fileName).send();
        if (!ciphertextData.getValue1().equals("")) {
            AccessStructure as = AccessStructure.buildFromPolicy(ciphertextData.getValue1());
            String c0_ = new String(ciphertextData.getValue2(), "UTF-8");
            byte[] c0 = Base64.getDecoder().decode(c0_);
            String c1x_ = new String(ciphertextData.getValue3(), "UTF-8");
            List<byte[]> c1x = new ArrayList<>();
            for (String x : c1x_.replace("[", "").replace("]", "").split(",")) {
                c1x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            String c2x_ = new String(ciphertextData.getValue4(), "UTF-8");
            List<byte[]> c2x = new ArrayList<>();
            for (String x : c2x_.replace("[", "").replace("]", "").split(",")) {
                c2x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            String c3x_ = new String(ciphertextData.getValue5(), "UTF-8");
            List<byte[]> c3x = new ArrayList<>();
            for (String x : c3x_.replace("[", "").replace("]", "").split(",")) {
                c3x.add(Base64.getDecoder().decode(x.replace("\"", "")));
            }
            ct = new CiphertextJSON(c0, c1x, c2x, c3x, as);
        }
        profiler.end();
        return ct;
    }

    public Recording getRecording(String user, String fileName) {
        profiler.start(this.getClass(), "getRecording");
        Recording r = null;
        try {
            Tuple5<String, BigInteger, byte[], byte[], BigInteger> recordingData;
            recordingData = scFiles.getRecording(user, fileName).send();
            if (recordingData.getValue5().intValue() != 0) {
                String key = Base64.getEncoder().encodeToString(recordingData.getValue3());
                String hash = Base64.getEncoder().encodeToString(recordingData.getValue4());
                long timestamp = recordingData.getValue5().longValue();
                Tuple3<String, String, BigInteger> serverData = scFiles.getServer(recordingData.getValue2()).send();
                String domain = serverData.getValue1();
                String serverPath = serverData.getValue2();
                int port = serverData.getValue3().intValue();
                String recordingFN = fileName.split("\\.")[0];
                CiphertextJSON ct = getCiphertext(user, fileName);
                if (ct != null) {
                    r = new Recording(fileName, ct, domain, serverPath, port, key, null, recordingFN, timestamp, hash, null, false, false);
                } else {
                    log.error("File {} found but ciphertext is not published.", fileName);
                }
            } else {
                log.error("File {} not found.\n", fileName);
            }
        } catch (Exception e) {
            log.error("Houve um problema ao tentar obter os metadados do arquivo {}.", fileName, e);
        }
        profiler.end();
        return r;
    }

    public ArrayNode getAttributeRequests(String authority, String address, int listSizeLocal) {
        profiler.start(this.getClass(), "getAttributeRequests");
        ArrayNode requests = fc.getMapper().createArrayNode();
        try {
            for (int i = 0; i < listSizeLocal; i++) {
                /*
                 * NOTE: this could lead to error, since it is expected to always have the
                 * request on blockchain, which could not be true if the contract is a new one.
                 * Needs more work to stablish a migration model that does not break the client
                 */
                BigInteger status = scRequests.getRequestStatus(authority, address, BigInteger.valueOf(i)).send();
                requests.add(status);
            }
            BigInteger numRequests = scRequests.getRequestListSize(authority, address).send();
            for (int i = listSizeLocal; i < numRequests.intValue(); i++) {
                Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>> requestTuple;
                requestTuple = scRequests.getRequest(authority, address, BigInteger.valueOf(i)).send();
                ObjectNode request = fc.getMapper().createObjectNode();
                request.put("status", requestTuple.getValue1().intValue());
                request.put("index", requestTuple.getValue2().intValue());
                request.put("timestamp", requestTuple.getValue3());
                request.put("responseTimestamp", requestTuple.getValue4());
                ArrayNode attributes = request.putArray("attributes");
                for (byte[] attrName : requestTuple.getValue5()) {
                    attributes.add(trimmedToString(attrName));
                }
                requests.add(request);
            }
        } catch (Exception e) {
            log.error("Houve um erro ao tentar obter requisições de atributos feitas pelo usuário {} à autoridade {}. ", authority, address, e);
        }
        profiler.end();
        return requests;
    }

    // NOTE: check if this code and others could be confined in a util class
    private String trimmedToString(byte[] data) throws UnsupportedEncodingException {
        return new String(data, "UTF-8").replaceFirst("\u0000+$", "");
    }

    public ObjectNode publishAttributeRequest(String authority, String address, List<String> attributes) {
        profiler.start(this.getClass(), "publishAttributeRequest");
        List<byte[]> attributes_ = new ArrayList<byte[]>();
        attributes.forEach(s -> {
            try {
                attributes_.add(Arrays.copyOf(s.getBytes("UTF-8"), 32));
            } catch (UnsupportedEncodingException e) {
                log.error("Não foi possível transformar o nome dos atributos em uma lista de bytes", e);
            }
        });
        ObjectNode request = null;
        try {
            BigInteger index = scRequests.getRequestListSize(authority, address).send();
            long timestamp = System.currentTimeMillis();
            TransactionReceipt tr = scRequests.addRequest(authority, address, BigInteger.valueOf(timestamp), attributes_).send();
            profiler.addGasCost(tr.getGasUsed());
            request = fc.getMapper().createObjectNode();
            request.put("index", index);
            request.put("status", BigInteger.ZERO);
            request.put("timestamp", timestamp);
            ArrayNode attributesNode = request.putArray("attributes");
            attributes.forEach(s -> attributesNode.add(s));
        } catch (Exception e) {
            log.error("Não foi possível publicar a requisição de atributos feita pelo usuário {} à autoridade {}", address, authority, e);
        }
        profiler.end();
        return request;
    }

    public boolean userExists(String address) {
        profiler.start(this.getClass(), "userExists");
        Tuple3<String, String, String> user = null;
        try {
            user = scUsers.getUser(address).send();
        } catch (Exception e) {
            log.error("Não foi possível averiguar se o usuário {} exite. ", address, e);
        }
        profiler.end();
        return user != null && !user.getValue1().equals("");
    }

    public ArrayNode getAttributeRequestsForUser(String userID, String status) {
        // TODO: store last timestamp of checking to allow early exit of loop in smart
        // contract
        profiler.start(this.getClass(), "getAttributeRequestsForUser");
        String path = getBlockchainDataPath() + "AttributeRequest\\";
        String[] authorities = new File(path).list();
        ArrayNode allRequests = fc.getMapper().createArrayNode();
        for (String auth : authorities) {
            String authPath = path + auth + "\\";
            ArrayNode userRequests = (ArrayNode) fc.loadAsJSON(authPath, userID + ".json");
            {
                for (Iterator<JsonNode> iter = userRequests.elements(); iter.hasNext();) {
                    JsonNode r = iter.next();
                    if (!r.get("status").asText().equals(status)) {
                        iter.remove();
                    }
                }
            }
            allRequests.addAll(userRequests);
        }
        profiler.end();
        return allRequests;
    }

    public ArrayNode getAttributeRequestsForCertifier(String authority, String status) {
        profiler.start(this.getClass(), "getAttributeRequestsForCertifier");
        String path = getBlockchainDataPath() + "AttributeRequest\\" + authority + "\\";
        ArrayNode allRequests = fc.getMapper().createArrayNode();
        File f = new File(path);
        for (String user : f.list()) {
            ArrayNode userRequests = (ArrayNode) fc.loadAsJSON(path, user);
            Iterator<JsonNode> iter = userRequests.elements();
            while (iter.hasNext()) {
                JsonNode element = iter.next();
                if (!element.get("status").asText().equals(status)) {
                    iter.remove();
                }
            }
            allRequests.addAll(userRequests);
        }
        profiler.end();
        return allRequests;
    }

    public Map<String, int[]> publishAttributeRequestUpdate(String authority, String user, BigInteger pendingRequesterIndex,
            BigInteger pendingRequestIndex, RequestStatus newStatus) {
        profiler.start(this.getClass(), "publishAttributeRequestUpdate");
        TransactionReceipt tx = null;
        Map<String, int[]> changedIndexes = new HashMap<>();
        try {
            tx = scRequests.processRequest(authority, pendingRequesterIndex,
                    pendingRequestIndex, BigInteger.valueOf(newStatus.getValue())).send();
            profiler.addGasCost(tx.getGasUsed());
            log.info("Requisição de atributo feita a {}. Resultado: {}.", authority.substring(0, 6), newStatus);
        } catch (Exception e) {
            log.error("Não foi possível alterar o status da publicação feita a {}", authority.substring(0, 6), e);
        }
        if (tx != null) {
            List<PendingRequesterIndexChangedEventResponse> changedRequesterIndexes;
            changedRequesterIndexes = scRequests.getPendingRequesterIndexChangedEvents(tx);
            if (changedRequesterIndexes.size() > 0) {
                int[] requesterChanges = { changedRequesterIndexes.get(0).oldIndex.intValue(),
                        changedRequesterIndexes.get(0).newIndex.intValue() };
                changedIndexes.put("requester", requesterChanges);
            }
            List<PendingRequestIndexChangedEventResponse> changedRequestIndexes;
            changedRequestIndexes = scRequests.getPendingRequestIndexChangedEvents(tx);
            if (changedRequestIndexes.size() > 0) {
                int[] requestChanges = {changedRequestIndexes.get(0).oldIndex.intValue(),
                    changedRequestIndexes.get(0).newIndex.intValue()};
                changedIndexes.put("request", requestChanges);
            }
        }
        profiler.end();
        return changedIndexes;
    }


    public String getNetworkURL() {
        return networkURL;
    }

    // NOTE: function not used
    public int getAttributeRequestListSize(ObjectNode msg) {
        profiler.start(this.getClass(), "getAttributeRequestListSize");
        String authority = msg.get("authority").asText();
        String address = msg.get("address").asText();
        int numRequests = -1;
        try {
            BigInteger numRequests_ = scRequests.getRequestListSize(authority, address).send();
            numRequests = numRequests_.intValue();
        } catch (Exception e) {
            String base_str = "Houve um erro ao tentar verificar o tamanho da lista de requisições de atributos feitas pelo usuário {} ao certificador {}.";
            log.error(base_str, authority, address, e);
        }
        profiler.end();
        return numRequests;
    }

    public void syncAttributeRequestCache(String address, Map<String, ArrayNode> requestCache, String authority) {
        profiler.start(this.getClass(), "syncAttributeRequestCache");
        if (!userExists(address)) {
            log.error("Usuário {} não existe na Blockchain", address);
            profiler.end();
            return;
        }
        ArrayNode cache = requestCache.get(authority);
        if (cache == null) {
            cache = fc.getMapper().createArrayNode();
            requestCache.put(authority, cache);
        }
        int listSizeLocal = cache.size();
        ArrayNode requests = getAttributeRequests(authority, address, listSizeLocal);

        for (int i = 0; i < listSizeLocal; i++) {
            ObjectNode cachedRequest = (ObjectNode) cache.get(i);
            JsonNode status = requests.remove(0);
            String base_str = "Requisição com timestamp {} mudou de {} para {}.";
            if (cachedRequest.get("status").asInt() != status.asInt()) {
                log.info(base_str, cachedRequest.get("timestamp").asInt(),
                RequestStatus.valueOf(cachedRequest.get("status").asInt()), RequestStatus.valueOf(status.asInt()));
                cachedRequest.replace("status", status);
            }
        }
        cache.addAll(requests);
        profiler.end();
    }

    public ArrayNode getPendingAttributeRequests(String authority, String address, List<BigInteger> pendingRequests) {
        profiler.start(this.getClass(), "getPendingAttributeRequests");
        ArrayNode requests = fc.getMapper().createArrayNode();
        try {
            for (int i = 0; i < pendingRequests.size(); i++) {
                Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>> requestTuple;
                requestTuple = scRequests.getRequest(authority, address, pendingRequests.get(i)).send();
                ObjectNode request = fc.getMapper().createObjectNode();
                request.put("pendingIndex", i);
                request.put("status", requestTuple.getValue1().intValue());
                request.put("index", requestTuple.getValue2().intValue());
                request.put("timestamp", requestTuple.getValue3());
                request.put("responseTimestamp", requestTuple.getValue4());
                ArrayNode attributes = request.putArray("attributes");
                for (byte[] attrName : requestTuple.getValue5()) {
                    attributes.add(trimmedToString(attrName));
                }
                requests.add(request);
            }
        } catch (Exception e) {
            String base_str = "Houve um erro ao verificar por requisições atributos feitas pelo usuário {} ao certificador {}.";
            log.error(base_str, authority, address, e);
        }
        profiler.end();
        return requests;
    }

    @SuppressWarnings("unchecked")
    public void syncPendingAttributeRequests(String authority, Map<String, ObjectNode> requestCache) {
        profiler.start(this.getClass(), "syncPendingAttributeRequests");
        if (!certifierExists(authority)) {
            log.error("Certificador não existe na Blockchain: ", authority);
            profiler.end();
            return;
        }
        BigInteger numRequesters;
        try {
            numRequesters = scRequests.getPendingRequesterListSize(authority).send();
            for (int i = 0; i < numRequesters.intValue(); i++) {
                // FIX: return proper address with valid Checksum. Currently the address is all lowercase
                String address = scRequests.getPendingRequesterAddress(authority, BigInteger.valueOf(i)).send();
                ObjectNode cacheWrapper = requestCache.get(address);
                if (cacheWrapper == null) {
                    cacheWrapper = fc.getMapper().createObjectNode();
                    requestCache.put(address, cacheWrapper);
                    cacheWrapper.put("index", i);
                    cacheWrapper.put("address", address);
                }
                List<BigInteger> pendingRequestsIndexes = scRequests.getPendingList(authority, address).send();
                ArrayNode pendingRequests = getPendingAttributeRequests(authority, address, pendingRequestsIndexes);
                cacheWrapper.withArray("requests").removeAll().addAll(pendingRequests);
            }
        } catch (Exception e) {
            log.error("Houve um erro.", e);
        }
        profiler.end();
    }

    private boolean certifierExists(String address) {
        profiler.start(this.getClass(), "certifierExists");
        Tuple4<String, String, String, BigInteger> certifier = null;
        try {
            certifier = scAuthority.getCertifier(address).send();
        } catch (Exception e) {
            log.error("Não foi possível verificar se o certificador {} existe.", address, e);
        }
        profiler.end();
        return certifier != null && !certifier.getValue1().equals("");
    }
}
