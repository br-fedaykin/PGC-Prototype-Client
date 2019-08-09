pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";

contract SmartDCPABEFiles {

    struct User {
        address addr;
        bytes32 name;
        bytes32 email;
        uint32 numRecordings;
        mapping (uint32 => Recording) files;
    }

    struct Recording {
        uint64 id;
        uint128 timestamp;
        Ciphertext ct;
        FileInfo info;
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

    struct FileInfo {
        string filename;
        uint64 serverID;
        bytes32 key;
        bytes32 hashing;
    }

    address[] public userAddresses;

    uint64 public numServers;
    uint256 public numUsers;

    FileServer[] servers;
    mapping (address => User) users;
    SmartDCPABEUtility util;

    constructor () public {
        util = new SmartDCPABEUtility();
    }

    // TODO: create cheaper functions using bytes32 instead of string in input

    function addUser(address addr, string memory name, string memory email) public {
        userAddresses.push(addr);
        numUsers++;
        users[addr] = User(addr, util.stringToBytes32(name), util.stringToBytes32(email), 0);
    }

    // TODO: dismember server logic from this contract

    function addServer(string memory domain, string memory path, uint16 port) public returns (int64 serverIndex) {
        servers[numServers] = FileServer(util.stringToBytes32(domain), util.stringToBytes32(path), port);
        numServers++;
        return int64(numServers -1);
    }

    function addRecording(
        address addr,
        // recordings parameters
        uint40 timestamp,
        // ciphertext parameters
        string memory c0,
        string memory c1,
        string memory c2,
        string memory c3,
        // FileInfo parameters
        string memory filename,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing
    )
        public
        returns
    (
        uint32
    )
    {
        User storage p = users[addr];
        p.files[p.numRecordings] = Recording(p.numRecordings, timestamp, Ciphertext(c0, c1, c2, c3),
            FileInfo(filename, serverID, key, hashing));
        p.numRecordings++;
        return p.numRecordings - 1;
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

    function getUser
    (
        address addr
    )
        public
        view
        returns
    (
        address addr_,
        string memory name,
        string memory email,
        uint32 numRecordings
    )
    {
        User storage u = users[addr];
        return (addr, util.bytes32ToString(u.name), util.bytes32ToString(u.email), u.numRecordings);
    }

    function getRecording
    (
        address addr,
        uint32 index
    )
        public
        view
        returns
    (
        uint64 id,
        uint128 timestamp,
        string memory c0,
        string memory c1,
        string memory c2,
        string memory c3,
        string memory filename,
        uint64 serverID,
        bytes32 key,
        bytes32 hashing
    )
    {
        Recording storage r = users[addr].files[index];
        return (
            r.id,
            r.timestamp,
            r.ct.base64C0,
            r.ct.base64C1,
            r.ct.base64C2,
            r.ct.base64C3,
            r.info.filename,
            r.info.serverID,
            r.info.key,
            r.info.hashing
        );
    }
}