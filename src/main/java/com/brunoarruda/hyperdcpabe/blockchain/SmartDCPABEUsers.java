package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161001d90610065565b604051809103906000f080158015610039573d6000803e3d6000fd5b50600260086101000a8154816001600160a01b0302191690836001600160a01b03160217905550610072565b6103cc806108d283390190565b610851806100816000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806319a50f491461005c5780634209fff114610081578063502c9bd5146100bb5780636f77926b146100f4578063d7554a6814610211575b600080fd5b61006461034c565b6040805167ffffffffffffffff9092168252519081900360200190f35b6100a76004803603602081101561009757600080fd5b50356001600160a01b031661035c565b604080519115158252519081900360200190f35b6100d8600480360360208110156100d157600080fd5b503561037d565b604080516001600160a01b039092168252519081900360200190f35b61011a6004803603602081101561010a57600080fd5b50356001600160a01b03166103a4565b60405180846001600160a01b03166001600160a01b031681526020018060200180602001838103835285818151815260200191508051906020019080838360005b8381101561017357818101518382015260200161015b565b50505050905090810190601f1680156101a05780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b838110156101d35781810151838201526020016101bb565b50505050905090810190601f1680156102005780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b61034a6004803603606081101561022757600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561025157600080fd5b82018360208201111561026357600080fd5b803590602001918460018302840111600160201b8311171561028457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156102d657600080fd5b8201836020820111156102e857600080fd5b803590602001918460018302840111600160201b8311171561030957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610593945050505050565b005b60025467ffffffffffffffff1681565b6001600160a01b031660009081526001602081905260409091200154151590565b6000818154811061038a57fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b0380821660009081526001602081905260408083208054600254938201548351639201de5560e01b81526004810191909152925194956060958695939492821693600160401b900490911691639201de55916024808301928a929190829003018186803b15801561041b57600080fd5b505afa15801561042f573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561045857600080fd5b810190808051600160201b81111561046f57600080fd5b8201602081018481111561048257600080fd5b8151600160201b81118282018710171561049b57600080fd5b5050929190505050600260089054906101000a90046001600160a01b03166001600160a01b0316639201de5584600201546040518263ffffffff1660e01b81526004018082815260200191505060006040518083038186803b15801561050057600080fd5b505afa158015610514573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561053d57600080fd5b810190808051600160201b81111561055457600080fd5b8201602081018481111561056757600080fd5b8151600160201b81118282018710171561058057600080fd5b50959b949a509850929650505050505050565b60008054600180820183558280527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e56390910180546001600160a01b0319166001600160a01b038781169182179092556002805467ffffffffffffffff19811667ffffffffffffffff91821690950116939093179283905560408051606081018252918252516319f6a32560e31b8152602060048201818152885160248401528851939682880196600160401b90049095169463cfb51928948a94929384936044019290860191908190849084905b83811015610679578181015183820152602001610661565b50505050905090810190601f1680156106a65780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156106c357600080fd5b505afa1580156106d7573d6000803e3d6000fd5b505050506040513d60208110156106ed57600080fd5b505181526002546040516319f6a32560e31b815260206004820181815286516024840152865194820194600160401b9094046001600160a01b03169363cfb5192893889383926044909201919085019080838360005b8381101561075b578181015183820152602001610743565b50505050905090810190601f1680156107885780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156107a557600080fd5b505afa1580156107b9573d6000803e3d6000fd5b505050506040513d60208110156107cf57600080fd5b505190526001600160a01b03938416600090815260016020818152604092839020845181546001600160a01b0319169816979097178755830151908601550151600290930192909255505056fea265627a7a723058207018475cad9b633af685b6887b03c36dcd6833408cecc6a60a376a76129b0b8064736f6c634300050a0032608060405234801561001057600080fd5b506103ac806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806370751f5b146100465780639201de55146100e5578063cfb5192814610102575b600080fd5b6100706004803603604081101561005c57600080fd5b5060ff19813516906020013560ff166101ba565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100aa578181015183820152602001610092565b50505050905090810190601f1680156100d75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610070600480360360208110156100fb57600080fd5b5035610242565b6101a86004803603602081101561011857600080fd5b81019060208101813564010000000081111561013357600080fd5b82018360208201111561014557600080fd5b8035906020019184600183028401116401000000008311171561016757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610347945050505050565b60408051918252519081900360200190f35b60608160ff166040519080825280601f01601f1916602001820160405280156101ea576020820181803883390190505b50905060ff19831660005b8360ff1681101561023a5782516008820260020a830290819085908490811061021a57fe5b60200101906001600160f81b031916908160001a905350506001016101f5565b505092915050565b6040805160208082528183019092526060918291906020820181803883390190505090506000805b60208110156102c0576008810260020a85026001600160f81b03198116156102b7578084848151811061029957fe5b60200101906001600160f81b031916908160001a9053506001909201915b5060010161026a565b506060816040519080825280601f01601f1916602001820160405280156102ee576020820181803883390190505b50905060005b8281101561033e5783818151811061030857fe5b602001015160f81c60f81b82828151811061031f57fe5b60200101906001600160f81b031916908160001a9053506001016102f4565b50949350505050565b6000606082905060208151111561035a57fe5b805161036a575060009050610372565b505060208101515b91905056fea265627a7a72305820cca4bd2eb96bf7e7a2d5e2ca2bb6bb09492ec6c3e1fe3f1b3d3e8f63e9ac174c64736f6c634300050a0032";

    public static final String FUNC_NUMUSERS = "numUsers";

    public static final String FUNC_ISUSER = "isUser";

    public static final String FUNC_USERADDRESSES = "userAddresses";

    public static final String FUNC_GETUSER = "getUser";

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

    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
