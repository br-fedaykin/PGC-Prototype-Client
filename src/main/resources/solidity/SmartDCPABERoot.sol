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

    function deployContracts() public {
        contracts[uint(ContractType.AUTHORITY)] = new SmartDCPABEAuthority();
        contracts[uint(ContractType.FILES)] = new SmartDCPABEFiles();
        contracts[uint(ContractType.KEYS)] = new SmartDCPABEKeys();
        contracts[uint(ContractType.USERS)] = new SmartDCPABEUsers();
        contracts[uint(ContractType.UTILITY)] = new SmartDCPABEUtility();
        for (uint8 i = 0; i < 5; i++) {
            contractAddress[i] = address(contracts[i]);
        }
    }

    function setContractAddress(ContractType contractType, address addr) public returns (address) {
        uint8 index = uint8(contractType);
        require(index < 5, "targered contract type aren't implemented yet.");
        if (contractType == ContractType.AUTHORITY) {
            contracts[index] = SmartDCPABEAuthority(addr);
            contractAddress[index] = addr;
        } else if (contractType == ContractType.FILES) {
            contracts[index] = SmartDCPABEFiles(addr);
            contractAddress[index] = addr;
        } else if (contractType == ContractType.KEYS) {
            contracts[index] = SmartDCPABEKeys(addr);
            contractAddress[index] = addr;
        }else if (contractType == ContractType.USERS) {
            contracts[index] = SmartDCPABEUsers(addr);
            contractAddress[index] = addr;
        } else if (contractType == ContractType.UTILITY) {
            contracts[index] = SmartDCPABEUtility(addr);
            contractAddress[index] = addr;
        }
    }

    function getContractAddress(ContractType contractType) public view returns (address) {
        return contractAddress[uint(contractType)];
    }
}
