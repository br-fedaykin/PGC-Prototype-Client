package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class SmartDCPABEUsers extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610a7c380380610a7c8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610a17806100656000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80636f77926b1161005b5780636f77926b1461014d5780638da5cb5b1461026a578063c8c76f0614610272578063d7554a68146102a157610088565b806319a50f491461008d5780632af4c31e146100b25780634209fff1146100da578063502c9bd514610114575b600080fd5b6100956103da565b6040805167ffffffffffffffff9092168252519081900360200190f35b6100d8600480360360208110156100c857600080fd5b50356001600160a01b03166103ea565b005b610100600480360360208110156100f057600080fd5b50356001600160a01b0316610455565b604080519115158252519081900360200190f35b6101316004803603602081101561012a57600080fd5b5035610475565b604080516001600160a01b039092168252519081900360200190f35b6101736004803603602081101561016357600080fd5b50356001600160a01b031661049c565b60405180846001600160a01b03166001600160a01b031681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156101cc5781810151838201526020016101b4565b50505050905090810190601f1680156101f95780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561022c578181015183820152602001610214565b50505050905090810190601f1680156102595780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b610131610689565b6100d86004803603604081101561028857600080fd5b50803560ff1690602001356001600160a01b0316610698565b6100d8600480360360608110156102b757600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102e157600080fd5b8201836020820111156102f357600080fd5b803590602001918460018302840111600160201b8311171561031457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561036657600080fd5b82018360208201111561037857600080fd5b803590602001918460018302840111600160201b8311171561039957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610723945050505050565b60035467ffffffffffffffff1681565b6000546001600160a01b031633146104335760405162461bcd60e51b81526004018080602001828103825260368152602001806109ad6036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b0316600090815260026020526040902060010154151590565b6001818154811061048257fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b03808216600090815260026020526040808220805460035460018301548451639201de5560e01b8152600481019190915293519495606095869593821693600160401b90930490911691639201de55916024808301928a929190829003018186803b15801561051157600080fd5b505afa158015610525573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561054e57600080fd5b810190808051600160201b81111561056557600080fd5b8201602081018481111561057857600080fd5b8151600160201b81118282018710171561059157600080fd5b5050929190505050600360089054906101000a90046001600160a01b03166001600160a01b0316639201de5584600201546040518263ffffffff1660e01b81526004018082815260200191505060006040518083038186803b1580156105f657600080fd5b505afa15801561060a573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561063357600080fd5b810190808051600160201b81111561064a57600080fd5b8201602081018481111561065d57600080fd5b8151600160201b81118282018710171561067657600080fd5b50959b949a509850929650505050505050565b6000546001600160a01b031681565b6000546001600160a01b031633146106e15760405162461bcd60e51b81526004018080602001828103825260368152602001806109ad6036913960400191505060405180910390fd5b60058260058111156106ef57fe5b141561071f576003805468010000000000000000600160e01b031916600160401b6001600160a01b038416021790555b5050565b60018054808201825560008281527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf690910180546001600160a01b0319166001600160a01b038781169182179092556003805467ffffffffffffffff19811667ffffffffffffffff91821690960116949094179384905560408051606081018252918252516319f6a32560e31b8152602060048201818152885160248401528851939682880196600160401b9091049095169463cfb51928948a94929384936044019290860191908190849084905b8381101561080a5781810151838201526020016107f2565b50505050905090810190601f1680156108375780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561085457600080fd5b505afa158015610868573d6000803e3d6000fd5b505050506040513d602081101561087e57600080fd5b505181526003546040516319f6a32560e31b815260206004820181815286516024840152865194820194600160401b9094046001600160a01b03169363cfb5192893889383926044909201919085019080838360005b838110156108ec5781810151838201526020016108d4565b50505050905090810190601f1680156109195780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561093657600080fd5b505afa15801561094a573d6000803e3d6000fd5b505050506040513d602081101561096057600080fd5b505190526001600160a01b03938416600090815260026020818152604092839020845181546001600160a01b03191698169790971787558301516001870155910151930192909255505056fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a265627a7a72305820f0cdd7f66beea806e549d9e20ae1215b6bc37fe7e2daf5a7777d5212dfa8481b64736f6c634300050a0032";

    public static final String FUNC_NUMUSERS = "numUsers";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_ISUSER = "isUser";

    public static final String FUNC_USERADDRESSES = "userAddresses";

    public static final String FUNC_GETUSER = "getUser";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_ADDUSER = "addUser";

    @Deprecated
    protected SmartDCPABEUsers(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABEUsers(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABEUsers(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABEUsers(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> numUsers() {
        final Function function = new Function(FUNC_NUMUSERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isUser(String addr) {
        final Function function = new Function(FUNC_ISUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> userAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_USERADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, String, String>> getUser(String addr) {
        final Function function = new Function(FUNC_GETUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<String, String, String>>(
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
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

    public RemoteCall<TransactionReceipt> addUser(String addr, String name, String email) {
        final Function function = new Function(
                FUNC_ADDUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(email)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SmartDCPABEUsers load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEUsers(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABEUsers load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEUsers(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABEUsers load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEUsers(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABEUsers load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEUsers(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
