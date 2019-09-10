pragma solidity ^0.5.1;

import "./SmartDCPABEAuthority.sol";
import "./SmartDCPABEFiles.sol";
import "./SmartDCPABEKeys.sol";
import "./SmartDCPABEUsers.sol";
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEAuthority.sol";
import "./Collection.sol";

contract SmartDCPABERoot {

    enum contractType {AUTHORITY, FILES, KEYS, USERS, UTILITY }

    Collection[5] contracts;

    // método para instanciar contratos não criados
    function deployContracts() public {
        contracts[uint(contractType.UTILITY)] = new SmartDCPABEUtility();
        contracts[uint(contractType.USERS)] = new SmartDCPABEUsers();
        contracts[uint(contractType.FILES)] = new SmartDCPABEFiles(getFileContract());
        contracts[uint(contractType.AUTHORITY)] = new SmartDCPABEAuthority(getFileContract());
        contracts[uint(contractType.KEYS)] = new SmartDCPABEKeys(getFileContract());
    }

    function getFileContract() public view returns (address) {
        return address(contracts[uint(contractType(0))]);
    }

    // método para carregar contratos não criados

    // método para setar um contrato específico

    // método para devolver todos os contratos

    // método para atualizar as referências a um contrato

}
