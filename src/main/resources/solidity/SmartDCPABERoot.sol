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

    function getContractAddress(ContractType contractType) public view returns (address) {
        return contractAddress[uint(contractType)];
    }
}
