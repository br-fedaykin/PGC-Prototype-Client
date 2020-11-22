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
import org.web3j.protocol.core.RemoteFunctionCall;
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
 * <p>Generated with web3j version 4.7.0.
 */

@SuppressWarnings("rawtypes")
public class SmartDCPABEUsers extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516108b03803806108b08339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b031990921691909117905561084b806100656000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80636f77926b1161005b5780636f77926b1461014d5780638da5cb5b14610261578063c8c76f0614610269578063d7554a681461029857610088565b806319a50f491461008d5780632af4c31e146100b25780634209fff1146100da578063502c9bd514610114575b600080fd5b6100956103d5565b6040805167ffffffffffffffff9092168252519081900360200190f35b6100d8600480360360208110156100c857600080fd5b50356001600160a01b03166103e5565b005b610100600480360360208110156100f057600080fd5b50356001600160a01b0316610450565b604080519115158252519081900360200190f35b6101316004803603602081101561012a57600080fd5b5035610483565b604080516001600160a01b039092168252519081900360200190f35b6101736004803603602081101561016357600080fd5b50356001600160a01b03166104ad565b60405180846001600160a01b031681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156101c35781810151838201526020016101ab565b50505050905090810190601f1680156101f05780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561022357818101518382015260200161020b565b50505050905090810190601f1680156102505780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b610131610604565b6100d86004803603604081101561027f57600080fd5b50803560ff1690602001356001600160a01b0316610613565b6100d8600480360360608110156102ae57600080fd5b6001600160a01b0382351691908101906040810160208201356401000000008111156102d957600080fd5b8201836020820111156102eb57600080fd5b8035906020019184600183028401116401000000008311171561030d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561036057600080fd5b82018360208201111561037257600080fd5b8035906020019184600183028401116401000000008311171561039457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610660945050505050565b60035467ffffffffffffffff1681565b6000546001600160a01b0316331461042e5760405162461bcd60e51b81526004018080602001828103825260368152602001806107e06036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b031660009081526002602081905260409091206001908101549081161561010002600019011604151590565b6001818154811061049357600080fd5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b038181166000908152600260208181526040808420805460018083018054855161010093821615939093026000190116879004601f8101879004870283018701909552848252969760609788979496939091169490938601928491908301828280156105615780601f1061053657610100808354040283529160200191610561565b820191906000526020600020905b81548152906001019060200180831161054457829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959750869450925084019050828280156105ef5780601f106105c4576101008083540402835291602001916105ef565b820191906000526020600020905b8154815290600101906020018083116105d257829003601f168201915b50505050509050935093509350509193909250565b6000546001600160a01b031681565b6000546001600160a01b0316331461065c5760405162461bcd60e51b81526004018080602001828103825260368152602001806107e06036913960400191505060405180910390fd5b5050565b6001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b038087166001600160a01b031992831681179093556003805467ffffffffffffffff80821687011667ffffffffffffffff1990911617905560408051606081018252848152602081810189815282840189905260009687526002825292909520815181549416939094169290921783555180519194929361071a9385019291019061073e565b506040820151805161073691600284019160209091019061073e565b505050505050565b828054600181600116156101000203166002900490600052602060002090601f01602090048101928261077457600085556107ba565b82601f1061078d57805160ff19168380011785556107ba565b828001600101855582156107ba579182015b828111156107ba57825182559160200191906001019061079f565b506107c69291506107ca565b5090565b5b808211156107c657600081556001016107cb56fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a26469706673582212208204888f02e722e6204f5bd53abb73a2ae759603558949b493d72326f41355bb64736f6c63430007050033";

    public static final String FUNC_ADDUSER = "addUser";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETUSER = "getUser";

    public static final String FUNC_ISUSER = "isUser";

    public static final String FUNC_NUMUSERS = "numUsers";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_USERADDRESSES = "userAddresses";

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

    public RemoteFunctionCall<TransactionReceipt> addUser(String addr, String name, String email) {
        final Function function = new Function(
                FUNC_ADDUSER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr),
                new org.web3j.abi.datatypes.Utf8String(name),
                new org.web3j.abi.datatypes.Utf8String(email)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> getUser(String addr) {
        final Function function = new Function(FUNC_GETUSER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
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

    public RemoteFunctionCall<Boolean> isUser(String addr) {
        final Function function = new Function(FUNC_ISUSER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> numUsers() {
        final Function function = new Function(FUNC_NUMUSERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> userAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_USERADDRESSES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEUsers> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEUsers.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
