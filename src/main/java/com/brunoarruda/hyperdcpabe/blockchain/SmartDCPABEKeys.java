package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516110533803806110538339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055610fee806100656000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806316aa39e0146100675780632af4c31e14610227578063365631d11461024d5780634d75cd40146102fd5780638da5cb5b146104f5578063c8c76f0614610519575b600080fd5b6102256004803603608081101561007d57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156100a757600080fd5b8201836020820111156100b957600080fd5b803590602001918460018302840111600160201b831117156100da57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561012c57600080fd5b82018360208201111561013e57600080fd5b803590602001918460018302840111600160201b8311171561015f57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101b157600080fd5b8201836020820111156101c357600080fd5b803590602001918460018302840111600160201b831117156101e457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610548945050505050565b005b6102256004803603602081101561023d57600080fd5b50356001600160a01b03166106b1565b610225600480360361018081101561026457600080fd5b60408051606081810183526001600160a01b0385351694602081013594810193909260a084019290918401906003908390839080828437600092019190915250506040805160608181018352939660ff1986351696602087013560ff1696919590945060a08201939091019060039083908390808284376000920191909152509194505060ff198235169250506020013560ff1661071c565b6103b16004803603604081101561031357600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561033d57600080fd5b82018360208201111561034f57600080fd5b803590602001918460018302840111600160201b8311171561037057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a33945050505050565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156103f65781810151838201526020016103de565b50505050905090810190601f1680156104235780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b8381101561045657818101518382015260200161043e565b50505050905090810190601f1680156104835780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156104b657818101518382015260200161049e565b50505050905090810190601f1680156104e35780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6104fd610e77565b604080516001600160a01b039092168252519081900360200190f35b6102256004803603604081101561052f57600080fd5b50803560ff1690602001356001600160a01b0316610e86565b6001546040516319f6a32560e31b81526020600482018181528651602484015286516000946001600160a01b03169363cfb51928938993928392604401918501908083838b5b838110156105a657818101518382015260200161058e565b50505050905090810190601f1680156105d35780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156105f057600080fd5b505afa158015610604573d6000803e3d6000fd5b505050506040513d602081101561061a57600080fd5b50519050610626610f37565b600080602086518161063457fe5b06905061063f610f37565b600080602088518161064d57fe5b06905060208901518652604089015160208701526060890151604087015260808901519450602088015183526040880151602084015260608801516040840152608088015191506106a48b8888888888888861071c565b5050505050505050505050565b6000546001600160a01b031633146106fa5760405162461bcd60e51b8152600401808060200182810382526036815260200180610f846036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60025460408051631c2353e160e01b81526001600160a01b038b8116600483015291519190921691631c2353e1916024808301926020929190829003018186803b15801561076957600080fd5b505afa15801561077d573d6000803e3d6000fd5b505050506040513d602081101561079357600080fd5b505161079b57fe5b6107a3610f55565b506040805160a0810182528751815260208089015190820152878201519181019190915260ff198616606082015260ff851660808201526107e2610f55565b6040805160a0810182528651815260208088015190820152908101866002602002015181526020018560ff191681526020018460ff168152509050604051806040016040528083815260200182815250600460008c6001600160a01b03166001600160a01b0316815260200190815260200160002060008b815260200190815260200160002060008201518160000160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff160217905550505060208201518160040160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff16021790555050509050506000600460008c6001600160a01b03166001600160a01b0316815260200190815260200160002060008b81526020019081526020016000206000016000015460001c14156109c1576001600160a01b038a166000908152600360209081526040822080546001810182559083529120018990555b60025460408051632751228160e01b81526001600160a01b038d8116600483015291519190921691632751228191602480830192600092919082900301818387803b158015610a0f57600080fd5b505af1158015610a23573d6000803e3d6000fd5b5050505050505050505050505050565b6001600160a01b03808316600090815260046020818152604080842060015491516319f6a32560e31b81529384018381528751602486015287516060978897889790969495879592169363cfb51928938c9391928392604490910191908501908083838b5b83811015610ab0578181015183820152602001610a98565b50505050905090810190601f168015610add5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610afa57600080fd5b505afa158015610b0e573d6000803e3d6000fd5b505050506040513d6020811015610b2457600080fd5b50518152602081019190915260409081016000908120805460018083015460028401549154600385015487516370751f5b60e01b8152600882901b60ff19166004820152600160f81b90910460ff16602482015296519497508b969395919492936001600160a01b03909116926370751f5b92604480840193919291829003018186803b158015610bb457600080fd5b505afa158015610bc8573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610bf157600080fd5b810190808051600160201b811115610c0857600080fd5b82016020810184811115610c1b57600080fd5b8151600160201b811182820187101715610c3457600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610c805780518252601f199092019160209182019101610c61565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052826004016000015483600401600101548460040160020154600160009054906101000a90046001600160a01b03166001600160a01b03166370751f5b8760040160030160009054906101000a900460081b88600401600301601f9054906101000a900460ff166040518363ffffffff1660e01b8152600401808360ff191660ff191681526020018260ff1660ff1681526020019250505060006040518083038186803b158015610d6657600080fd5b505afa158015610d7a573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610da357600080fd5b810190808051600160201b811115610dba57600080fd5b82016020810184811115610dcd57600080fd5b8151600160201b811182820187101715610de657600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610e325780518252601f199092019160209182019101610e13565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052935093509350509250925092565b6000546001600160a01b031681565b6000546001600160a01b03163314610ecf5760405162461bcd60e51b8152600401808060200182810382526036815260200180610f846036913960400191505060405180910390fd5b6005826005811115610edd57fe5b1415610f0357600180546001600160a01b0319166001600160a01b038316179055610f33565b6000826005811115610f1157fe5b1415610f3357600280546001600160a01b0319166001600160a01b0383161790555b5050565b60405180606001604052806003906020820280388339509192915050565b6040805160a0810182526000808252602082018190529181018290526060810182905260808101919091529056fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a265627a7a723058202384f3f6bb7e5f17fb56be2b2386081cc28fd4b92846f7f9528512cb3233d90064736f6c634300050a0032";

    public static final String FUNC_ADDPUBLICKEY = "addPublicKey";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETPUBLICKEY = "getPublicKey";

    public static final String FUNC_OWNER = "owner";

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
