pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEUsers.sol";
import "./Collection.sol";

contract SmartDCPABEFiles is Collection {

    struct Recording {
        uint64 serverID;
        bytes32 key;
        bytes32 hashing;
        uint64 timestamp;
    }

    struct Ciphertext {
        string policy;
        bytes c0;
        bytes c1;
        bytes c2;
        bytes c3;
    }

    struct FileServer {
        bytes32 domain;
        bytes32 path;
        uint16 port;
    }

    uint64 public numServers;
    mapping (address => string[]) fileNames;
    mapping (address => mapping(string => Recording)) files;
    mapping (address => mapping(string => Ciphertext)) ciphertexts;
    /* IDEIA: in order to allow ciphertext changes, ciphertext must be decoupled from Recording,
     * maybe in a map of the recording to a Ciphertext object
     */

    FileServer[] servers;
    SmartDCPABEUtility util;
    SmartDCPABEUsers users;

    constructor(address root) Collection(root) public {}

    function setContractDependencies(ContractType contractType, address addr) public onlyOwner {
        if (contractType == ContractType.UTILITY) {
            util = SmartDCPABEUtility(addr);
        } else if (contractType == ContractType.USERS) {
            users = SmartDCPABEUsers(addr);
        }
    }

    function addServer(string memory domain, string memory path, uint16 port) public returns (uint64 serverIndex) {
        servers.push(FileServer(util.stringToBytes32(domain), util.stringToBytes32(path), port));
        serverIndex = numServers;
        numServers++;
    }

    function addRecording (
        address addr,
        string memory filename,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing,
        uint64 timestamp
    )
        public
        onlyFileOwner(addr)
        validUser(addr)

    {
        if (files[addr][filename].timestamp == 0) {
            fileNames[addr].push(filename);
        }
        files[addr][filename] = Recording(serverID, key, hashing, timestamp);
    }

    function addRecordingCiphertext
    (
        address addr,
        string memory fileName,
        string memory policy,
        bytes memory c0,
        bytes memory c1,
        bytes memory c2,
        bytes memory c3
    )
        public
        onlyFileOwner(addr)
    {
        require (msg.sender == addr, "Only file owner is allowed to change file register.");
        ciphertexts[addr][fileName] = Ciphertext(policy, c0, c1, c2, c3);
    }

    function getCiphertext
    (
        address addr,
        string memory fileName
    )
        public
        view
        returns
    (
        string memory policy,
        bytes memory c0,
        bytes memory c1,
        bytes memory c2,
        bytes memory c3
    )
    {
        Ciphertext memory ct = ciphertexts[addr][fileName];
        return (
            ct.policy,
            ct.c0,
            ct.c1,
            ct.c2,
            ct.c3
        );
    }

    function getServerID(string memory domain) public view returns (int64) {
        bytes32 domainBytes32 = util.stringToBytes32(domain);
        for (uint64 i = 0; i < numServers; i++) {
            if (domainBytes32 == servers[i].domain) {
                return int64(i);
            }
        }
        return -1;
    }

    function getServer(uint64 index) public view returns (string memory domain, string memory path, uint16 port) {
        assert(index < numServers);
        FileServer storage s = servers[index];
        return (util.bytes32ToString(s.domain), util.bytes32ToString(s.path), s.port);
    }

    function getRecording
    (
        address addr,
        uint64 index
    )
        public
        view
        returns
    (
        string memory,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing,
        uint64 timestamp
    )
    {
        return getRecording(addr, fileNames[addr][index]);
    }

    function getRecording
    (
        address addr,
        string memory name
    )
        public
        view
        returns
    (
        string memory,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing,
        uint64 timestamp
    )
    {
        Recording storage r = files[addr][name];
        return (
            name,
            r.serverID,
            r.key,
            r.hashing,
            r.timestamp
        );
    }

    function getFileNameByIndex(address addr, uint64 index) public view returns (string memory) {
        return fileNames[addr][index];
    }

    function getFileCounting(address addr) public view returns (uint256) {
        return fileNames[addr].length;
    }

    modifier onlyFileOwner(address addr) {
        require (msg.sender == addr, "Operation not allowed. User must be the file owner");
        _;
    }

    modifier validUser(address addr) {
        require(users.isUser(addr), "User not registered");
        _;
    }

    modifier validFile(address addr, string memory fileName) {
        require(files[addr][fileName].timestamp != 0, "File does not exist.");
        _;
    }
}