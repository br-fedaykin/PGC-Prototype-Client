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
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610a78380380610a788339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610a13806100656000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063502c9bd51161005b578063502c9bd5146101095780636f77926b14610142578063c8c76f061461025f578063d7554a681461028e5761007d565b806319a50f49146100825780632af4c31e146100a75780634209fff1146100cf575b600080fd5b61008a6103c7565b6040805167ffffffffffffffff9092168252519081900360200190f35b6100cd600480360360208110156100bd57600080fd5b50356001600160a01b03166103d7565b005b6100f5600480360360208110156100e557600080fd5b50356001600160a01b0316610451565b604080519115158252519081900360200190f35b6101266004803603602081101561011f57600080fd5b5035610471565b604080516001600160a01b039092168252519081900360200190f35b6101686004803603602081101561015857600080fd5b50356001600160a01b0316610498565b60405180846001600160a01b03166001600160a01b031681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156101c15781810151838201526020016101a9565b50505050905090810190601f1680156101ee5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610221578181015183820152602001610209565b50505050905090810190601f16801561024e5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6100cd6004803603604081101561027557600080fd5b50803560ff1690602001356001600160a01b0316610685565b6100cd600480360360608110156102a457600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102ce57600080fd5b8201836020820111156102e057600080fd5b803590602001918460018302840111600160201b8311171561030157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561035357600080fd5b82018360208201111561036557600080fd5b803590602001918460018302840111600160201b8311171561038657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610710945050505050565b60035467ffffffffffffffff1681565b6000546001600160a01b0316331461042f576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b0316600090815260026020526040902060010154151590565b6001818154811061047e57fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b03808216600090815260026020526040808220805460035460018301548451639201de5560e01b8152600481019190915293519495606095869593821693600160401b90930490911691639201de55916024808301928a929190829003018186803b15801561050d57600080fd5b505afa158015610521573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561054a57600080fd5b810190808051600160201b81111561056157600080fd5b8201602081018481111561057457600080fd5b8151600160201b81118282018710171561058d57600080fd5b5050929190505050600360089054906101000a90046001600160a01b03166001600160a01b0316639201de5584600201546040518263ffffffff1660e01b81526004018082815260200191505060006040518083038186803b1580156105f257600080fd5b505afa158015610606573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561062f57600080fd5b810190808051600160201b81111561064657600080fd5b8201602081018481111561065957600080fd5b8151600160201b81118282018710171561067257600080fd5b50959b949a509850929650505050505050565b6000546001600160a01b031633146106ce5760405162461bcd60e51b815260040180806020018281038252604581526020018061099a6045913960600191505060405180910390fd5b60048260048111156106dc57fe5b141561070c576003805468010000000000000000600160e01b031916600160401b6001600160a01b038416021790555b5050565b60018054808201825560008281527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf690910180546001600160a01b0319166001600160a01b038781169182179092556003805467ffffffffffffffff19811667ffffffffffffffff91821690960116949094179384905560408051606081018252918252516319f6a32560e31b8152602060048201818152885160248401528851939682880196600160401b9091049095169463cfb51928948a94929384936044019290860191908190849084905b838110156107f75781810151838201526020016107df565b50505050905090810190601f1680156108245780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561084157600080fd5b505afa158015610855573d6000803e3d6000fd5b505050506040513d602081101561086b57600080fd5b505181526003546040516319f6a32560e31b815260206004820181815286516024840152865194820194600160401b9094046001600160a01b03169363cfb5192893889383926044909201919085019080838360005b838110156108d95781810151838201526020016108c1565b50505050905090810190601f1680156109065780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561092357600080fd5b505afa158015610937573d6000803e3d6000fd5b505050506040513d602081101561094d57600080fd5b505190526001600160a01b03938416600090815260026020818152604092839020845181546001600160a01b03191698169790971787558301516001870155910151930192909255505056fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742ea265627a7a72305820f528fd40793ceb2744c3afcfa4e84339fdb8028ed7d75c0cfe791f76a76d2cb164736f6c634300050a0032";

    public static final String FUNC_NUMUSERS = "numUsers";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_ISUSER = "isUser";

    public static final String FUNC_USERADDRESSES = "userAddresses";

    public static final String FUNC_GETUSER = "getUser";

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
