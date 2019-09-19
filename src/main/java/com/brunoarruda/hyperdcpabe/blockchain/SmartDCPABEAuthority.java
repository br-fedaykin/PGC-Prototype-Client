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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516119513803806119518339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556118ec806100656000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80638aa33ff311610097578063c8c76f0611610066578063c8c76f0614610604578063e7a516b114610633578063f0255a8914610673578063f4d67309146106bc576100f5565b80638aa33ff3146104a657806391eca0c1146104e657806398fe2b6614610503578063b78ee7c414610529576100f5565b806327512281116100d357806327512281146102bb5780632af4c31e146102e357806337a92dc2146103095780634a2eae6c14610442576100f5565b80630595a466146100fa5780631c2353e11461014b5780631d70677714610185575b600080fd5b61012f6004803603604081101561011057600080fd5b5080356001600160a01b031690602001356001600160401b0316610786565b604080516001600160a01b039092168252519081900360200190f35b6101716004803603602081101561016157600080fd5b50356001600160a01b03166107cf565b604080519115158252519081900360200190f35b6101ab6004803603602081101561019b57600080fd5b50356001600160a01b03166107ef565b60405180856001600160a01b03166001600160a01b031681526020018060200180602001846001600160401b03166001600160401b03168152602001838103835286818151815260200191508051906020019080838360005b8381101561021c578181015183820152602001610204565b50505050905090810190601f1680156102495780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561027c578181015183820152602001610264565b50505050905090810190601f1680156102a95780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102e1600480360360208110156102d157600080fd5b50356001600160a01b03166109dc565b005b6102e1600480360360208110156102f957600080fd5b50356001600160a01b0316610a71565b6102e16004803603606081101561031f57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561034957600080fd5b82018360208201111561035b57600080fd5b803590602001918460018302840111600160201b8311171561037c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156103ce57600080fd5b8201836020820111156103e057600080fd5b803590602001918460018302840111600160201b8311171561040157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610aeb945050505050565b6104826004803603606081101561045857600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610d6c565b6040518082600281111561049257fe5b60ff16815260200191505060405180910390f35b6104d4600480360360408110156104bc57600080fd5b506001600160a01b0381358116916020013516610dc4565b60408051918252519081900360200190f35b61012f600480360360208110156104fc57600080fd5b5035610def565b6104d46004803603602081101561051957600080fd5b50356001600160a01b0316610e16565b6105696004803603606081101561053f57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610e31565b6040518085600281111561057957fe5b60ff168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b838110156105ed5781810151838201526020016105d5565b505050509050019550505050505060405180910390f35b6102e16004803603604081101561061a57600080fd5b50803560ff1690602001356001600160a01b0316610f68565b6105696004803603606081101561064957600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b031661104d565b6102e16004803603608081101561068957600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff166110d7565b6102e1600480360360808110156106d257600080fd5b6001600160a01b0382358116926020810135909116916001600160401b036040830135169190810190608081016060820135600160201b81111561071557600080fd5b82018360208201111561072757600080fd5b803590602001918460208302840111600160201b8311171561074857600080fd5b91908080602002602001604051908101604052809392919081815260200183836020028082843760009201919091525092955061154b945050505050565b6001600160a01b038216600090815260036020526040812080546001600160401b0384169081106107b357fe5b6000918252602090912001546001600160a01b03169392505050565b6001600160a01b0316600090815260026020526040902060010154151590565b6001600160a01b03808216600090815260026020526040808220805460065460018301548451639201de5560e01b8152600481019190915293519495606095869588959483169390921691639201de559160248083019288929190829003018186803b15801561085e57600080fd5b505afa158015610872573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561089b57600080fd5b810190808051600160201b8111156108b257600080fd5b820160208101848111156108c557600080fd5b8151600160201b8111828201871017156108de57600080fd5b5050600654600287015460408051639201de5560e01b81526004810192909252519295506001600160a01b039091169350639201de559250602480820192600092909190829003018186803b15801561093657600080fd5b505afa15801561094a573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561097357600080fd5b810190808051600160201b81111561098a57600080fd5b8201602081018481111561099d57600080fd5b8151600160201b8111828201871017156109b657600080fd5b505060039690960154949b939a50949850506001600160401b0390921695509350505050565b6008546001600160a01b03163314610a34576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6001600160a01b0316600090815260026020526040902060030180546001600160401b038082166001011667ffffffffffffffff19909116179055565b6000546001600160a01b03163314610ac9576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60018054808201825560009182527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038681169182179092556040805160808101825291825260065490516319f6a32560e31b8152602060048201818152885160248401528851949682880196949094169463cfb51928948a949293849360440192908601918190849084905b83811015610ba4578181015183820152602001610b8c565b50505050905090810190601f168015610bd15780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610bee57600080fd5b505afa158015610c02573d6000803e3d6000fd5b505050506040513d6020811015610c1857600080fd5b505181526006546040516319f6a32560e31b8152602060048201818152865160248401528651948201946001600160a01b039094169363cfb5192893889383926044909201919085019080838360005b83811015610c80578181015183820152602001610c68565b50505050905090810190601f168015610cad5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610cca57600080fd5b505afa158015610cde573d6000803e3d6000fd5b505050506040513d6020811015610cf457600080fd5b50518152600060209182018190526001600160a01b0395861681526002808352604091829020845181546001600160a01b031916981697909717875591830151600187015582015190850155606001516003909301805467ffffffffffffffff19166001600160401b03909416939093179092555050565b6001600160a01b038084166000908152600560209081526040808320938616835292905290812080546001600160401b038416908110610da857fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b03918216600090815260046020908152604080832093909416825291909152205490565b60018181548110610dfc57fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b031660009081526003602052604090205490565b60008060006060610e4061175f565b6001600160a01b038089166000908152600560209081526040808320938b1683529290522080546001600160401b038816908110610e7a57fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff166002811115610eb157fe5b6002811115610ebc57fe5b815281546001600160401b0361010082048116602080850191909152600160481b909204166040808401919091526001840180548251818502810185019093528083526060909401939192909190830182828015610f3957602002820191906000526020600020905b815481526020019060010190808311610f25575b505050919092525050815160208301516040840151606090940151919c909b5092995097509095505050505050565b6000546001600160a01b03163314610fb15760405162461bcd60e51b815260040180806020018281038252604581526020018061184c6045913960600191505060405180910390fd5b6004826004811115610fbf57fe5b1415610fe557600680546001600160a01b0319166001600160a01b038316179055611049565b6003826004811115610ff357fe5b141561101957600780546001600160a01b0319166001600160a01b038316179055611049565b600282600481111561102757fe5b141561104957600880546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03808416600090815260046020908152604080832093861683529290529081208054829182916060916110c691899189916001600160401b038a1690811061109857fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b0316610e31565b935093509350935093509350935093565b6001600160a01b038416600090815260036020526040812080546001600160401b03861690811061110457fe5b60009182526020808320909101546001600160a01b03888116845260048352604080852091909216808552925290912054909150600111156111775760405162461bcd60e51b81526004018080602001828103825260278152602001806118916027913960400191505060405180910390fd5b6001600160a01b038086166000908152600460209081526040808320938516835292905290812080546001600160401b0386169081106111b357fe5b600091825260208083206004830401546001600160a01b038a81168552600583526040808620918816865292529220805460039092166008026101000a9092046001600160401b031692508491908390811061120b57fe5b906000526020600020906002020160000160006101000a81548160ff0219169083600281111561123757fe5b02179055506001600160a01b03808716600090815260046020908152604080832093861683529290522054600114156113bd576001600160a01b03808716600090815260046020908152604080832093861683529290522080548061129857fe5b600082815260208082206004600019948501908104909101805460038084166008026101000a6001600160401b03021990911690915594556001600160a01b038a16825292909252604082208054909181019081106112f357fe5b60009182526020808320909101546001600160a01b038a811684526003909252604090922080549190921692509061132f906000198301611786565b506001600160a01b0387166000908152600360205260409020546001600160401b038616146113b7576001600160a01b038716600090815260036020526040902080548291906001600160401b03881690811061138857fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055505b50611543565b6001600160a01b0386811660009081526004602090815260408083209386168352929052908120805460001981019081106113f457fe5b600091825260208083206004808404909101546001600160a01b038c811686529183526040808620928916865291909252909220805460039092166008026101000a9092046001600160401b03169250906114539060001983016117af565b506001600160a01b0387811660009081526004602090815260408083209387168352929052208054600019810190811061148957fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b03166001600160401b0316856001600160401b031614611541576001600160a01b03808816600090815260046020908152604080832093871683529290522080548291906001600160401b03881690811061150757fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055505b505b505050505050565b60075460408051634209fff160e01b81526001600160a01b03868116600483015291519190921691634209fff1916024808301926020929190829003018186803b15801561159857600080fd5b505afa1580156115ac573d6000803e3d6000fd5b505050506040513d60208110156115c257600080fd5b50516115ca57fe5b6115d3846107cf565b6115d957fe5b6001600160a01b0384811660009081526005602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff19169190849081111561166057fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff00199092169190911716178255606083015180516116cb92600185019201906117e3565b5050506001600160a01b03958616600081815260046020818152604080842099909a1680845298815289832080546001818101835591855282852093810490930180546001600160401b0398891660086003968716026101000a908102990219169790971790965592825282529687208054938401815587529095200180546001600160a01b031916909317909255505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b8154818355818111156117aa576000838152602090206117aa91810190830161182e565b505050565b8154818355818111156117aa5760030160049004816003016004900483600052602060002091820191016117aa919061182e565b82805482825590600052602060002090810192821561181e579160200282015b8281111561181e578251825591602001919060010190611803565b5061182a92915061182e565b5090565b61184891905b8082111561182a5760008155600101611834565b9056fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742e4e6f2070656e64696e6720726571756573747320666f72207468697320617574686f726974792ea265627a7a7230582008c3d24951c5feb4fa706cbf3c3bd128886f5ce7f4c4537d30b1e7ee1cd8ee2464736f6c634300050a0032";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_ISCERTIFIER = "isCertifier";

    public static final String FUNC_GETCERTIFIER = "getCertifier";

    public static final String FUNC_INCREMENTPUBLICKEYCOUNT = "incrementPublicKeyCount";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_ADDCERTIFIER = "addCertifier";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_GETPENDINGLISTSIZE = "getPendingListSize";

    public static final String FUNC_CERTIFIERADDRESSES = "certifierAddresses";

    public static final String FUNC_GETPENDINGREQUESTERLISTSIZE = "getPendingRequesterListSize";

    public static final String FUNC_GETREQUEST = "getRequest";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_GETPENDINGREQUEST = "getPendingRequest";

    public static final String FUNC_PROCESSREQUEST = "processRequest";

    public static final String FUNC_ADDREQUEST = "addRequest";

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

    public RemoteCall<String> getPendingRequesterAddress(String authority, BigInteger requesterIndex) {
        final Function function = new Function(FUNC_GETPENDINGREQUESTERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<BigInteger> getRequestStatus(String authority, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUESTSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getPendingListSize(String authority, String requester) {
        final Function function = new Function(FUNC_GETPENDINGLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> certifierAddresses(BigInteger param0) {
        final Function function = new Function(FUNC_CERTIFIERADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getPendingRequesterListSize(String authority) {
        final Function function = new Function(FUNC_GETPENDINGREQUESTERLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>> getRequest(String authority, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>>(
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                convertToNative((List<Bytes32>) results.get(3).getValue()));
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

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>> getPendingRequest(String authority, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETPENDINGREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>>(
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                convertToNative((List<Bytes32>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> processRequest(String authority, BigInteger requesterIndex, BigInteger pendingIndex, BigInteger newStatus) {
        final Function function = new Function(
                FUNC_PROCESSREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex), 
                new org.web3j.abi.datatypes.generated.Uint64(pendingIndex), 
                new org.web3j.abi.datatypes.generated.Uint8(newStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addRequest(String authority, String requester, BigInteger timestamp, List<byte[]> attrNames) {
        final Function function = new Function(
                FUNC_ADDREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(timestamp), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(attrNames, org.web3j.abi.datatypes.generated.Bytes32.class))), 
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
