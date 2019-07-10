pragma solidity ^0.5.1;
pragma experimental ABIEncoderV2;

contract SmartDCPABE {

    struct Patient {
        address addr;
        string nameHash;
        string emailHash;
        uint32 numRecordings;
        mapping (uint32 => Recording) files;
    }

    struct Recording {
        uint32 id;
        Ciphertext ct;
        FileInfo info;
        uint40 timestamp;
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
        bytes32 hash;
    }

    address[] patientAddress;
    uint256 numPatients;
    mapping (address => Patient) public patients;

    function addPatient(address addr, string memory name, string memory email) public {
        patientAddress.push(addr);
        numPatients++;
        patients[addr] = Patient(addr, name, email, 0);
    }

    function addRecording(address addr, Recording memory r) public {
        Patient storage p = patients[addr];
        p.files[p.numRecordings++] = r;
    }
}