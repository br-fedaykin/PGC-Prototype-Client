pragma solidity >=0.5.1 <0.6.0;
import "remix_tests.sol"; // this import is automatically injected by Remix.
import "./SmartDCPABEFiles.sol";

contract UserTest {

    SmartDCPABE dcpabe;

    address aliceAddress = 0x5dB97ed9752E2095eB8a4Ec5A61c9e11f5a824D7;
    address bobAddress = 0xDD34F8A4D869863263462EfC856AE0486220d934;
    address crmAddress = 0xc7228E9Add83d58Fd62DAFe5b098b10c2ed6A2F1;

    function beforeAll () public {
       dcpabe = new SmartDCPABE();
    }

    function checkCertifierAdding() public {
        // arrange
        address addr = crmAddress;
        string memory name = "CRM";
        bytes32 nameEncoded = 0x416c696365000000000000000000000000000000000000000000000000000000;
        string memory email = "manager@crm.com";

        // act
        dcpabe.addCertifier(addr, name, email);
        (, bytes32 nameEncodedRecovered, , ) = dcpabe.getUser(addr);

        // assert
        Assert.equal(nameEncoded, nameEncodedRecovered, "encoded names are not the same");
    }

    function checkUserAdding() public {

        // arrange
        address addr = aliceAddress;
        string memory name = "Alice";
        bytes32 nameEncoded = 0x416c696365000000000000000000000000000000000000000000000000000000;
        string memory email = "alice@email.com";

        // act
        dcpabe.addUser(addr, name, email);
        (, bytes32 nameEncodedRecovered, , ) = dcpabe.getUser(addr);

        // assert
        Assert.equal(nameEncoded, nameEncodedRecovered, "encoded names are not the same");
    }

    function checkRecordingAdding() public {

        // arrange
        address addr = 0x1bb88F049cf7fa2C454d369BeaC42b82eAf347eb;
        uint40 timestamp = uint40(1563743978213);
        bytes32 key = 0x1000000000000000000000000000000000000000000000000000000000000000;
        bytes32 randomHash = 0xace94ac6dca783d9b3f11b52d74aa559403fc62abd328561c8fea65687b574d2;

        // act
        dcpabe.addRecording(addr, timestamp, "bytes of c0", "bytes of c1", "bytes of c2", "bytes of c3",
                            "ipsum_lorem.pdf", uint64(0), key, randomHash);
        ( , , , , , , , , , bytes32 recoveredHash) = dcpabe.getRecording(addr, 0);

        // Assert
        Assert.equal(randomHash, recoveredHash, "Recording hashes are not the same.");
    }

    function join(string memory a, string memory b, string memory c, string memory d) internal pure returns (string memory) {
        return string(abi.encodePacked(a, b, c, d));
    }

    function checkPublicKeyAdding() public {

        // arrange
        string memory name = "attributo 1";
    }
}
