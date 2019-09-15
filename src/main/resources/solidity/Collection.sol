pragma solidity ^0.5.1;

contract Collection {

    address owner;
    enum ContractType {AUTHORITY, FILES, KEYS, USERS, UTILITY }

    /* FIX: rootContract should intanciate them to be their original owner, but it couldn't be done because it would exceed maximum size by doing so
     * https://github.com/ethereum/EIPs/blob/master/EIPS/eip-170.md
     */
    constructor (address rootContract) public {
        owner = rootContract;
    }

    function setContractDependencies(Collection.ContractType contractType, address addr) public;

    function changeOwnership(address newOwner) public {
        require(msg.sender == owner, "Operation not allowed.");
        owner = newOwner;
    }
}