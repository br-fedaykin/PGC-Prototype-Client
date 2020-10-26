#!/bin/bash

echo ""
if [[ "$1" = "--compile" ]]; then
    echo -n "Compiling solidity contracts into EVM Binary code and ABI ... "
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABEAuthority.sol > logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABEFiles.sol >> logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABEKeys.sol >> logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABERequests.sol >> logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABERoot.sol >> logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABEUsers.sol >> logs/solc_build.log
    solc -o bin/src/solidity --bin --abi --asm --optimize --overwrite --gas --evm-version petersburg src/main/solidity/smartDCPABEUtility.sol >> logs/solc_build.log
    echo "Done, files are in bin/src/solidity"
fi

echo ""
echo -n "Generating Java wrapper classes for each contract ... "
web3j solidity generate -a=bin/src/solidity/smartDCPABEAuthority.abi -b=bin/src/solidity/smartDCPABEAuthority.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABEFiles.abi -b=bin/src/solidity/smartDCPABEFiles.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABEKeys.abi -b=bin/src/solidity/smartDCPABEKeys.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABERequests.abi -b=bin/src/solidity/smartDCPABERequests.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABERoot.abi -b=bin/src/solidity/smartDCPABERoot.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABEUsers.abi -b=bin/src/solidity/smartDCPABEUsers.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
web3j solidity generate -a=bin/src/solidity/smartDCPABEUtility.abi -b=bin/src/solidity/smartDCPABEUtility.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain > /dev/null
echo "Done, classes are inside blockchain package."
