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
        string base64C0;
        string base64C1;
        string base64C2;
        string base64C3;
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
        uint40 timestamp,
        // ciphertext parameters
        string memory c0,
        string memory c1,
        string memory c2,
        string memory c3
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
        string memory c0,
        string memory c1,
        string memory c2,
        string memory c3
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
        string memory c0,
        string memory c1,
        string memory c2,
        string memory c3
    )
    {
        Recording storage r = files[addr][name];
        return (
            util.bytes32ToString(name),
            r.serverID,
            r.key,
            r.hashing,
            r.timestamp,
            r.ct.base64C0,
            r.ct.base64C1,
            r.ct.base64C2,
            r.ct.base64C3
        );
    }

    function getFileIndex(address addr) public view returns (uint256) {
        return fileNames[addr].length;
    }
}