pragma solidity ^0.5.1;
import "./SmartDCPABEAuthority.sol";
import "./SmartDCPABEUsers.sol";
import "./Collection.sol";

contract SmartDCPABERequests is Collection {

    enum KeyRequestStatus {
        PENDING,
        OK,
        REJECTED
    }

    struct KeyRequest {
        KeyRequestStatus status;
        uint64 timestamp;
        uint64 responseTimestamp;
        bytes32[] attrNames;
    }

    mapping (address => address[]) pendingRequesters;
    mapping (address => mapping (address => uint64[])) pendingRequests;
    mapping (address => mapping (address => KeyRequest[])) requests;
    SmartDCPABEUsers user;
    SmartDCPABEAuthority authority;

    constructor(address root) Collection(root) public {}

    function setContractDependencies(Collection.ContractType contractType, address addr) public onlyOwner {
        if (contractType == ContractType.AUTHORITY) {
            authority = SmartDCPABEAuthority(addr);
        } else if (contractType == ContractType.USERS) {
            user = SmartDCPABEUsers(addr);
        }
    }

    /**
     * attrNames are bytes32 instead of string because I don't tested if web3j works well
     * with dynamic array of dynamic types string/bytes
     */
    function addRequest(address certifier, address requester, uint64 timestamp, bytes32[] memory attrNames) public {
        assert(user.isUser(requester));
        assert(authority.isCertifier(certifier));
        // cria uma lista de espera para aquele endereço
        uint64 pendingIndex = uint64(requests[certifier][requester].length);
        requests[certifier][requester].push(KeyRequest(KeyRequestStatus.PENDING, timestamp, 0, attrNames));
        pendingRequests[certifier][requester].push(pendingIndex);
        pendingRequesters[certifier].push(requester);
    }

    function processRequest(address certifier, uint64 requesterIndex, uint64 pendingIndex, KeyRequestStatus newStatus) public {
        address requester = pendingRequesters[certifier][requesterIndex];
        require(pendingRequests[certifier][requester].length >= 1, "No pending requests for this certifier.");
        uint64 index = pendingRequests[certifier][requester][pendingIndex];
        requests[certifier][requester][index].status = newStatus;
        if (pendingRequests[certifier][requester].length == 1) {
            pendingRequests[certifier][requester].pop();
            address lastRequester = pendingRequesters[certifier][pendingRequesters[certifier].length - 1];
            pendingRequesters[certifier].length--;
            if (pendingIndex != pendingRequesters[certifier].length) {
                pendingRequesters[certifier][pendingIndex] = lastRequester;
            }
        } else {
            uint64 lastIndex = pendingRequests[certifier][requester][pendingRequests[certifier][requester].length - 1];
            pendingRequests[certifier][requester].length--;
            if (pendingIndex != pendingRequests[certifier][requester][pendingRequests[certifier][requester].length - 1]) {
                pendingRequests[certifier][requester][pendingIndex] = lastIndex;
            }
        }
    }

    function getRequestListSize(address certifier, address requester) public view returns (uint256) {
        return requests[certifier][requester].length;
    }

    function getPendingList(address certifier, address requester) public view returns (uint64[] memory) {
        return pendingRequests[certifier][requester];
    }

    function getPendingRequesterListSize(address certifier) public view returns (uint256) {
        return pendingRequesters[certifier].length;
    }

    function getPendingRequesterAddress(address certifier, uint64 requesterIndex) public view returns (address) {
        return pendingRequesters[certifier][requesterIndex];
    }

    function getRequestStatus(address certifier, address requester, uint64 index) public view returns (KeyRequestStatus status) {
        return requests[certifier][requester][index].status;
    }

    function getRequest
    (
        address certifier,
        address requester,
        uint64 index
    )
        public
        view
        returns
    (
        KeyRequestStatus status,
        uint64,
        uint64 timestamp,
        uint64 responseTimestamp,
        bytes32[] memory attrNames
    )
    {
        KeyRequest memory kr = requests[certifier][requester][index];
        return (
            kr.status,
            index,
            kr.timestamp,
            kr.responseTimestamp,
            kr.attrNames
            );
    }
}