pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEUsers.sol";

contract SmartDCPABEAuthority {

    struct Certifier {
        address addr;
        bytes32 name;
        bytes32 email;
        uint64 numPublicKeys;
    }

    enum KeyRequestStatus {
        PENDING,
        OK,
        REJECTED
    }

    struct KeyRequest {
        bytes32 ID;
        uint64 timestamp;
        uint64 responseTimestamp;
        KeyRequestStatus status;
        bytes32[] attrNames;
    }

    address[] public certifierAddresses;
    mapping (address => Certifier) certifiers;
    mapping (address => address[]) attributeRequesters;
    mapping (address => mapping (address => KeyRequest[])) requests;
    SmartDCPABEUtility util;
    SmartDCPABEUsers user;

    constructor (address userContract) public {
        util = new SmartDCPABEUtility();
        user = SmartDCPABEUsers(userContract);
    }

    function isCertifier(address addr) public view returns (bool) {
        return certifiers[addr].name != bytes32(0);
    }

    function addCertifier(address addr, string memory name, string memory email) public {
        certifierAddresses.push(addr);
        certifiers[addr] = Certifier(addr, util.stringToBytes32(name), util.stringToBytes32(email), 0);
    }

    function addRequest(address authority, address requester, bytes32[] memory attrNames) public {
        assert(user.isUser(requester));
        assert(isCertifier(authority));

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