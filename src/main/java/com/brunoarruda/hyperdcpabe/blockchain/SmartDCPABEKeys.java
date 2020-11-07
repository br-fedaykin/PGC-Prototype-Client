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
public class SmartDCPABEKeys extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516110ac3803806110ac8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055611047806100656000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806316aa39e014610067578063243e337d146102275780632af4c31e146103545780634d75cd401461037a5780638da5cb5b14610572578063c8c76f0614610596575b600080fd5b6102256004803603608081101561007d57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156100a757600080fd5b8201836020820111156100b957600080fd5b803590602001918460018302840111600160201b831117156100da57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561012c57600080fd5b82018360208201111561013e57600080fd5b803590602001918460018302840111600160201b8311171561015f57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101b157600080fd5b8201836020820111156101c357600080fd5b803590602001918460018302840111600160201b831117156101e457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506105c5945050505050565b005b610225600480360361018081101561023e57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561026857600080fd5b82018360208201111561027a57600080fd5b803590602001918460018302840111600160201b8311171561029b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250506040805160608181019092529396959481810194935091506003908390839080828437600092019190915250506040805160608181018352939660ff1986351696602087013560ff1696919590945060a08201939091019060039083908390808284376000920191909152509194505060ff198235169250506020013560ff16610657565b6102256004803603602081101561036a57600080fd5b50356001600160a01b0316610a06565b61042e6004803603604081101561039057600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156103ba57600080fd5b8201836020820111156103cc57600080fd5b803590602001918460018302840111600160201b831117156103ed57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a71945050505050565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b8381101561047357818101518382015260200161045b565b50505050905090810190601f1680156104a05780820380516001836020036101000a031916815260200191505b50848103835286518152865160209182019188019080838360005b838110156104d35781810151838201526020016104bb565b50505050905090810190601f1680156105005780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b8381101561053357818101518382015260200161051b565b50505050905090810190601f1680156105605780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61057a610e35565b604080516001600160a01b039092168252519081900360200190f35b610225600480360360408110156105ac57600080fd5b50803560ff1690602001356001600160a01b0316610e44565b6105cd610ef5565b60008060208551816105db57fe5b0690506105e6610ef5565b60008060208751816105f457fe5b069050602088015186526040880151602087015260608801516040870152608088015194506020870151835260408701516020840152606087015160408401526080870151915061064b8a8a888888888888610657565b50505050505050505050565b60025460408051631c2353e160e01b81526001600160a01b038b8116600483015291519190921691631c2353e1916024808301926020929190829003018186803b1580156106a457600080fd5b505afa1580156106b8573d6000803e3d6000fd5b505050506040513d60208110156106ce57600080fd5b50516106d657fe5b6106de610f13565b506040805160a0810182528751815260208089015190820152878201519181019190915260ff198616606082015260ff8516608082015261071d610f13565b506040805160a0810182528551815260208087015181830152868301518284015260ff198616606083015260ff85166080830152825180840184528481528082018390526001600160a01b038d1660009081526004835284902093518c519394919391928d9282918401908083835b602083106107ab5780518252601f19909201916020918201910161078c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060008201518160000160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff160217905550505060208201518160040160008201518160000155602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160f81b03021916908360081c0217905550608082015181600301601f6101000a81548160ff021916908360ff16021790555050509050506000600460008c6001600160a01b03166001600160a01b031681526020019081526020016000208a6040518082805190602001908083835b6020831061091a5780518252601f1990920191602091820191016108fb565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054929092141591506109949050576001600160a01b038a1660009081526003602090815260408220805460018101808355918452928290208c51919361099193910191908d0190610f41565b50505b60025460408051632751228160e01b81526001600160a01b038d8116600483015291519190921691632751228191602480830192600092919082900301818387803b1580156109e257600080fd5b505af11580156109f6573d6000803e3d6000fd5b5050505050505050505050505050565b6000546001600160a01b03163314610a4f5760405162461bcd60e51b8152600401808060200182810382526036815260200180610fdd6036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6060806060600060046000876001600160a01b03166001600160a01b03168152602001908152602001600020856040518082805190602001908083835b60208310610acd5780518252601f199092019160209182019101610aae565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003909101842080546001808301546002840154915460038501546370751f5b60e01b8a52600881901b60ff191660048b0152600160f81b900460ff1660248a015294519398508c979296509450926001600160a01b0316916370751f5b91604480820192600092909190829003018186803b158015610b7257600080fd5b505afa158015610b86573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610baf57600080fd5b810190808051600160201b811115610bc657600080fd5b82016020810184811115610bd957600080fd5b8151600160201b811182820187101715610bf257600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610c3e5780518252601f199092019160209182019101610c1f565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052826004016000015483600401600101548460040160020154600160009054906101000a90046001600160a01b03166001600160a01b03166370751f5b8760040160030160009054906101000a900460081b88600401600301601f9054906101000a900460ff166040518363ffffffff1660e01b8152600401808360ff191660ff191681526020018260ff1660ff1681526020019250505060006040518083038186803b158015610d2457600080fd5b505afa158015610d38573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610d6157600080fd5b810190808051600160201b811115610d7857600080fd5b82016020810184811115610d8b57600080fd5b8151600160201b811182820187101715610da457600080fd5b50509291905050506040516020018085815260200184815260200183815260200182805190602001908083835b60208310610df05780518252601f199092019160209182019101610dd1565b6001836020036101000a038019825116818451168082178552505050505050905001945050505050604051602081830303815290604052935093509350509250925092565b6000546001600160a01b031681565b6000546001600160a01b03163314610e8d5760405162461bcd60e51b8152600401808060200182810382526036815260200180610fdd6036913960400191505060405180910390fd5b6005826005811115610e9b57fe5b1415610ec157600180546001600160a01b0319166001600160a01b038316179055610ef1565b6000826005811115610ecf57fe5b1415610ef157600280546001600160a01b0319166001600160a01b0383161790555b5050565b60405180606001604052806003906020820280388339509192915050565b6040805160a08101825260008082526020820181905291810182905260608101829052608081019190915290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610f8257805160ff1916838001178555610faf565b82800160010185558215610faf579182015b82811115610faf578251825591602001919060010190610f94565b50610fbb929150610fbf565b5090565b610fd991905b80821115610fbb5760008155600101610fc5565b9056fe4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a265627a7a7230582023dd77453dda46a58e1b455e2d6f6f46ce6c0251a87967074315d2a88f6e7d2f64736f6c634300050a0032";

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

    public RemoteCall<TransactionReceipt> addPublicKey(String addr, String name, List<byte[]> eg1g1aiChunks, byte[] eg1g1aiLastChunk, BigInteger eg1g1aiLastChunkSize, List<byte[]> g1yiChunks, byte[] g1yiLastChunk, BigInteger g1yiLastChunkSize) {
        final Function function = new Function(
                FUNC_ADDPUBLICKEY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr),
                new org.web3j.abi.datatypes.Utf8String(name),
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

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
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
