pragma solidity ^0.5.1;

import "./Collection.sol";

contract SmartDCPABERoot {

    address[5] public contractAddress;
    Collection.ContractType AUTHORITY = Collection.ContractType.AUTHORITY;
    Collection.ContractType FILES = Collection.ContractType.FILES;
    Collection.ContractType KEYS = Collection.ContractType.KEYS;
    Collection.ContractType USERS = Collection.ContractType.USERS;
    Collection.ContractType UTILITY = Collection.ContractType.UTILITY;
    address owner;

    constructor () public {
        owner = msg.sender;
    }

    function changeOwnership(address newRoot) public {
        require(msg.sender == owner, "Operation not allowed.");
        for (uint8 i = 0; i < 5; i++) {
            bytes memory payload = abi.encodeWithSignature("changeOwnership(address)", newRoot);
            (bool success, ) = contractAddress[i].call(payload);

            // NOTE: this could lead to consistency problems if a failed require() breaks the loop and ownership is not restored.
            require(success, "Contract method invocation failed.");
        }
    }

    function setAllContracts(Collection.ContractType[5] memory contractType, address[5] memory addr) public {
        for (uint8 i = 0; i < 5; i++) {
            if (uint(addr[i]) != 0) {
                setContractAddress(contractType[i], addr[i]);
            }
        }
        for (uint8 i = 0; i < 5; i++) {
            if (uint(addr[i]) != 0) {
                setContractDependencies(contractType[i]);
            }
        }
    }

    function setContract(Collection.ContractType contractType, address addr) public {
        setContractAddress(contractType, addr);
        setContractDependencies(contractType);
    }

    function setContractAddress(Collection.ContractType contractType, address addr) private {
        uint8 index = uint8(contractType);
        require(index < 5, "targered contract type aren't implemented yet.");
        if (contractType == AUTHORITY) {
            contractAddress[index] = addr;
        } else if (contractType == FILES) {
            contractAddress[index] = addr;
        } else if (contractType == KEYS) {
            contractAddress[index] = addr;
        }else if (contractType == USERS) {
            contractAddress[index] = addr;
        } else if (contractType == UTILITY) {
            contractAddress[index] = addr;
        }
    }

    function setContractDependencies(Collection.ContractType contractType) private {
        uint8 index = uint8(contractType);
        require(index < 5, "targered contract type aren't implemented yet.");
        Collection.ContractType[5] memory dependencies;
        uint8 numDependencies = 0;
        if (contractType == AUTHORITY) {
            dependencies[0] = KEYS;
            numDependencies = 1;
        } else if (contractType == KEYS) {
            dependencies[0] = AUTHORITY;
            numDependencies = 1;
        }else if (contractType == USERS) {
            dependencies[0] = AUTHORITY;
            dependencies[1] = FILES;
            numDependencies = 2;
        } else if (contractType == UTILITY) {
            dependencies[0] = AUTHORITY;
            dependencies[1] = FILES;
            dependencies[2] = KEYS;
            dependencies[3] = USERS;
            dependencies[4] = UTILITY;
            numDependencies = 5;
        }

        for (uint8 i = 0; i < numDependencies; i++) {
            uint8 indexDependentContract = uint8(dependencies[i]);
            bytes memory payload = abi.encodeWithSignature("setContractDependencies(uint8,address)", index, contractAddress[index]);
            (bool success, ) = contractAddress[indexDependentContract].call(payload);
            require(success, "Contract method invocation failed.");
        }
    }

    function getAllContractAddresses() public view returns (address[5] memory) {
        return contractAddress;
    }
}