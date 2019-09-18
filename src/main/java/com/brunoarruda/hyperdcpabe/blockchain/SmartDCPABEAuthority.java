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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516118853803806118858339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055611820806100656000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c80635fd43d5111610097578063c8c76f0611610066578063c8c76f06146105f3578063e7a516b114610622578063f0255a8914610662578063f4d67309146106ab576100f5565b80635fd43d51146104975780638aa33ff3146104bb57806391eca0c1146104fb578063b78ee7c414610518576100f5565b80632af4c31e116100d35780632af4c31e14610292578063362f09c8146102b857806337a92dc2146102fa5780634a2eae6c14610433576100f5565b80631c2353e1146100fa5780631d70677714610134578063275122811461026a575b600080fd5b6101206004803603602081101561011057600080fd5b50356001600160a01b0316610775565b604080519115158252519081900360200190f35b61015a6004803603602081101561014a57600080fd5b50356001600160a01b0316610795565b60405180856001600160a01b03166001600160a01b031681526020018060200180602001846001600160401b03166001600160401b03168152602001838103835286818151815260200191508051906020019080838360005b838110156101cb5781810151838201526020016101b3565b50505050905090810190601f1680156101f85780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561022b578181015183820152602001610213565b50505050905090810190601f1680156102585780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102906004803603602081101561028057600080fd5b50356001600160a01b0316610982565b005b610290600480360360208110156102a857600080fd5b50356001600160a01b0316610a17565b6102de600480360360208110156102ce57600080fd5b50356001600160401b0316610a91565b604080516001600160a01b039092168252519081900360200190f35b6102906004803603606081101561031057600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561033a57600080fd5b82018360208201111561034c57600080fd5b803590602001918460018302840111600160201b8311171561036d57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156103bf57600080fd5b8201836020820111156103d157600080fd5b803590602001918460018302840111600160201b831117156103f257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610ab5945050505050565b6104736004803603606081101561044957600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610d36565b6040518082600281111561048357fe5b60ff16815260200191505060405180910390f35b61049f610d8e565b604080516001600160401b039092168252519081900360200190f35b6104e9600480360360408110156104d157600080fd5b506001600160a01b0381358116916020013516610d9d565b60408051918252519081900360200190f35b6102de6004803603602081101561051157600080fd5b5035610dc8565b6105586004803603606081101561052e57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610def565b6040518085600281111561056857fe5b60ff168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b838110156105dc5781810151838201526020016105c4565b505050509050019550505050505060405180910390f35b6102906004803603604081101561060957600080fd5b50803560ff1690602001356001600160a01b0316610f26565b6105586004803603606081101561063857600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b031661100b565b6102906004803603608081101561067857600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff16611095565b610290600480360360808110156106c157600080fd5b6001600160a01b0382358116926020810135909116916001600160401b036040830135169190810190608081016060820135600160201b81111561070457600080fd5b82018360208201111561071657600080fd5b803590602001918460208302840111600160201b8311171561073757600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550611414945050505050565b6001600160a01b0316600090815260026020526040902060010154151590565b6001600160a01b03808216600090815260026020526040808220805460075460018301548451639201de5560e01b8152600481019190915293519495606095869588959483169390921691639201de559160248083019288929190829003018186803b15801561080457600080fd5b505afa158015610818573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561084157600080fd5b810190808051600160201b81111561085857600080fd5b8201602081018481111561086b57600080fd5b8151600160201b81118282018710171561088457600080fd5b5050600754600287015460408051639201de5560e01b81526004810192909252519295506001600160a01b039091169350639201de559250602480820192600092909190829003018186803b1580156108dc57600080fd5b505afa1580156108f0573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561091957600080fd5b810190808051600160201b81111561093057600080fd5b8201602081018481111561094357600080fd5b8151600160201b81118282018710171561095c57600080fd5b505060039690960154949b939a50949850506001600160401b0390921695509350505050565b6009546001600160a01b031633146109da576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6001600160a01b0316600090815260026020526040902060030180546001600160401b038082166001011667ffffffffffffffff19909116179055565b6000546001600160a01b03163314610a6f576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160401b03166000908152600460205260409020546001600160a01b031690565b60018054808201825560009182527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038681169182179092556040805160808101825291825260075490516319f6a32560e31b8152602060048201818152885160248401528851949682880196949094169463cfb51928948a949293849360440192908601918190849084905b83811015610b6e578181015183820152602001610b56565b50505050905090810190601f168015610b9b5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610bb857600080fd5b505afa158015610bcc573d6000803e3d6000fd5b505050506040513d6020811015610be257600080fd5b505181526007546040516319f6a32560e31b8152602060048201818152865160248401528651948201946001600160a01b039094169363cfb5192893889383926044909201919085019080838360005b83811015610c4a578181015183820152602001610c32565b50505050905090810190601f168015610c775780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610c9457600080fd5b505afa158015610ca8573d6000803e3d6000fd5b505050506040513d6020811015610cbe57600080fd5b50518152600060209182018190526001600160a01b0395861681526002808352604091829020845181546001600160a01b031916981697909717875591830151600187015582015190850155606001516003909301805467ffffffffffffffff19166001600160401b03909416939093179092555050565b6001600160a01b038084166000908152600660209081526040808320938616835292905290812080546001600160401b038416908110610d7257fe5b600091825260209091206002909102015460ff16949350505050565b6003546001600160401b031681565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205490565b60018181548110610dd557fe5b6000918252602090912001546001600160a01b0316905081565b60008060006060610dfe61163e565b6001600160a01b038089166000908152600660209081526040808320938b1683529290522080546001600160401b038816908110610e3857fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff166002811115610e6f57fe5b6002811115610e7a57fe5b815281546001600160401b0361010082048116602080850191909152600160481b909204166040808401919091526001840180548251818502810185019093528083526060909401939192909190830182828015610ef757602002820191906000526020600020905b815481526020019060010190808311610ee3575b505050919092525050815160208301516040840151606090940151919c909b5092995097509095505050505050565b6000546001600160a01b03163314610f6f5760405162461bcd60e51b81526004018080602001828103825260458152602001806117a76045913960600191505060405180910390fd5b6004826004811115610f7d57fe5b1415610fa357600780546001600160a01b0319166001600160a01b038316179055611007565b6003826004811115610fb157fe5b1415610fd757600880546001600160a01b0319166001600160a01b038316179055611007565b6002826004811115610fe557fe5b141561100757600980546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b038084166000908152600560209081526040808320938616835292905290812080548291829160609161108491899189916001600160401b038a1690811061105657fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b0316610def565b935093509350935093509350935093565b6001600160401b038084166000908152600460209081526040808320546001600160a01b038981168552600584528285209116808552925282208054919390919086169081106110e157fe5b600091825260208083206004830401546001600160a01b038a81168552600683526040808620918816865292529220805460039092166008026101000a9092046001600160401b031692508491908390811061113957fe5b906000526020600020906002020160000160006101000a81548160ff0219169083600281111561116557fe5b02179055506001600160a01b0380871660009081526005602090815260408083209386168352929052205460011415611261576001600160a01b0380871660009081526005602090815260408083209386168352929052208054806111c657fe5b60008281526020902060046000199092019182040180546001600160401b03600860038516026101000a02191690559055845b6003546001600160401b03908116908216101561125b576001600160401b0360018201818116600090815260046020526040808220549390941681529290922080546001600160a01b0319166001600160a01b039092169190911790556111f9565b5061140c565b6001600160a01b0380871660009081526005602090815260408083209386168352928152908290208054835181840281018401909452808452606093928301828280156112ff57602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116112bc5790505b509394508893505050505b6001825103816001600160401b031610156113745781816001016001600160401b03168151811061133757fe5b602002602001015182826001600160401b03168151811061135457fe5b6001600160401b039092166020928302919091019091015260010161130a565b506001600160a01b038088166000908152600560209081526040808320938716835292815291902082516113aa92840190611665565b506001600160a01b0380881660009081526005602090815260408083209387168352929052208054806113d957fe5b60008281526020902060046000199092019182040180546001600160401b03600860038516026101000a02191690559055505b505050505050565b60085460408051634209fff160e01b81526001600160a01b03868116600483015291519190921691634209fff1916024808301926020929190829003018186803b15801561146157600080fd5b505afa158015611475573d6000803e3d6000fd5b505050506040513d602081101561148b57600080fd5b505161149357fe5b61149c84610775565b6114a257fe5b6001600160a01b0384811660009081526006602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff19169190849081111561152957fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff0019909216919091171617825560608301518051611594926001850192019061171d565b5050506001600160a01b03958616600090815260056020908152604080832097909816808352968152878220805460018181018355918452828420600480830490910180546001600160401b0398891660086003958616026101000a908102908a02199091161790558154871685529092529790912080546001600160a01b0319169096179095555083548082169095011667ffffffffffffffff19909416939093179091555050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b8280548282559060005260206000209060030160049004810192821561170d5791602002820160005b838211156116d857835183826101000a8154816001600160401b0302191690836001600160401b03160217905550926020019260080160208160070104928301926001030261168e565b801561170b5782816101000a8154906001600160401b0302191690556008016020816007010492830192600103026116d8565b505b50611719929150611764565b5090565b828054828255906000526020600020908101928215611758579160200282015b8281111561175857825182559160200191906001019061173d565b5061171992915061178c565b61178991905b8082111561171957805467ffffffffffffffff1916815560010161176a565b90565b61178991905b80821115611719576000815560010161179256fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742ea265627a7a72305820c019acf2751e1e045a8654fc8beffed9cd94fa91fc05fb451608c0fa3520b50364736f6c634300050a0032";

    public static final String FUNC_ISCERTIFIER = "isCertifier";

    public static final String FUNC_GETCERTIFIER = "getCertifier";

    public static final String FUNC_INCREMENTPUBLICKEYCOUNT = "incrementPublicKeyCount";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_ADDCERTIFIER = "addCertifier";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_NUMREQUESTOWNERS = "numRequestOwners";

    public static final String FUNC_GETPENDINGLISTSIZE = "getPendingListSize";

    public static final String FUNC_CERTIFIERADDRESSES = "certifierAddresses";

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

    public RemoteCall<String> getPendingRequesterAddress(BigInteger requesterIndex) {
        final Function function = new Function(FUNC_GETPENDINGREQUESTERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(requesterIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<BigInteger> numRequestOwners() {
        final Function function = new Function(FUNC_NUMREQUESTOWNERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
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
