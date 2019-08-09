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
        Bytes127 eg1g1ai;
        Bytes127 g1yi;
    }

    struct Bytes127 {
        bytes32 chunk1;
        bytes32 chunk2;
        bytes32 chunk3;
        bytes31 chunk4;
        uint8 lastChunkSize;
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
        bytes32 nameBytes32 = util.stringToBytes32(name);
        bytes32[3] memory eg1g1aiChunks;
        bytes31 eg1g1aiLastChunk;
        uint8 eg1g1aiLastChunkSize = uint8(eg1g1ai.length % 32);
        bytes32[3] memory g1yiChunks;
        bytes31 g1yiLastChunk;
        uint8 g1yiLastChunkSize = uint8(g1yi.length % 32);

        assembly {
            // writing eg1g1ai byte dynamic array on the bytes32[3] structure
            mstore(eg1g1aiChunks, mload(add(eg1g1ai, 0x20)))
            mstore(add(eg1g1aiChunks, 0x20), mload(add(eg1g1ai, 0x40)))
            mstore(add(eg1g1aiChunks, 0x40), mload(add(eg1g1ai, 0x60)))
            eg1g1aiLastChunk := mload(add(eg1g1ai, 0x80))

            // writing g1yi byte dynamic array on the bytes32[3] structure
            mstore(g1yiChunks, mload(add(g1yi, 0x20)))
            mstore(add(g1yiChunks, 0x20), mload(add(g1yi, 0x40)))
            mstore(add(g1yiChunks, 0x40), mload(add(g1yi, 0x60)))
            g1yiLastChunk := mload(add(g1yi, 0x80))
        }

        addPublicKey(addr, nameBytes32, eg1g1aiChunks, eg1g1aiLastChunk, eg1g1aiLastChunkSize,
                     g1yiChunks, g1yiLastChunk, g1yiLastChunkSize);
    }

    function addPublicKey
    (
        address addr,
        bytes32 name,
        bytes32[3] memory eg1g1aiChunks,
        bytes31 eg1g1aiLastChunk,
        uint8 eg1g1aiLastChunkSize,
        bytes32[3] memory g1yiChunks,
        bytes31 g1yiLastChunk,
        uint8 g1yiLastChunkSize
    )
        public
    {
        Bytes127 memory eg1g1ai = Bytes127(eg1g1aiChunks[0], eg1g1aiChunks[1], eg1g1aiChunks[2], eg1g1aiLastChunk, eg1g1aiLastChunkSize);
        Bytes127 memory g1yi = Bytes127(g1yiChunks[0], g1yiChunks[1], g1yiChunks[2], g1yiLastChunk, g1yiLastChunkSize);
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
        string memory name
    )
        public
        view
        returns
    (
        string memory,
        bytes memory,
        bytes memory
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
        string memory name,
        bytes memory eg1g1ai,
        bytes memory g1yi
    )
    {
        PublicKey memory key = certifiers[addr].keys[index];
        return (
            util.bytes32ToString(key.name),
            abi.encodePacked(
                key.eg1g1ai.chunk1,
                key.eg1g1ai.chunk2,
                key.eg1g1ai.chunk3,
                util.trimBytes31(key.eg1g1ai.chunk4, key.eg1g1ai.lastChunkSize)),
            abi.encodePacked(
                key.g1yi.chunk1,
                key.g1yi.chunk2,
                key.g1yi.chunk3,
                util.trimBytes31(key.g1yi.chunk4, key.g1yi.lastChunkSize)));
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