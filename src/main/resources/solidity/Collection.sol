pragma solidity ^0.5.1;

contract Collection {

    address owner;
    enum ContractType {AUTHORITY, FILES, KEYS, USERS, UTILITY }

    constructor () public {
        owner = msg.sender;
    }

    function setContractDependencies(Collection.ContractType contractType, address addr) public;
}