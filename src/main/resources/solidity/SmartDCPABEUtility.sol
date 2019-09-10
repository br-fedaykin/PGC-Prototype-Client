pragma solidity ^0.5.1;
import "./Collection.sol";

contract SmartDCPABEUtility is Collection {

    function stringToBytes32(string memory source) public pure returns (bytes32 result) {
        bytes memory tempEmptyStringTest = bytes(source);
        assert(tempEmptyStringTest.length <= 32);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }
        assembly {
            result := mload(add(source, 0x20))
        }
    }

    function bytes32ToString(bytes32 source) public pure returns (string memory value) {
        // TODO: Optimize that code to avoid loops somehow.
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            byte char = byte(bytes32(uint(source) * 2 ** (8 * j)));
            if (char != 0) {
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for (uint j = 0; j < charCount; j++) {
            bytesStringTrimmed[j] = bytesString[j];
        }
        value = string(bytesStringTrimmed);
    }

    function trimBytes31(bytes31 source, uint8 size) public pure returns (bytes memory output) {
        output = new bytes(size);
        bytes32 source32 = bytes32(source);
        for (uint j = 0; j < size; j++) {
            byte char = byte(bytes32(uint(source32) * 2 ** (8 * j)));
            output[j] = char;
        }
        return output;
    }
}