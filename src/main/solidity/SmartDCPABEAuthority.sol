pragma solidity ^0.5.1;

import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEUsers.sol";
import "./Collection.sol";

contract SmartDCPABEAuthority is Collection {

    struct Certifier {
        address addr;
        bytes32 name;
        bytes32 email;
        uint64 numPublicKeys;
    }

    address[] public certifierAddresses;
    mapping (address => Certifier) certifiers;
    SmartDCPABEUtility util;
    SmartDCPABEUsers user;
    address contractKeys;

    constructor(address root) Collection(root) public {}

    function setContractDependencies(ContractType contractType, address addr) public onlyOwner {
        if (contractType == ContractType.UTILITY) {
            util = SmartDCPABEUtility(addr);
        } else if (contractType == ContractType.USERS) {
            user = SmartDCPABEUsers(addr);
        } else if (contractType == ContractType.KEYS) {
            contractKeys = addr;
        }
    }

    function incrementPublicKeyCount(address addr) public {
        require(msg.sender == contractKeys, "Operation not allowed.");
        certifiers[addr].numPublicKeys++;
    }

    function isCertifier(address addr) public view returns (bool) {
        return certifiers[addr].name != bytes32(0);
    }

    function addCertifier(address addr, string memory name, string memory email) public {
        certifierAddresses.push(addr);
        certifiers[addr] = Certifier(addr, util.stringToBytes32(name), util.stringToBytes32(email), 0);
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
        return (c.addr, util.bytes32ToString(c.name), util.bytes32ToString(c.email), c.numPublicKeys);
    }
}