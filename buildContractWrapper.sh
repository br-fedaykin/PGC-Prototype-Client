#!/bin/bash

web3j solidity generate -a=src/main/resources/smartDCPABEFiles.abi -b=src/main/resources/smartDCPABEFiles.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain
web3j solidity generate -a=src/main/resources/smartDCPABEAuthority.abi -b=src/main/resources/smartDCPABEAuthority.bin -o=src/main/java -p=com.brunoarruda.hyperdcpabe.blockchain