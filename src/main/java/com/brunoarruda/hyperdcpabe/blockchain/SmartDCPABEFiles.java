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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161181e38038061181e8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556117b9806100656000396000f3fe608060405234801561001057600080fd5b506004361061009d5760003560e01c8063445c7d8611610066578063445c7d8614610556578063905da440146107c35780639b04d0d5146107f8578063c8c76f0614610830578063f08d61121461085f5761009d565b8062a12d78146100a25780632af4c31e1461031e5780632ef01983146103465780633a76f5ad146104035780634190a94214610427575b600080fd5b6100ce600480360360408110156100b857600080fd5b506001600160a01b038135169060200135610972565b60405180806020018a6001600160401b03166001600160401b03168152602001898152602001888152602001876001600160401b03166001600160401b031681526020018060200180602001806020018060200186810386528f818151815260200191508051906020019080838360005b8381101561015757818101518382015260200161013f565b50505050905090810190601f1680156101845780820380516001836020036101000a031916815260200191505b5086810385528a5181528a516020918201918c019080838360005b838110156101b757818101518382015260200161019f565b50505050905090810190601f1680156101e45780820380516001836020036101000a031916815260200191505b5086810384528951815289516020918201918b019080838360005b838110156102175781810151838201526020016101ff565b50505050905090810190601f1680156102445780820380516001836020036101000a031916815260200191505b5086810383528851815288516020918201918a019080838360005b8381101561027757818101518382015260200161025f565b50505050905090810190601f1680156102a45780820380516001836020036101000a031916815260200191505b50868103825287518152875160209182019189019080838360005b838110156102d75781810151838201526020016102bf565b50505050905090810190601f1680156103045780820380516001836020036101000a031916815260200191505b509e50505050505050505050505050505060405180910390f35b6103446004803603602081101561033457600080fd5b50356001600160a01b0316610d01565b005b6103ea6004803603602081101561035c57600080fd5b810190602081018135600160201b81111561037657600080fd5b82018360208201111561038857600080fd5b803590602001918460018302840111600160201b831117156103a957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610d7b945050505050565b60408051600792830b90920b8252519081900360200190f35b61040b610ec3565b604080516001600160401b039092168252519081900360200190f35b61040b6004803603606081101561043d57600080fd5b810190602081018135600160201b81111561045757600080fd5b82018360208201111561046957600080fd5b803590602001918460018302840111600160201b8311171561048a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104dc57600080fd5b8201836020820111156104ee57600080fd5b803590602001918460018302840111600160201b8311171561050f57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505050903561ffff169150610ed99050565b610344600480360361014081101561056d57600080fd5b6001600160a01b03823516916020810135916001600160401b03604083013581169260608101359260808201359260a083013516919081019060e0810160c0820135600160201b8111156105c057600080fd5b8201836020820111156105d257600080fd5b803590602001918460018302840111600160201b831117156105f357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561064557600080fd5b82018360208201111561065757600080fd5b803590602001918460018302840111600160201b8311171561067857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156106ca57600080fd5b8201836020820111156106dc57600080fd5b803590602001918460018302840111600160201b831117156106fd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561074f57600080fd5b82018360208201111561076157600080fd5b803590602001918460018302840111600160201b8311171561078257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611123945050505050565b6100ce600480360360408110156107d957600080fd5b5080356001600160a01b031690602001356001600160401b0316611350565b61081e6004803603602081101561080e57600080fd5b50356001600160a01b03166113ca565b60408051918252519081900360200190f35b6103446004803603604081101561084657600080fd5b50803560ff1690602001356001600160a01b03166113e5565b6108856004803603602081101561087557600080fd5b50356001600160401b0316611496565b6040518080602001806020018461ffff1661ffff168152602001838103835286818151815260200191508051906020019080838360005b838110156108d45781810151838201526020016108bc565b50505050905090810190601f1680156109015780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561093457818101518382015260200161091c565b50505050905090810190601f1680156109615780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6001600160a01b038083166000908152600260209081526040808320858452909152808220600480548351639201de5560e01b81529182018790529251606095859485948594899485948594859493911691639201de55916024808201928b92909190829003018186803b1580156109e957600080fd5b505afa1580156109fd573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610a2657600080fd5b810190808051600160201b811115610a3d57600080fd5b82016020810184811115610a5057600080fd5b8151600160201b811182820187101715610a6957600080fd5b5050845460018087015460028089015460038a015460048b01805460408051602061010099841615999099026000190190921695909504601f8101889004880282018801909552848152979a506001600160401b0396871699509397509095941693919260058a019260068b019260078c0192869190830182828015610b305780601f10610b0557610100808354040283529160200191610b30565b820191906000526020600020905b815481529060010190602001808311610b1357829003601f168201915b5050865460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295995088945092508401905082828015610bbe5780601f10610b9357610100808354040283529160200191610bbe565b820191906000526020600020905b815481529060010190602001808311610ba157829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295985087945092508401905082828015610c4c5780601f10610c2157610100808354040283529160200191610c4c565b820191906000526020600020905b815481529060010190602001808311610c2f57829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295975086945092508401905082828015610cda5780601f10610caf57610100808354040283529160200191610cda565b820191906000526020600020905b815481529060010190602001808311610cbd57829003601f168201915b50505050509050995099509950995099509950995099509950509295985092959850929598565b6000546001600160a01b03163314610d59576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b600480546040516319f6a32560e31b8152602092810183815284516024830152845160009485946001600160a01b03169363cfb519289388939092839260440191908501908083838b5b83811015610ddd578181015183820152602001610dc5565b50505050905090810190601f168015610e0a5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610e2757600080fd5b505afa158015610e3b573d6000803e3d6000fd5b505050506040513d6020811015610e5157600080fd5b5051905060005b6000546001600160401b03600160a01b90910481169082161015610eb6576003816001600160401b031681548110610e8c57fe5b906000526020600020906003020160000154821415610eae579150610ebe9050565b600101610e58565b506000199150505b919050565b600054600160a01b90046001600160401b031681565b6040805160608101918290526004546319f6a32560e31b9092526020606482018181528651608484015286516000946003949384936001600160a01b039092169263cfb51928928b929091829160a4880191908501908083838e5b83811015610f4c578181015183820152602001610f34565b50505050905090810190601f168015610f795780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610f9657600080fd5b505afa158015610faa573d6000803e3d6000fd5b505050506040513d6020811015610fc057600080fd5b50518152600480546040516319f6a32560e31b81526020928101838152895160248301528951948401946001600160a01b039093169363cfb51928938b9383926044909101919085019080838360005b83811015611028578181015183820152602001611010565b50505050905090810190601f1680156110555780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b15801561107257600080fd5b505afa158015611086573d6000803e3d6000fd5b505050506040513d602081101561109c57600080fd5b5051815261ffff9485166020918201528254600180820185556000948552828520845160039093020191825591830151818301556040909201516002909201805461ffff19169290951691909117909355805467ffffffffffffffff60a01b198116600160a01b918290046001600160401b03908116958601169091021790555092915050565b60055460408051634209fff160e01b81526001600160a01b038d8116600483015291519190921691634209fff1916024808301926020929190829003018186803b15801561117057600080fd5b505afa158015611184573d6000803e3d6000fd5b505050506040513d602081101561119a57600080fd5b50516111a257fe5b6040518060a00160405280896001600160401b03168152602001888152602001878152602001866001600160401b03168152602001604051806080016040528087815260200186815260200185815260200184815250815250600260008c6001600160a01b03166001600160a01b0316815260200190815260200160002060008b815260200190815260200160002060008201518160000160006101000a8154816001600160401b0302191690836001600160401b03160217905550602082015181600101556040820151816002015560608201518160030160006101000a8154816001600160401b0302191690836001600160401b0316021790555060808201518160040160008201518160000190805190602001906112c49291906116a4565b5060208281015180516112dd92600185019201906116a4565b50604082015180516112f99160028401916020909101906116a4565b50606082015180516113159160038401916020909101906116a4565b5050506001600160a01b03909b1660009081526001602081815260408320805492830181558352909120019990995550505050505050505050565b60606000806000806060806060806113ab8b600160008e6001600160a01b03166001600160a01b031681526020019081526020016000208c6001600160401b03168154811061139b57fe5b9060005260206000200154610972565b9850985098509850985098509850985098509295985092959850929598565b6001600160a01b031660009081526001602052604090205490565b6000546001600160a01b0316331461142e5760405162461bcd60e51b81526004018080602001828103825260458152602001806117406045913960600191505060405180910390fd5b600482600481111561143c57fe5b141561146257600480546001600160a01b0319166001600160a01b038316179055611492565b600382600481111561147057fe5b141561149257600580546001600160a01b0319166001600160a01b0383161790555b5050565b6000805460609182916001600160401b03600160a01b9091048116908516106114bb57fe5b60006003856001600160401b0316815481106114d357fe5b600091825260208220600480546003909302909101805460408051639201de5560e01b815293840191909152519094506001600160a01b0390921692639201de5592602480840193829003018186803b15801561152f57600080fd5b505afa158015611543573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561156c57600080fd5b810190808051600160201b81111561158357600080fd5b8201602081018481111561159657600080fd5b8151600160201b8111828201871017156115af57600080fd5b505060048054600187015460408051639201de5560e01b815293840191909152519295506001600160a01b03169350639201de559250602480820192600092909190829003018186803b15801561160557600080fd5b505afa158015611619573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561164257600080fd5b810190808051600160201b81111561165957600080fd5b8201602081018481111561166c57600080fd5b8151600160201b81118282018710171561168557600080fd5b50506002959095015493999498505061ffff9092169550919350505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106116e557805160ff1916838001178555611712565b82800160010185558215611712579182015b828111156117125782518255916020019190600101906116f7565b5061171e929150611722565b5090565b61173c91905b8082111561171e5760008155600101611728565b9056fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742ea265627a7a723058202ac448067b7f3e05a52587467ba8166c18f7666c787a70398885c6dc337d409864736f6c634300050a0032";

    public static final String FUNC_GETRECORDING = "getRecording";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETSERVERID = "getServerID";

    public static final String FUNC_NUMSERVERS = "numServers";

    public static final String FUNC_ADDSERVER = "addServer";

    public static final String FUNC_ADDRECORDING = "addRecording";

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
