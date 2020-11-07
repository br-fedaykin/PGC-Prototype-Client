package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

// cSpell:disable
/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */

@SuppressWarnings("rawtypes")
public class SmartDCPABEUtility extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516105613803806105618339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556104fc806100656000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80632af4c31e1461006757806370751f5b1461008f5780638da5cb5b1461012e5780639201de5514610152578063c8c76f061461016f578063cfb519281461019e575b600080fd5b61008d6004803603602081101561007d57600080fd5b50356001600160a01b0316610256565b005b6100b9600480360360408110156100a557600080fd5b5060ff19813516906020013560ff166102c1565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100f35781810151838201526020016100db565b50505050905090810190601f1680156101205780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610136610349565b604080516001600160a01b039092168252519081900360200190f35b6100b96004803603602081101561016857600080fd5b5035610358565b61008d6004803603604081101561018557600080fd5b50803560ff1690602001356001600160a01b031661045d565b610244600480360360208110156101b457600080fd5b8101906020810181356401000000008111156101cf57600080fd5b8201836020820111156101e157600080fd5b8035906020019184600183028401116401000000008311171561020357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610461945050505050565b60408051918252519081900360200190f35b6000546001600160a01b0316331461029f5760405162461bcd60e51b81526004018080602001828103825260368152602001806104926036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60608160ff166040519080825280601f01601f1916602001820160405280156102f1576020820181803883390190505b50905060ff19831660005b8360ff168110156103415782516008820260020a830290819085908490811061032157fe5b60200101906001600160f81b031916908160001a905350506001016102fc565b505092915050565b6000546001600160a01b031681565b6040805160208082528183019092526060918291906020820181803883390190505090506000805b60208110156103d6576008810260020a85026001600160f81b03198116156103cd57808484815181106103af57fe5b60200101906001600160f81b031916908160001a9053506001909201915b50600101610380565b506060816040519080825280601f01601f191660200182016040528015610404576020820181803883390190505b50905060005b828110156104545783818151811061041e57fe5b602001015160f81c60f81b82828151811061043557fe5b60200101906001600160f81b031916908160001a90535060010161040a565b50949350505050565b5050565b6000606082905060208151111561047457fe5b805161048457506000905061048c565b505060208101515b91905056fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a265627a7a72305820e019c0d27b89e914d5a6e3b44f13a794c14b157272dc43785cf3aafcf877030764736f6c634300050a0032";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_TRIMBYTES31 = "trimBytes31";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_BYTES32TOSTRING = "bytes32ToString";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_STRINGTOBYTES32 = "stringToBytes32";

    @Deprecated
    protected SmartDCPABEUtility(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABEUtility(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABEUtility(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABEUtility(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> trimBytes31(byte[] source, BigInteger size) {
        final Function function = new Function(FUNC_TRIMBYTES31,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes31(source),
                new org.web3j.abi.datatypes.generated.Uint8(size)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> bytes32ToString(byte[] source) {
        final Function function = new Function(FUNC_BYTES32TOSTRING,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(source)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setContractDependencies(BigInteger contractType, String addr) {
        final Function function = new Function(
                FUNC_SETCONTRACTDEPENDENCIES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(contractType),
                new org.web3j.abi.datatypes.Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> stringToBytes32(String source) {
        final Function function = new Function(FUNC_STRINGTOBYTES32,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(source)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    @Deprecated
    public static SmartDCPABEUtility load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEUtility(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABEUtility load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEUtility(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABEUtility load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEUtility(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABEUtility load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEUtility(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABEUtility> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUtility.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEUtility> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUtility.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUtility> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUtility.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUtility> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUtility.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
