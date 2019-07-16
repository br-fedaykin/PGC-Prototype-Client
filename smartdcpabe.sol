pragma solidity ^0.5.1;
pragma experimental ABIEncoderV2;

contract SmartDCPABE {

    struct Patient {
        address addr;
        string nameHash;
        string emailHash;
        uint32 numRecordings;
        Recording[] files;
    }

    struct Recording {
        uint32 id;
        uint40 timestamp;
        Ciphertext ct;
        FileInfo info;
    }

    struct Ciphertext {
        bytes c0;
        bytes[] c1;
        bytes[] c2;
        bytes[] c3;
    }

    struct FileInfo {
        string filename;
        string url;
        string key;
        bytes32 hashing;
    }

    address[] patientAddress;
    uint256 numPatients;
    mapping (address => Patient) public patients;

    function addPatient(address addr, string memory name, string memory email) public {
        patientAddress.push(addr);
        numPatients++;
        patients[addr] = Patient(addr, name, email, 0, new Recording[](0));
    }

    function addRecording(
        address addr,
        // recordings parameters
        uint32 id,
        uint40 timestamp,
        // ciphertext parameters
        bytes memory c0,
        bytes[] memory c1,
        bytes[] memory c2,
        bytes[] memory c3,
        // FileInfo parameters
        string memory filename,
        string memory url,
        string memory key,
        bytes32 hashing
    )
        public
    {
        Patient storage p = patients[addr];
        p.numRecordings++;
        Ciphertext memory ct = Ciphertext(c0, c1, c2, c3);
        FileInfo memory fi = FileInfo(filename, url, key, hashing);
        p.files.push(Recording(id, timestamp, ct, fi));
    }

    function getRecording(address addr, uint32 index) public view returns (Recording memory) {
        return patients[addr].files[index];
    }

    function getAllRecordings(address addr) public view returns (Recording[] memory)  {
        return patients[addr].files;
    }
}