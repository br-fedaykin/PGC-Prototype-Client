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
        Ciphertext ct;
    }

    struct Ciphertext {
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
    mapping (address => bytes32[]) fileNames;
    mapping (address => mapping(bytes32 => Recording)) files;

    FileServer[] servers;
    SmartDCPABEUtility util;
    SmartDCPABEUsers users;

    constructor(address root) Collection(root) public {}

    function setContractDependencies(ContractType contractType, address addr) public {
        require(msg.sender == owner, "Operation not allowed. Must be the done by the owner of the contract.");
        if (contractType == ContractType.UTILITY) {
            util = SmartDCPABEUtility(addr);
        } else if (contractType == ContractType.USERS) {
            users = SmartDCPABEUsers(addr);
        }
    }

    // TODO: dismember server logic from this contract

    function addServer(string memory domain, string memory path, uint16 port) public returns (uint64 serverIndex) {
        servers.push(FileServer(util.stringToBytes32(domain), util.stringToBytes32(path), port));
        serverIndex = numServers;
        numServers++;
    }

    function addRecording(
        address addr,
        // recordings parameters
        bytes32 filename,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing,
        uint64 timestamp,
        // ciphertext parameters
        bytes memory c0,
        bytes memory c1,
        bytes memory c2,
        bytes memory c3
    )
        public
    {
        assert(users.isUser(addr));
        files[addr][filename] = Recording(serverID, key, hashing, timestamp, Ciphertext(c0, c1, c2, c3));
        fileNames[addr].push(filename);
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
        uint64 timestamp,
        bytes memory c0,
        bytes memory c1,
        bytes memory c2,
        bytes memory c3
    )
    {
        return getRecording(addr, fileNames[addr][index]);
    }

    function getRecording
    (
        address addr,
        bytes32 name
    )
        public
        view
        returns
    (
        string memory,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing,
        uint64 timestamp,
        bytes memory c0,
        bytes memory c1,
        bytes memory c2,
        bytes memory c3
    )
    {
        Recording storage r = files[addr][name];
        return (
            util.bytes32ToString(name),
            r.serverID,
            r.key,
            r.hashing,
            r.timestamp,
            r.ct.c0,
            r.ct.c1,
            r.ct.c2,
            r.ct.c3
        );
    }

    function getFileIndex(address addr) public view returns (uint256) {
        return fileNames[addr].length;
    }
}