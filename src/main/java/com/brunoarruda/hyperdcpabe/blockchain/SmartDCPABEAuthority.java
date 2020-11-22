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
import org.web3j.tuples.generated.Tuple4;
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
public class SmartDCPABEAuthority extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516109ce3803806109ce8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610969806100656000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c806337a92dc21161005b57806337a92dc21461023a5780638da5cb5b1461037757806391eca0c11461039b578063c8c76f06146103b857610088565b80631c2353e11461008d5780631d706777146100c757806327512281146101ec5780632af4c31e14610214575b600080fd5b6100b3600480360360208110156100a357600080fd5b50356001600160a01b03166103e7565b604080519115158252519081900360200190f35b6100ed600480360360208110156100dd57600080fd5b50356001600160a01b031661041a565b60405180856001600160a01b0316815260200180602001806020018467ffffffffffffffff168152602001838103835286818151815260200191508051906020019080838360005b8381101561014d578181015183820152602001610135565b50505050905090810190601f16801561017a5780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b838110156101ad578181015183820152602001610195565b50505050905090810190601f1680156101da5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102126004803603602081101561020257600080fd5b50356001600160a01b0316610586565b005b6102126004803603602081101561022a57600080fd5b50356001600160a01b031661061c565b6102126004803603606081101561025057600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561027b57600080fd5b82018360208201111561028d57600080fd5b803590602001918460018302840111640100000000831117156102af57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561030257600080fd5b82018360208201111561031457600080fd5b8035906020019184600183028401116401000000008311171561033657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610687945050505050565b61037f610772565b604080516001600160a01b039092168252519081900360200190f35b61037f600480360360208110156103b157600080fd5b5035610781565b610212600480360360408110156103ce57600080fd5b50803560ff1690602001356001600160a01b03166107ab565b6001600160a01b031660009081526002602081905260409091206001908101549081161561010002600019011604151590565b6001600160a01b0381811660009081526002602081815260408084208054600382015460018084018054865161010093821615939093026000190116889004601f8101889004880283018801909652858252979860609889988b989697959092169590949187019367ffffffffffffffff16929185918301828280156104e15780601f106104b6576101008083540402835291602001916104e1565b820191906000526020600020905b8154815290600101906020018083116104c457829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529598508794509250840190508282801561056f5780601f106105445761010080835404028352916020019161056f565b820191906000526020600020905b81548152906001019060200180831161055257829003601f168201915b505050505091509450945094509450509193509193565b6004546001600160a01b031633146105de576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6001600160a01b03166000908152600260205260409020600301805467ffffffffffffffff8082166001011667ffffffffffffffff19909116179055565b6000546001600160a01b031633146106655760405162461bcd60e51b81526004018080602001828103825260368152602001806108fe6036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b038087166001600160a01b03199283168117909355604080516080810182528481526020818101898152828401899052600060608401819052968752600282529290952081518154941693909416929092178355518051919492936107259385019291019061085c565b506040820151805161074191600284019160209091019061085c565b50606091909101516003909101805467ffffffffffffffff191667ffffffffffffffff909216919091179055505050565b6000546001600160a01b031681565b6001818154811061079157600080fd5b6000918252602090912001546001600160a01b0316905081565b6000546001600160a01b031633146107f45760405162461bcd60e51b81526004018080602001828103825260368152602001806108fe6036913960400191505060405180910390fd5b600482600581111561080257fe5b141561082857600380546001600160a01b0319166001600160a01b038316179055610858565b600282600581111561083657fe5b141561085857600480546001600160a01b0319166001600160a01b0383161790555b5050565b828054600181600116156101000203166002900490600052602060002090601f01602090048101928261089257600085556108d8565b82601f106108ab57805160ff19168380011785556108d8565b828001600101855582156108d8579182015b828111156108d85782518255916020019190600101906108bd565b506108e49291506108e8565b5090565b5b808211156108e457600081556001016108e956fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a2646970667358221220d8517d773b6c149d11e4d21943d84717104d58c15270ff6b95ab311c6978176364736f6c63430007050033";

    public static final String FUNC_ADDCERTIFIER = "addCertifier";

    public static final String FUNC_CERTIFIERADDRESSES = "certifierAddresses";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETCERTIFIER = "getCertifier";

    public static final String FUNC_INCREMENTPUBLICKEYCOUNT = "incrementPublicKeyCount";

    public static final String FUNC_ISCERTIFIER = "isCertifier";

    public static final String FUNC_OWNER = "owner";

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

    public RemoteFunctionCall<TransactionReceipt> addCertifier(String addr, String name, String email) {
        final Function function = new Function(
                FUNC_ADDCERTIFIER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr),
                new org.web3j.abi.datatypes.Utf8String(name),
                new org.web3j.abi.datatypes.Utf8String(email)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> certifierAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_CERTIFIERADDRESSES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<String, String, String, BigInteger>> getCertifier(String addr) {
        final Function function = new Function(FUNC_GETCERTIFIER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, String, BigInteger>>(function,
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

    public RemoteFunctionCall<TransactionReceipt> incrementPublicKeyCount(String addr) {
        final Function function = new Function(
                FUNC_INCREMENTPUBLICKEYCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isCertifier(String addr) {
        final Function function = new Function(FUNC_ISCERTIFIER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractDependencies(BigInteger contractType, String addr) {
        final Function function = new Function(
                FUNC_SETCONTRACTDEPENDENCIES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(contractType),
                new org.web3j.abi.datatypes.Address(160, addr)),
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
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
