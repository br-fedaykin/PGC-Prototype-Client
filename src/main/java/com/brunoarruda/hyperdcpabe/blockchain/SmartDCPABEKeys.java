package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class SmartDCPABEKeys extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161102d38038061102d8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610fc8806100656000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806316aa39e01461005c5780632af4c31e1461021c578063365631d1146102425780634d75cd40146102f2578063c8c76f06146104ea575b600080fd5b61021a6004803603608081101561007257600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561009c57600080fd5b8201836020820111156100ae57600080fd5b803590602001918460018302840111600160201b831117156100cf57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561012157600080fd5b82018360208201111561013357600080fd5b803590602001918460018302840111600160201b8311171561015457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101a657600080fd5b8201836020820111156101b857600080fd5b803590602001918460018302840111600160201b831117156101d957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610519945050505050565b005b61021a6004803603602081101561023257600080fd5b50356001600160a01b0316610682565b61021a600480360361018081101561025957600080fd5b60408051606081810183526001600160a01b0385351694602081013594810193909260a084019290918401906003908390839080828437600092019190915250506040805160608181018352939660ff1986351696602087013560ff1696919590945060a08201939091019060039083908390808284376000920191909152509194505060ff198235169250506020013560ff166106fc565b6103a66004803603604081101561030857600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561033257600080fd5b82018360208201111561034457600080fd5b803590602001918460018302840111600160201b8311171561036557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a0d945050505050565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156103eb5781810151838201526020016103d3565b50505050905090810190601f1680156104185780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b8381101561044b578181015183820152602001610433565b50505050905090810190601f1680156104785780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156104ab578181015183820152602001610493565b50505050905090810190601f1680156104d85780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61021a6004803603604081101561050057600080fd5b50803560ff1690602001356001600160a01b0316610e51565b6001546040516319f6a32560e31b81526020600482018181528651602484015286516000946001600160a01b03169363cfb51928938993928392604401918501908083838b5b8381101561057757818101518382015260200161055f565b50505050905090810190601f1680156105a45780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156105c157600080fd5b505afa1580156105d5573d6000803e3d6000fd5b505050506040513d60208110156105eb57600080fd5b505190506105f7610f02565b600080602086518161060557fe5b069050610610610f02565b600080602088518161061e57fe5b06905060208901518652604089015160208701526060890151604087015260808901519450602088015183526040880151602084015260608801516040840152608088015191506106758b888888888888886106fc565b5050505050505050505050565b6000546001600160a01b031633146106da576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60025460408051631c2353e160e01b81526001600160a01b038b8116600483015291519190921691631c2353e1916024808301926020929190829003018186803b15801561074957600080fd5b505afa15801561075d573d6000803e3d6000fd5b505050506040513d602081101561077357600080fd5b505161077b57fe5b610783610f20565b506040805160a0810182528751815260208089015190820152878201519181019190915260ff198616606082015260ff851660808201526107c2610f20565b6040805160a0810182528651815260208088015190820152908101866002602002015181526020018560ff191681526020018460ff168152509050604051806040016040528083815260200182815250600460008c6001600160a01b03166001600160a01b0316815260200190815260200160002060008b815260200190815260200160002060008201518160000160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff160217905550505060208201518160040160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff1602179055505050905050600360008b6001600160a01b03166001600160a01b03168152602001908152602001600020899080600181540180825580915050906001820390600052602060002001600090919290919091505550600260009054906101000a90046001600160a01b03166001600160a01b031663275122818b6040518263ffffffff1660e01b815260040180826001600160a01b03166001600160a01b03168152602001915050600060405180830381600087803b1580156109e957600080fd5b505af11580156109fd573d6000803e3d6000fd5b5050505050505050505050505050565b6001600160a01b03808316600090815260046020818152604080842060015491516319f6a32560e31b81529384018381528751602486015287516060978897889790969495879592169363cfb51928938c9391928392604490910191908501908083838b5b83811015610a8a578181015183820152602001610a72565b50505050905090810190601f168015610ab75780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610ad457600080fd5b505afa158015610ae8573d6000803e3d6000fd5b505050506040513d6020811015610afe57600080fd5b50518152602081019190915260409081016000908120805460018083015460028401549154600385015487516370751f5b60e01b8152600882901b60ff19166004820152600160f81b90910460ff16602482015296519497508b969395919492936001600160a01b03909116926370751f5b92604480840193919291829003018186803b158015610b8e57600080fd5b505afa158015610ba2573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610bcb57600080fd5b810190808051600160201b811115610be257600080fd5b82016020810184811115610bf557600080fd5b8151600160201b811182820187101715610c0e57600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610c5a5780518252601f199092019160209182019101610c3b565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052826004016000015483600401600101548460040160020154600160009054906101000a90046001600160a01b03166001600160a01b03166370751f5b8760040160030160009054906101000a900460081b88600401600301601f9054906101000a900460ff166040518363ffffffff1660e01b8152600401808360ff191660ff191681526020018260ff1660ff1681526020019250505060006040518083038186803b158015610d4057600080fd5b505afa158015610d54573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610d7d57600080fd5b810190808051600160201b811115610d9457600080fd5b82016020810184811115610da757600080fd5b8151600160201b811182820187101715610dc057600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610e0c5780518252601f199092019160209182019101610ded565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052935093509350509250925092565b6000546001600160a01b03163314610e9a5760405162461bcd60e51b8152600401808060200182810382526045815260200180610f4f6045913960600191505060405180910390fd5b6004826004811115610ea857fe5b1415610ece57600180546001600160a01b0319166001600160a01b038316179055610efe565b6000826004811115610edc57fe5b1415610efe57600280546001600160a01b0319166001600160a01b0383161790555b5050565b60405180606001604052806003906020820280388339509192915050565b6040805160a0810182526000808252602082018190529181018290526060810182905260808101919091529056fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742ea265627a7a72305820c861f390efa2dd88a9000bc0d85d95f6253f3ad2ee6062759e0092a3f64c515964736f6c634300050a0032";

    public static final String FUNC_ADDPUBLICKEY = "addPublicKey";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETPUBLICKEY = "getPublicKey";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    @Deprecated
    protected SmartDCPABEKeys(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABEKeys(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABEKeys(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABEKeys(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addPublicKey(String addr, String name, byte[] eg1g1ai, byte[] g1yi) {
        final Function function = new Function(
                FUNC_ADDPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.DynamicBytes(eg1g1ai), 
                new org.web3j.abi.datatypes.DynamicBytes(g1yi)), 
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

    public RemoteCall<TransactionReceipt> addPublicKey(String addr, byte[] name, List<byte[]> eg1g1aiChunks, byte[] eg1g1aiLastChunk, BigInteger eg1g1aiLastChunkSize, List<byte[]> g1yiChunks, byte[] g1yiLastChunk, BigInteger g1yiLastChunkSize) {
        final Function function = new Function(
                FUNC_ADDPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.generated.Bytes32(name), 
                new org.web3j.abi.datatypes.generated.StaticArray3<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(eg1g1aiChunks, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.generated.Bytes31(eg1g1aiLastChunk), 
                new org.web3j.abi.datatypes.generated.Uint8(eg1g1aiLastChunkSize), 
                new org.web3j.abi.datatypes.generated.StaticArray3<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(g1yiChunks, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.generated.Bytes31(g1yiLastChunk), 
                new org.web3j.abi.datatypes.generated.Uint8(g1yiLastChunkSize)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, byte[], byte[]>> getPublicKey(String addr, String name) {
        final Function function = new Function(FUNC_GETPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.Utf8String(name)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteCall<Tuple3<String, byte[], byte[]>>(
                new Callable<Tuple3<String, byte[], byte[]>>() {
                    @Override
                    public Tuple3<String, byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, byte[], byte[]>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
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

    @Deprecated
    public static SmartDCPABEKeys load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEKeys(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABEKeys load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEKeys(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABEKeys load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEKeys(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABEKeys load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEKeys(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABEKeys> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEKeys.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEKeys> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEKeys.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEKeys> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEKeys.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEKeys> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEKeys.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
