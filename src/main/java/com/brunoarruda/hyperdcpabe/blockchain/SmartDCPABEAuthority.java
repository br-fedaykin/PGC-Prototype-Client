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
import org.web3j.tuples.generated.Tuple4;
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
public class SmartDCPABEAuthority extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610c1b380380610c1b8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610bb6806100656000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806337a92dc21161005b57806337a92dc21461024d5780638da5cb5b1461038657806391eca0c1146103aa578063c8c76f06146103c757610088565b80631c2353e11461008d5780631d706777146100c757806327512281146101ff5780632af4c31e14610227575b600080fd5b6100b3600480360360208110156100a357600080fd5b50356001600160a01b03166103f6565b604080519115158252519081900360200190f35b6100ed600480360360208110156100dd57600080fd5b50356001600160a01b0316610416565b60405180856001600160a01b03166001600160a01b0316815260200180602001806020018467ffffffffffffffff1667ffffffffffffffff168152602001838103835286818151815260200191508051906020019080838360005b83811015610160578181015183820152602001610148565b50505050905090810190601f16801561018d5780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b838110156101c05781810151838201526020016101a8565b50505050905090810190601f1680156101ed5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102256004803603602081101561021557600080fd5b50356001600160a01b03166106c5565b005b6102256004803603602081101561023d57600080fd5b50356001600160a01b031661075b565b6102256004803603606081101561026357600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561028d57600080fd5b82018360208201111561029f57600080fd5b803590602001918460018302840111600160201b831117156102c057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561031257600080fd5b82018360208201111561032457600080fd5b803590602001918460018302840111600160201b8311171561034557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506107d5945050505050565b61038e610a57565b604080516001600160a01b039092168252519081900360200190f35b61038e600480360360208110156103c057600080fd5b5035610a66565b610225600480360360408110156103dd57600080fd5b50803560ff1690602001356001600160a01b0316610a8d565b6001600160a01b0316600090815260026020526040902060010154151590565b6001600160a01b03808216600090815260026020526040808220805460035460018301548451639201de5560e01b8152600481019190915293519495606095869588959483169390921691639201de559160248083019288929190829003018186803b15801561048557600080fd5b505afa158015610499573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156104c257600080fd5b8101908080516040519392919084600160201b8211156104e157600080fd5b9083019060208201858111156104f657600080fd5b8251600160201b81118282018810171561050f57600080fd5b82525081516020918201929091019080838360005b8381101561053c578181015183820152602001610524565b50505050905090810190601f1680156105695780820380516001836020036101000a031916815260200191505b5060408181526003546002890154639201de5560e01b8452600484015290516001600160a01b039091169450639201de55935060248083019350600092829003018186803b1580156105ba57600080fd5b505afa1580156105ce573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156105f757600080fd5b8101908080516040519392919084600160201b82111561061657600080fd5b90830190602082018581111561062b57600080fd5b8251600160201b81118282018810171561064457600080fd5b82525081516020918201929091019080838360005b83811015610671578181015183820152602001610659565b50505050905090810190601f16801561069e5780820380516001836020036101000a031916815260200191505b5060405250505060039390930154919890975091955067ffffffffffffffff169350915050565b6005546001600160a01b0316331461071d576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6001600160a01b03166000908152600260205260409020600301805467ffffffffffffffff8082166001011667ffffffffffffffff19909116179055565b6000546001600160a01b031633146107b3576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60018054808201825560009182527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038681169182179092556040805160808101825291825260035490516319f6a32560e31b8152602060048201818152885160248401528851949682880196949094169463cfb51928948a949293849360440192908601918190849084905b8381101561088e578181015183820152602001610876565b50505050905090810190601f1680156108bb5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156108d857600080fd5b505afa1580156108ec573d6000803e3d6000fd5b505050506040513d602081101561090257600080fd5b505181526003546040516319f6a32560e31b8152602060048201818152865160248401528651948201946001600160a01b039094169363cfb5192893889383926044909201919085019080838360005b8381101561096a578181015183820152602001610952565b50505050905090810190601f1680156109975780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156109b457600080fd5b505afa1580156109c8573d6000803e3d6000fd5b505050506040513d60208110156109de57600080fd5b50518152600060209182018190526001600160a01b0395861681526002808352604091829020845181546001600160a01b031916981697909717875591830151600187015582015190850155606001516003909301805467ffffffffffffffff191667ffffffffffffffff909416939093179092555050565b6000546001600160a01b031681565b60018181548110610a7357fe5b6000918252602090912001546001600160a01b0316905081565b6000546001600160a01b03163314610ae5576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6005826005811115610af357fe5b1415610b1957600380546001600160a01b0319166001600160a01b038316179055610b7d565b6004826005811115610b2757fe5b1415610b4d57600480546001600160a01b0319166001600160a01b038316179055610b7d565b6002826005811115610b5b57fe5b1415610b7d57600580546001600160a01b0319166001600160a01b0383161790555b505056fea265627a7a723158205825ffa7ea3d5c04dd48a46c39647fd170ad67c48ad75b1ae4c8413b9620905464736f6c634300050b0032";

    public static final String FUNC_ISCERTIFIER = "isCertifier";

    public static final String FUNC_GETCERTIFIER = "getCertifier";

    public static final String FUNC_INCREMENTPUBLICKEYCOUNT = "incrementPublicKeyCount";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_ADDCERTIFIER = "addCertifier";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_CERTIFIERADDRESSES = "certifierAddresses";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    @Deprecated
    protected SmartDCPABEAuthority(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABEAuthority(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABEAuthority(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABEAuthority(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> isCertifier(String addr) {
        final Function function = new Function(FUNC_ISCERTIFIER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Tuple4<String, String, String, BigInteger>> getCertifier(String addr) {
        final Function function = new Function(FUNC_GETCERTIFIER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}));
        return new RemoteCall<Tuple4<String, String, String, BigInteger>>(
                new Callable<Tuple4<String, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> incrementPublicKeyCount(String addr) {
        final Function function = new Function(
                FUNC_INCREMENTPUBLICKEYCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addCertifier(String addr, String name, String email) {
        final Function function = new Function(
                FUNC_ADDCERTIFIER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(email)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> certifierAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_CERTIFIERADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
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

    @Deprecated
    public static SmartDCPABEAuthority load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEAuthority(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABEAuthority load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEAuthority(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABEAuthority load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEAuthority(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABEAuthority load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEAuthority(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
