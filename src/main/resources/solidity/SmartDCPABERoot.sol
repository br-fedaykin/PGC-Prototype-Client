pragma solidity ^0.5.1;

import "./SmartDCPABEAuthority.sol";
import "./SmartDCPABEFiles.sol";
import "./SmartDCPABEKeys.sol";
import "./SmartDCPABEUsers.sol";
import "./SmartDCPABEUtility.sol";
import "./SmartDCPABEAuthority.sol";

contract SmartDCPABERoot {

    enum contractType {AUTHORITY, FILES, KEYS, USERS, UTILITY }

    mapping (uint => address) contractAddress;
    SmartDCPABEAuthority authority;
    SmartDCPABEFiles files;
    SmartDCPABEKeys keys;
    SmartDCPABEUsers users;
    SmartDCPABEUtility util;

    // método para instanciar contratos não criados
    function deployContracts() public {
        util = new SmartDCPABEUtility();
        contractAddress[uint(contractType.UTILITY)] = address(util);
        users = new SmartDCPABEUsers();
        contractAddress[uint(contractType.USERS)] = address(users);
        files = new SmartDCPABEFiles(address(users));
        contractAddress[uint(contractType.FILES)] = address(files);
        authority = new SmartDCPABEAuthority(address(users));
        contractAddress[uint(contractType.AUTHORITY)] = address(authority);
        keys = new SmartDCPABEKeys(address(authority));
        contractAddress[uint(contractType.KEYS)] = address(keys);
    }

    // método para carregar contratos não criados

    // método para setar um contrato específico

    // método para devolver todos os contratos

    // método para atualizar as referências a um contrato

}
