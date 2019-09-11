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
    }

    function setContractDependencies(Collection.ContractType contractType) public {
        uint8 index = uint8(contractType);
        require(index < 5, "targered contract type aren't implemented yet.");
        // if clauses to select contracts that will be sent to target contract
        // loop over selection to send a address over each iteration
    }

    function getContractAddress(Collection.ContractType contractType) public view returns (address) {
        return contractAddress[uint(contractType)];
    }
}
