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

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class SmartDCPABEUsers extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610b20380380610b208339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610abb806100656000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80636f77926b1161005b5780636f77926b1461014d5780638da5cb5b1461026a578063c8c76f0614610272578063d7554a68146102a157610088565b806319a50f491461008d5780632af4c31e146100b25780634209fff1146100da578063502c9bd514610114575b600080fd5b6100956103da565b6040805167ffffffffffffffff9092168252519081900360200190f35b6100d8600480360360208110156100c857600080fd5b50356001600160a01b03166103ea565b005b610100600480360360208110156100f057600080fd5b50356001600160a01b0316610464565b604080519115158252519081900360200190f35b6101316004803603602081101561012a57600080fd5b5035610484565b604080516001600160a01b039092168252519081900360200190f35b6101736004803603602081101561016357600080fd5b50356001600160a01b03166104ab565b60405180846001600160a01b03166001600160a01b031681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156101cc5781810151838201526020016101b4565b50505050905090810190601f1680156101f95780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561022c578181015183820152602001610214565b50505050905090810190601f1680156102595780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b610131610754565b6100d86004803603604081101561028857600080fd5b50803560ff1690602001356001600160a01b0316610763565b6100d8600480360360608110156102b757600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102e157600080fd5b8201836020820111156102f357600080fd5b803590602001918460018302840111600160201b8311171561031457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561036657600080fd5b82018360208201111561037857600080fd5b803590602001918460018302840111600160201b8311171561039957600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506107fd945050505050565b60035467ffffffffffffffff1681565b6000546001600160a01b03163314610442576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b0316600090815260026020526040902060010154151590565b6001818154811061049157fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b03808216600090815260026020526040808220805460035460018301548451639201de5560e01b8152600481019190915293519495606095869593821693600160401b90930490911691639201de55916024808301928a929190829003018186803b15801561052057600080fd5b505afa158015610534573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561055d57600080fd5b8101908080516040519392919084600160201b82111561057c57600080fd5b90830190602082018581111561059157600080fd5b8251600160201b8111828201881017156105aa57600080fd5b82525081516020918201929091019080838360005b838110156105d75781810151838201526020016105bf565b50505050905090810190601f1680156106045780820380516001836020036101000a031916815260200191505b5060408181526003546002890154639201de5560e01b845260048401529051600160401b9091046001600160a01b03169450639201de55935060248083019350600092829003018186803b15801561065b57600080fd5b505afa15801561066f573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561069857600080fd5b8101908080516040519392919084600160201b8211156106b757600080fd5b9083019060208201858111156106cc57600080fd5b8251600160201b8111828201881017156106e557600080fd5b82525081516020918201929091019080838360005b838110156107125781810151838201526020016106fa565b50505050905090810190601f16801561073f5780820380516001836020036101000a031916815260200191505b50604052505050935093509350509193909250565b6000546001600160a01b031681565b6000546001600160a01b031633146107bb576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b60058260058111156107c957fe5b14156107f9576003805468010000000000000000600160e01b031916600160401b6001600160a01b038416021790555b5050565b60018054808201825560008281527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf690910180546001600160a01b0319166001600160a01b038781169182179092556003805467ffffffffffffffff19811667ffffffffffffffff91821690960116949094179384905560408051606081018252918252516319f6a32560e31b8152602060048201818152885160248401528851939682880196600160401b9091049095169463cfb51928948a94929384936044019290860191908190849084905b838110156108e45781810151838201526020016108cc565b50505050905090810190601f1680156109115780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561092e57600080fd5b505afa158015610942573d6000803e3d6000fd5b505050506040513d602081101561095857600080fd5b505181526003546040516319f6a32560e31b815260206004820181815286516024840152865194820194600160401b9094046001600160a01b03169363cfb5192893889383926044909201919085019080838360005b838110156109c65781810151838201526020016109ae565b50505050905090810190601f1680156109f35780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610a1057600080fd5b505afa158015610a24573d6000803e3d6000fd5b505050506040513d6020811015610a3a57600080fd5b505190526001600160a01b03938416600090815260026020818152604092839020845181546001600160a01b03191698169790971787558301516001870155910151930192909255505056fea265627a7a7231582096e4127f446c9ef52bd03adda069d5f57c6d1c50eeac3a3d767558ed42ac41d964736f6c634300050b0032";

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
