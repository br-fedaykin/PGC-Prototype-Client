pragma solidity ^0.5.1;
pragma experimental ABIEncoderV2;

contract SmartDCPABE {

    struct Patient {
        address addr;
        string nameHash;
        string emailHash;
        uint32 numRecordings;
    }

    struct Recording {
        uint32 id;
        uint40 timestamp;
        Ciphertext ct;
        FileInfo info;
    }

    struct Ciphertext {
        bytes c0;
        string c1;
        string c2;
        string c3;
    }

    struct FileInfo {
        string filename;
        string url;
        string key;
        bytes32 hashing;
    }

    address[] patientAddress;
    uint256 numPatients;
    mapping (address => Recording[]) public files;
    mapping (address => Patient) public patients;

    function addPatient(address addr, string memory name, string memory email) public {
        patientAddress.push(addr);
        numPatients++;
        patients[addr] = Patient(addr, name, email, 0);
    }

    function addRecording(
        address addr,
        // recordings parameters
        uint32 id,
        uint40 timestamp,
        // ciphertext parameters
        bytes memory c0,
        string memory c1,
        string memory c2,
        string memory c3,
        // FileInfo parameters
        string memory filename,
        string memory url,
        string memory key,
        bytes32 hashing
    )
        public
    {
        Patient storage p = patients[addr];
        files[addr][p.numRecordings] = Recording(
            id,
            timestamp,
            Ciphertext(c0, c1, c2, c3),
            FileInfo(filename, url, key, hashing)
        );
        p.numRecordings++;
    }

    function getRecording
    (
        address addr, uint32 i
    )
        public
        view
        returns
    (
        uint32  id,
        uint40  timestamp,
        bytes memory c0,
        string memory c1,
        string memory c2,
        string memory c3,
        string memory filename,
        string memory url,
        string memory key,
        bytes32  hashing
    )
    {
        id = files[addr][i].id;
        timestamp = files[addr][i].timestamp;
        c0 = files[addr][i].ct.c0;
        c1 = files[addr][i].ct.c1;
        c2 = files[addr][i].ct.c2;
        c3 = files[addr][i].ct.c3;
        filename = files[addr][i].info.filename;
        url = files[addr][i].info.url;
        key = files[addr][i].info.key;
        hashing = files[addr][i].info.hashing;
        return (id, timestamp, c0, c1, c2, c3, filename, url, key, hashing);
    }
}