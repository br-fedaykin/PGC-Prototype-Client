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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple9;
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
public class SmartDCPABEFiles extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516119473803806119478339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556118e2806100656000396000f3fe608060405234801561001057600080fd5b50600436106100a85760003560e01c8063445c7d8611610071578063445c7d86146105615780638da5cb5b146107ce578063905da440146107f25780639b04d0d514610827578063c8c76f061461085f578063f08d61121461088e576100a8565b8062a12d78146100ad5780632af4c31e146103295780632ef01983146103515780633a76f5ad1461040e5780634190a94214610432575b600080fd5b6100d9600480360360408110156100c357600080fd5b506001600160a01b0381351690602001356109a1565b60405180806020018a6001600160401b03166001600160401b03168152602001898152602001888152602001876001600160401b03166001600160401b031681526020018060200180602001806020018060200186810386528f818151815260200191508051906020019080838360005b8381101561016257818101518382015260200161014a565b50505050905090810190601f16801561018f5780820380516001836020036101000a031916815260200191505b5086810385528a5181528a516020918201918c019080838360005b838110156101c25781810151838201526020016101aa565b50505050905090810190601f1680156101ef5780820380516001836020036101000a031916815260200191505b5086810384528951815289516020918201918b019080838360005b8381101561022257818101518382015260200161020a565b50505050905090810190601f16801561024f5780820380516001836020036101000a031916815260200191505b5086810383528851815288516020918201918a019080838360005b8381101561028257818101518382015260200161026a565b50505050905090810190601f1680156102af5780820380516001836020036101000a031916815260200191505b50868103825287518152875160209182019189019080838360005b838110156102e25781810151838201526020016102ca565b50505050905090810190601f16801561030f5780820380516001836020036101000a031916815260200191505b509e50505050505050505050505050505060405180910390f35b61034f6004803603602081101561033f57600080fd5b50356001600160a01b0316610d8c565b005b6103f56004803603602081101561036757600080fd5b810190602081018135600160201b81111561038157600080fd5b82018360208201111561039357600080fd5b803590602001918460018302840111600160201b831117156103b457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610e06945050505050565b60408051600792830b90920b8252519081900360200190f35b610416610f4e565b604080516001600160401b039092168252519081900360200190f35b6104166004803603606081101561044857600080fd5b810190602081018135600160201b81111561046257600080fd5b82018360208201111561047457600080fd5b803590602001918460018302840111600160201b8311171561049557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104e757600080fd5b8201836020820111156104f957600080fd5b803590602001918460018302840111600160201b8311171561051a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505050903561ffff169150610f649050565b61034f600480360361014081101561057857600080fd5b6001600160a01b03823516916020810135916001600160401b03604083013581169260608101359260808201359260a083013516919081019060e0810160c0820135600160201b8111156105cb57600080fd5b8201836020820111156105dd57600080fd5b803590602001918460018302840111600160201b831117156105fe57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561065057600080fd5b82018360208201111561066257600080fd5b803590602001918460018302840111600160201b8311171561068357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156106d557600080fd5b8201836020820111156106e757600080fd5b803590602001918460018302840111600160201b8311171561070857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561075a57600080fd5b82018360208201111561076c57600080fd5b803590602001918460018302840111600160201b8311171561078d57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506111ae945050505050565b6107d66113db565b604080516001600160a01b039092168252519081900360200190f35b6100d96004803603604081101561080857600080fd5b5080356001600160a01b031690602001356001600160401b03166113ea565b61084d6004803603602081101561083d57600080fd5b50356001600160a01b0316611464565b60408051918252519081900360200190f35b61034f6004803603604081101561087557600080fd5b50803560ff1690602001356001600160a01b031661147f565b6108b4600480360360208110156108a457600080fd5b50356001600160401b031661153f565b6040518080602001806020018461ffff1661ffff168152602001838103835286818151815260200191508051906020019080838360005b838110156109035781810151838201526020016108eb565b50505050905090810190601f1680156109305780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561096357818101518382015260200161094b565b50505050905090810190601f1680156109905780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6001600160a01b038083166000908152600260209081526040808320858452909152808220600480548351639201de5560e01b81529182018790529251606095859485948594899485948594859493911691639201de55916024808201928b92909190829003018186803b158015610a1857600080fd5b505afa158015610a2c573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610a5557600080fd5b8101908080516040519392919084600160201b821115610a7457600080fd5b908301906020820185811115610a8957600080fd5b8251600160201b811182820188101715610aa257600080fd5b82525081516020918201929091019080838360005b83811015610acf578181015183820152602001610ab7565b50505050905090810190601f168015610afc5780820380516001836020036101000a031916815260200191505b5085546001808801546002808a015460038b015460048c01805460206101009782161597909702600019011693909304601f81018690048602880186016040528088526001600160401b039687169a50939850909650939093169360058a019260068b019260078c019291869190830182828015610bbb5780601f10610b9057610100808354040283529160200191610bbb565b820191906000526020600020905b815481529060010190602001808311610b9e57829003601f168201915b5050865460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295995088945092508401905082828015610c495780601f10610c1e57610100808354040283529160200191610c49565b820191906000526020600020905b815481529060010190602001808311610c2c57829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295985087945092508401905082828015610cd75780601f10610cac57610100808354040283529160200191610cd7565b820191906000526020600020905b815481529060010190602001808311610cba57829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295975086945092508401905082828015610d655780601f10610d3a57610100808354040283529160200191610d65565b820191906000526020600020905b815481529060010190602001808311610d4857829003601f168201915b50505050509050995099509950995099509950995099509950509295985092959850929598565b6000546001600160a01b03163314610de4576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600480546040516319f6a32560e31b8152602092810183815284516024830152845160009485946001600160a01b03169363cfb519289388939092839260440191908501908083838b5b83811015610e68578181015183820152602001610e50565b50505050905090810190601f168015610e955780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610eb257600080fd5b505afa158015610ec6573d6000803e3d6000fd5b505050506040513d6020811015610edc57600080fd5b5051905060005b6000546001600160401b03600160a01b90910481169082161015610f41576003816001600160401b031681548110610f1757fe5b906000526020600020906003020160000154821415610f39579150610f499050565b600101610ee3565b506000199150505b919050565b600054600160a01b90046001600160401b031681565b6040805160608101918290526004546319f6a32560e31b9092526020606482018181528651608484015286516000946003949384936001600160a01b039092169263cfb51928928b929091829160a4880191908501908083838e5b83811015610fd7578181015183820152602001610fbf565b50505050905090810190601f1680156110045780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561102157600080fd5b505afa158015611035573d6000803e3d6000fd5b505050506040513d602081101561104b57600080fd5b50518152600480546040516319f6a32560e31b81526020928101838152895160248301528951948401946001600160a01b039093169363cfb51928938b9383926044909101919085019080838360005b838110156110b357818101518382015260200161109b565b50505050905090810190601f1680156110e05780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156110fd57600080fd5b505afa158015611111573d6000803e3d6000fd5b505050506040513d602081101561112757600080fd5b5051815261ffff9485166020918201528254600180820185556000948552828520845160039093020191825591830151818301556040909201516002909201805461ffff19169290951691909117909355805467ffffffffffffffff60a01b198116600160a01b918290046001600160401b03908116958601169091021790555092915050565b60055460408051634209fff160e01b81526001600160a01b038d8116600483015291519190921691634209fff1916024808301926020929190829003018186803b1580156111fb57600080fd5b505afa15801561120f573d6000803e3d6000fd5b505050506040513d602081101561122557600080fd5b505161122d57fe5b6040518060a00160405280896001600160401b03168152602001888152602001878152602001866001600160401b03168152602001604051806080016040528087815260200186815260200185815260200184815250815250600260008c6001600160a01b03166001600160a01b0316815260200190815260200160002060008b815260200190815260200160002060008201518160000160006101000a8154816001600160401b0302191690836001600160401b03160217905550602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160401b0302191690836001600160401b03160217905550608082015181600401600082015181600001908051906020019061134f929190611812565b5060208281015180516113689260018501920190611812565b5060408201518051611384916002840191602090910190611812565b50606082015180516113a0916003840191602090910190611812565b5050506001600160a01b03909b1660009081526001602081815260408320805492830181558352909120019990995550505050505050505050565b6000546001600160a01b031681565b60606000806000806060806060806114458b600160008e6001600160a01b03166001600160a01b031681526020019081526020016000208c6001600160401b03168154811061143557fe5b90600052602060002001546109a1565b9850985098509850985098509850985098509295985092959850929598565b6001600160a01b031660009081526001602052604090205490565b6000546001600160a01b031633146114d7576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b60058260058111156114e557fe5b141561150b57600480546001600160a01b0319166001600160a01b03831617905561153b565b600482600581111561151957fe5b141561153b57600580546001600160a01b0319166001600160a01b0383161790555b5050565b6000805460609182916001600160401b03600160a01b90910481169085161061156457fe5b60006003856001600160401b03168154811061157c57fe5b600091825260208220600480546003909302909101805460408051639201de5560e01b815293840191909152519094506001600160a01b0390921692639201de5592602480840193829003018186803b1580156115d857600080fd5b505afa1580156115ec573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561161557600080fd5b8101908080516040519392919084600160201b82111561163457600080fd5b90830190602082018581111561164957600080fd5b8251600160201b81118282018810171561166257600080fd5b82525081516020918201929091019080838360005b8381101561168f578181015183820152602001611677565b50505050905090810190601f1680156116bc5780820380516001836020036101000a031916815260200191505b506040818152600480546001890154639201de5560e01b85529184019190915290516001600160a01b039091169450639201de55935060248083019350600092829003018186803b15801561171057600080fd5b505afa158015611724573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561174d57600080fd5b8101908080516040519392919084600160201b82111561176c57600080fd5b90830190602082018581111561178157600080fd5b8251600160201b81118282018810171561179a57600080fd5b82525081516020918201929091019080838360005b838110156117c75781810151838201526020016117af565b50505050905090810190601f1680156117f45780820380516001836020036101000a031916815260200191505b5060405250505060029290920154909691955061ffff169350915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061185357805160ff1916838001178555611880565b82800160010185558215611880579182015b82811115611880578251825591602001919060010190611865565b5061188c929150611890565b5090565b6118aa91905b8082111561188c5760008155600101611896565b9056fea265627a7a72315820f71d03b01646e3d7e3a314eb0612af6fd1a8e1da89c96b480243681e0b675af764736f6c634300050b0032";

    public static final String FUNC_GETRECORDING = "getRecording";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETSERVERID = "getServerID";

    public static final String FUNC_NUMSERVERS = "numServers";

    public static final String FUNC_ADDSERVER = "addServer";

    public static final String FUNC_ADDRECORDING = "addRecording";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETFILEINDEX = "getFileIndex";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_GETSERVER = "getServer";

    @Deprecated
    protected SmartDCPABEFiles(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABEFiles(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABEFiles(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABEFiles(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>> getRecording(String addr, byte[] name) {
        final Function function = new Function(FUNC_GETRECORDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.generated.Bytes32(name)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteCall<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>>(
                new Callable<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>>() {
                    @Override
                    public Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (byte[]) results.get(5).getValue(), 
                                (byte[]) results.get(6).getValue(), 
                                (byte[]) results.get(7).getValue(), 
                                (byte[]) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getServerID(String domain) {
        final Function function = new Function(FUNC_GETSERVERID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(domain)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> numServers() {
        final Function function = new Function(FUNC_NUMSERVERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addServer(String domain, String path, BigInteger port) {
        final Function function = new Function(
                FUNC_ADDSERVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(domain), 
                new org.web3j.abi.datatypes.Utf8String(path), 
                new org.web3j.abi.datatypes.generated.Uint16(port)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addRecording(String addr, byte[] filename, BigInteger serverID, byte[] key, byte[] hashing, BigInteger timestamp, byte[] c0, byte[] c1, byte[] c2, byte[] c3) {
        final Function function = new Function(
                FUNC_ADDRECORDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.generated.Bytes32(filename), 
                new org.web3j.abi.datatypes.generated.Uint64(serverID), 
                new org.web3j.abi.datatypes.generated.Bytes32(key), 
                new org.web3j.abi.datatypes.generated.Bytes32(hashing), 
                new org.web3j.abi.datatypes.generated.Uint64(timestamp), 
                new org.web3j.abi.datatypes.DynamicBytes(c0), 
                new org.web3j.abi.datatypes.DynamicBytes(c1), 
                new org.web3j.abi.datatypes.DynamicBytes(c2), 
                new org.web3j.abi.datatypes.DynamicBytes(c3)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>> getRecording(String addr, BigInteger index) {
        final Function function = new Function(FUNC_GETRECORDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteCall<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>>(
                new Callable<Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>>() {
                    @Override
                    public Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, BigInteger, byte[], byte[], BigInteger, byte[], byte[], byte[], byte[]>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (byte[]) results.get(5).getValue(), 
                                (byte[]) results.get(6).getValue(), 
                                (byte[]) results.get(7).getValue(), 
                                (byte[]) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getFileIndex(String addr) {
        final Function function = new Function(FUNC_GETFILEINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setContractDependencies(BigInteger contractType, String addr) {
        final Function function = new Function(
                FUNC_SETCONTRACTDEPENDENCIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(contractType), 
                new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> getServer(BigInteger index) {
        final Function function = new Function(FUNC_GETSERVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint16>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static SmartDCPABEFiles load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEFiles(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABEFiles load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABEFiles(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABEFiles load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEFiles(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABEFiles load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABEFiles(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABEFiles> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEFiles.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEFiles> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEFiles.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEFiles> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEFiles.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEFiles> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABEFiles.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
