pragma solidity ^0.5.1;

contract SmartDCPABE {

    struct User {
        address addr;
        bytes32 name;
        bytes32 email;
        uint32 numRecordings;
        mapping (uint32 => Recording) files;
    }

    struct Certifier {
        address addr;
        bytes32 name;
        bytes32 email;
        uint64 numPublicKeys;
        mapping (uint64 => PublicKey) keys;
    }

    struct PublicKey {
        uint64 id;
        bytes32 name;
        Bytes122 eg1g1ai;
        Bytes122 g1yi;
    }

    struct Bytes122 {
        bytes32[3] chunks;
        bytes26 lastChunk;
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
    address[] public certifierAddresses;

    uint64 public numServers;
    uint256 public numUsers;
    uint256 public numCertifiers;

    FileServer[] public servers;
    mapping (address => User) users;
    mapping (address => Certifier) certifiers;

    function addCertifier(address addr, string memory name, string memory email) public {
        addCertifier(addr, stringToBytes32(name), stringToBytes32(email));
    }

    function addCertifier(address addr, bytes32 name, bytes32 email) public {
        certifierAddresses.push(addr);
        numCertifiers++;
        certifiers[addr] = Certifier(addr, name, email, 0);
    }

    function addUser(address addr, string memory name, string memory email) public {
        addUser(addr, stringToBytes32(name), stringToBytes32(email));
    }

    function addUser(address addr, bytes32 name, bytes32 email) public {
        userAddresses.push(addr);
        numUsers++;
        users[addr] = User(addr, name, email, 0);
    }

    function addServer(bytes32 domain, bytes32 path, uint16 port) public {
        servers[numServers] = FileServer(domain, path, port);
        numServers++;
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
    {
        User storage p = users[addr];
        p.files[p.numRecordings] = Recording(p.numRecordings, timestamp, Ciphertext(c0, c1, c2, c3),
            FileInfo(filename, serverID, key, hashing));
        p.numRecordings++;
    }

    function addPublicKey(address addr, string memory name, bytes memory eg1g1ai, bytes memory g1yi) public {
        bytes32 eg1g1aiChunk1;
        bytes32 eg1g1aiChunk2;
        bytes32 eg1g1aiChunk3;
        bytes26 eg1g1aiChunk4;
        bytes32 g1yiChunk1;
        bytes32 g1yiChunk2;
        bytes32 g1yiChunk3;
        bytes26 g1yiChunk4;

        assembly {
            eg1g1aiChunk1 := mload(add(eg1g1ai, 0x20))
            eg1g1aiChunk2 := mload(add(eg1g1ai, 0x40))
            eg1g1aiChunk3 := mload(add(eg1g1ai, 0x60))
            eg1g1aiChunk4 := mload(add(eg1g1ai, 0x80))
            g1yiChunk1 := mload(add(g1yi, 0x20))
            g1yiChunk2 := mload(add(g1yi, 0x40))
            g1yiChunk3 := mload(add(g1yi, 0x60))
            g1yiChunk4 := mload(add(g1yi, 0x80))
        }

        addPublicKey(addr, stringToBytes32(name), eg1g1aiChunk1, eg1g1aiChunk2, eg1g1aiChunk3, eg1g1aiChunk4,
                     g1yiChunk1, g1yiChunk2, g1yiChunk3, g1yiChunk4);
    }

    function addPublicKey
    (
        address addr,
        bytes32 name,
        bytes32 eg1g1aiChunk1,
        bytes32 eg1g1aiChunk2,
        bytes32 eg1g1aiChunk3,
        bytes26 eg1g1aiChunk4,
        bytes32 g1yiChunk1,
        bytes32 g1yiChunk2,
        bytes32 g1yiChunk3,
        bytes26 g1yiChunk4
    )
        public
    {
        Bytes122 memory eg1g1ai = Bytes122([eg1g1aiChunk1, eg1g1aiChunk2, eg1g1aiChunk3], eg1g1aiChunk4);
        Bytes122 memory g1yi = Bytes122([g1yiChunk1, g1yiChunk2, g1yiChunk3], g1yiChunk4);
        Certifier storage c = certifiers[addr];
        c.keys[c.numPublicKeys] = PublicKey(c.numPublicKeys, name, eg1g1ai, g1yi);
        c.numPublicKeys++;
    }

    function searchPublicKeyID(address addr, string memory name) public view returns (int64 id) {
        return searchPublicKeyID(addr, stringToBytes32(name));
    }

    function searchPublicKeyID(address addr, bytes32 name) public view returns (int64 id) {
        Certifier storage c = certifiers[addr];
        for (uint64 index = 0; index < c.numPublicKeys; index++) {
            if (c.keys[index].name == name) {
                return int64(index);
            }
        }
        return -1;
    }

    function getPublicKeyByName
    (
        address addr,
        bytes32 name
    )
        public
        view
        returns
    (
        bytes32 name_,
        bytes32 eg1g1aiChunk1,
        bytes32 eg1g1aiChunk2,
        bytes32 eg1g1aiChunk3,
        bytes26 eg1g1aiChunk4,
        bytes32 g1yiChunk1,
        bytes32 g1yiChunk2,
        bytes32 g1yiChunk3,
        bytes26 g1yiChunk4
    )
    {
        int64 index = searchPublicKeyID(addr, name);
        require(index >= 0, "Public Key not found");
        return getPublicKey(addr, uint64(index));
    }

    function getPublicKey
    (
        address addr,
        uint64 index
    )
        public
        view
        returns
    (
        bytes32 name,
        bytes32 eg1g1aiChunk1,
        bytes32 eg1g1aiChunk2,
        bytes32 eg1g1aiChunk3,
        bytes26 eg1g1aiChunk4,
        bytes32 g1yiChunk1,
        bytes32 g1yiChunk2,
        bytes32 g1yiChunk3,
        bytes26 g1yiChunk4
    )
    {
        PublicKey storage key = certifiers[addr].keys[index];
        return (
            key.name,
            key.eg1g1ai.chunks[0],
            key.eg1g1ai.chunks[1],
            key.eg1g1ai.chunks[2],
            key.eg1g1ai.lastChunk,
            key.g1yi.chunks[0],
            key.g1yi.chunks[1],
            key.g1yi.chunks[2],
            key.g1yi.lastChunk
            );
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
        bytes32 name,
        bytes32 email,
        uint32 numRecordings
    )
    {
        User storage u = users[addr];
        return (addr, u.name, u.email, u.numRecordings);
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

    function stringToBytes32(string memory source) public pure returns (bytes32 result) {
        bytes memory tempEmptyStringTest = bytes(source);
        assert(tempEmptyStringTest.length <= 32);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }
        assembly {
            result := mload(add(source, 32))
        }
    }

    function bytes32ToString(bytes32 source) public pure returns (string memory result) {
        if (source.length == 0) {
            return "";
        }
        result = string(abi.encodePacked(source));
    }
}