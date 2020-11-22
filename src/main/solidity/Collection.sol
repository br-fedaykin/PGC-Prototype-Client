// SPDX-License-Identifier: MIT
pragma solidity ^0.7.0 <= 0.7.5;

abstract contract Collection {

    address public owner;
    enum ContractType {AUTHORITY, FILES, KEYS, REQUESTS, USERS, UTILITY }

    /* FIX: rootContract should intanciate them to be their original owner, but it couldn't be done because it would exceed maximum size by doing so
     * https://github.com/ethereum/EIPs/blob/master/EIPS/eip-170.md
     */
    constructor (address rootContract) {
        owner = rootContract;
    }

    function setContractDependencies(ContractType contractType, address addr) virtual public;

    function changeOwnership(address newOwner) public onlyOwner {
        owner = newOwner;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Operation not allowed. User must be the contract owner");
        _;
    }
}
