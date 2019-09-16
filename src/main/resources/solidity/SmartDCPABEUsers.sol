pragma solidity ^0.5.1;
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEFiles.sol";
import "./Collection.sol";

contract SmartDCPABEUsers is Collection {

    struct User {
        address addr;
        bytes32 name;
        bytes32 email;
    }

    address[] public userAddresses;
    mapping (address => User) users;
    uint64 public numUsers;

    SmartDCPABEUtility util;

    constructor(address root) Collection(root) public {}


    function setContractDependencies(ContractType contractType, address addr) public {
        require(msg.sender == owner, "Operation not allowed. Must be the done by the owner of the contract.");
        if (contractType == ContractType.UTILITY) {
            util = SmartDCPABEUtility(addr);
        }
    }

    // TODO: create cheaper functions using bytes32 instead of string in input
    function addUser(address addr, string memory name, string memory email) public {
        userAddresses.push(addr);
        numUsers++;
        users[addr] = User(addr, util.stringToBytes32(name), util.stringToBytes32(email));
    }

    function isUser(address addr) public view returns (bool) {
        return users[addr].name != bytes32(0);
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
        string memory email
    )
    {
        User storage u = users[addr];
        return (u.addr, util.bytes32ToString(u.name), util.bytes32ToString(u.email));
    }
}