#!/bin/bash

web3j solidity generate -a=bin/src/main/resources/solidity/smartDCPABEFiles.abi -b=bin/src/main/resources/solidity/smartDCPABEFiles.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/main/resources/solidity/smartDCPABEAuthority.abi -b=bin/src/main/resources/solidity/smartDCPABEAuthority.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/main/resources/solidity/smartDCPABEKeys.abi -b=bin/src/main/resources/solidity/smartDCPABEKeys.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=bin/src/main/resources/solidity/smartDCPABEUsers.abi -b=bin/src/main/resources/solidity/smartDCPABEUsers.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain