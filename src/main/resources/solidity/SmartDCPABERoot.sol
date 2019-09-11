pragma solidity ^0.5.1;

import "./SmartDCPABEAuthority.sol";
import "./SmartDCPABEFiles.sol";
import "./SmartDCPABEKeys.sol";
import "./SmartDCPABEUsers.sol";
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEAuthority.sol";
import "./Collection.sol";

contract SmartDCPABERoot {

    Collection[5] contracts;
    address[5] public contractAddress;
    Collection.ContractType AUTHORITY = Collection.ContractType.AUTHORITY;
    Collection.ContractType FILES = Collection.ContractType.FILES;
    Collection.ContractType KEYS = Collection.ContractType.KEYS;
    Collection.ContractType USERS = Collection.ContractType.USERS;
    Collection.ContractType UTILITY = Collection.ContractType.UTILITY;

    function deployContracts() public {
        contracts[uint(AUTHORITY)] = new SmartDCPABEAuthority();
        contracts[uint(FILES)] = new SmartDCPABEFiles();
        contracts[uint(KEYS)] = new SmartDCPABEKeys();
        contracts[uint(USERS)] = new SmartDCPABEUsers();
        contracts[uint(UTILITY)] = new SmartDCPABEUtility();
        for (uint8 i = 0; i < 5; i++) {
            contractAddress[i] = address(contracts[i]);
            setContractDependencies(Collection.ContractType(i));
        }
    }

    function setContractAddress(Collection.ContractType contractType, address addr) public {
        uint8 index = uint8(contractType);
        require(index < 5, "targered contract type aren't implemented yet.");
        if (contractType == AUTHORITY) {
            contracts[index] = SmartDCPABEAuthority(addr);
            contractAddress[index] = addr;
        } else if (contractType == FILES) {
            contracts[index] = SmartDCPABEFiles(addr);
            contractAddress[index] = addr;
        } else if (contractType == KEYS) {
            contracts[index] = SmartDCPABEKeys(addr);
            contractAddress[index] = addr;
        }else if (contractType == USERS) {
            contracts[index] = SmartDCPABEUsers(addr);
            contractAddress[index] = addr;
        } else if (contractType == UTILITY) {
            contracts[index] = SmartDCPABEUtility(addr);
            contractAddress[index] = addr;
        }
        setContractDependencies(contractType);
    }

    function setContractDependencies(Collection.ContractType contractType) public {
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
            index = uint8(dependencies[i]);
            contracts[index].setContractDependencies(dependencies[i], contractAddress[index]);
        }
    }

    function getContractAddress(Collection.ContractType contractType) public view returns (address) {
        return contractAddress[uint(contractType)];
    }
}
