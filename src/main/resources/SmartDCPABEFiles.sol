pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEUsers.sol";

contract SmartDCPABEFiles {

    struct Recording {
        string filename;
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
    // essa estrutura presume que quem pesquisa um arquivo sabe o seu nome
    // caso: é necessário baixar todos os arquivos de um usuário.
    // não se sabe os nomes, então como sabê-los?
    mapping (address => bytes32[]) fileNames;
    mapping (address => mapping(bytes32 => Recording)) files;

    FileServer[] servers;
    SmartDCPABEUtility util;
    SmartDCPABEUsers users;

    constructor (address userContract) public {
        util = new SmartDCPABEUtility();
        users = SmartDCPABEUsers(userContract);
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
        string memory filename,
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
        returns
    (
        uint32 recordingID
    )
    {
        assert(users.isUser(addr));
        files[addr][filename] = Recording(filename, serverID, key, hashing, timestamp, Ciphertext(c0, c1, c2, c3));
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
        string memory filename
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
        Recording storage r = files[addr];
        return (
            r.filename,
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
}