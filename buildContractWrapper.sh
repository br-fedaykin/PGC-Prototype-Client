#!/bin/bash

if [[ "$1" = "--compile" ]]; then
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABEAuthority.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABEFiles.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABEKeys.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABERequests.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABERoot.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABEUsers.sol
    solc -o bin/src/solidity --bin --abi --optimize --overwrite --gas --evm-version petersburg src/main/resources/solidity/smartDCPABEUtility.sol
fi

web3j solidity generate -a=bin/src/solidity/smartDCPABEAuthority.abi -b=bin/src/solidity/smartDCPABEAuthority.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABEFiles.abi -b=bin/src/solidity/smartDCPABEFiles.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABEKeys.abi -b=bin/src/solidity/smartDCPABEKeys.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABERequests.abi -b=bin/src/solidity/smartDCPABERequests.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABERoot.abi -b=bin/src/solidity/smartDCPABERoot.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABEUsers.abi -b=bin/src/solidity/smartDCPABEUsers.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/solidity/smartDCPABEUtility.abi -b=bin/src/solidity/smartDCPABEUtility.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
