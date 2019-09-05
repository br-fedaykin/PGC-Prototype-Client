pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEAuthority.sol";

contract SmartDCPABEKeys {

    struct PublicKey {
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

    SmartDCPABEUtility util;
    SmartDCPABEAuthority authority;
    mapping (address => bytes32[]) publicKeyNames;
    mapping (address => mapping (bytes32 => PublicKey)) ABEKeys;

    constructor (address authorityContract) public {
        util = new SmartDCPABEUtility();
        authority = SmartDCPABEAuthority(authorityContract);
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

        assert(authority.isCertifier(addr));
        Bytes127 memory eg1g1ai = Bytes127(eg1g1aiChunks[0], eg1g1aiChunks[1], eg1g1aiChunks[2], eg1g1aiLastChunk, eg1g1aiLastChunkSize);
        Bytes127 memory g1yi = Bytes127(g1yiChunks[0], g1yiChunks[1], g1yiChunks[2], g1yiLastChunk, g1yiLastChunkSize);
        ABEKeys[addr][name] = PublicKey(eg1g1ai, g1yi);
        publicKeyNames[addr].push(name);
    }

    function getPublicKey
    (
        address addr,
        string memory name
    )
        public
        view
        returns
    (
        string memory name_,
        bytes memory eg1g1ai,
        bytes memory g1yi
    )
    {
        PublicKey storage pk = ABEKeys[addr][util.stringToBytes32(name)];
        return (
            name,
            abi.encodePacked(
                pk.eg1g1ai.chunk1,
                pk.eg1g1ai.chunk2,
                pk.eg1g1ai.chunk3,
                util.trimBytes31(pk.eg1g1ai.chunk4, pk.eg1g1ai.lastChunkSize)),
            abi.encodePacked(
                pk.g1yi.chunk1,
                pk.g1yi.chunk2,
                pk.g1yi.chunk3,
                util.trimBytes31(pk.g1yi.chunk4, pk.g1yi.lastChunkSize)));
    }
}