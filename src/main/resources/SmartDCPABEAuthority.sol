pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";

contract SmartDCPABEAuthority {
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

    address[] public certifierAddresses;
    uint256 public numCertifiers;
    mapping (address => Certifier) certifiers;

    SmartDCPABEUtility util;

    constructor () public {
        util = new SmartDCPABEUtility();
    }

    function addCertifier(address addr, string memory name, string memory email) public {
        addCertifier(addr, util.stringToBytes32(name), util.stringToBytes32(email));
    }

    function addCertifier(address addr, bytes32 name, bytes32 email) public {
        certifierAddresses.push(addr);
        numCertifiers++;
        certifiers[addr] = Certifier(addr, name, email, 0);
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

        addPublicKey(addr, util.stringToBytes32(name),
                     eg1g1aiChunk1, eg1g1aiChunk2, eg1g1aiChunk3, eg1g1aiChunk4,
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
        return searchPublicKeyID(addr, util.stringToBytes32(name));
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
        bytes memory eg1g1ai,
        bytes memory g1yi
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
        bytes memory eg1g1ai,
        bytes memory g1yi
    )
    {
        PublicKey storage key = certifiers[addr].keys[index];
        return (
            key.name,
            abi.encodePacked(
                key.eg1g1ai.chunks[0],
                key.eg1g1ai.chunks[1],
                key.eg1g1ai.chunks[2],
                key.eg1g1ai.lastChunk),
            abi.encodePacked(
                key.g1yi.chunks[0],
                key.g1yi.chunks[1],
                key.g1yi.chunks[2],
                key.g1yi.lastChunk));
    }

    function getCertifier
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
        uint64 numPublicKeys
    )
    {
        Certifier storage c = certifiers[addr];
        return (addr, util.bytes32ToString(c.name), util.bytes32ToString(c.email), c.numPublicKeys);
    }

}