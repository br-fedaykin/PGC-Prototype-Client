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
    private static final String BINARY = "608060405234801561001057600080fd5b50604051611a76380380611a768339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055611a11806100656000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c806391eca0c111610097578063e7a516b111610066578063e7a516b11461063e578063e9f67ece1461067e578063f0255a89146106ac578063f4d67309146106f557610100565b806391eca0c1146104f157806398fe2b661461050e578063b78ee7c414610534578063c8c76f061461060f57610100565b80632af4c31e116100d35780632af4c31e146102ee57806337a92dc2146103145780634a2eae6c1461044d5780638aa33ff3146104b157610100565b80630595a466146101055780631c2353e1146101565780631d7067771461019057806327512281146102c6575b600080fd5b61013a6004803603604081101561011b57600080fd5b5080356001600160a01b031690602001356001600160401b03166107bf565b604080516001600160a01b039092168252519081900360200190f35b61017c6004803603602081101561016c57600080fd5b50356001600160a01b0316610808565b604080519115158252519081900360200190f35b6101b6600480360360208110156101a657600080fd5b50356001600160a01b0316610828565b60405180856001600160a01b03166001600160a01b031681526020018060200180602001846001600160401b03166001600160401b03168152602001838103835286818151815260200191508051906020019080838360005b8381101561022757818101518382015260200161020f565b50505050905090810190601f1680156102545780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561028757818101518382015260200161026f565b50505050905090810190601f1680156102b45780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102ec600480360360208110156102dc57600080fd5b50356001600160a01b0316610ad6565b005b6102ec6004803603602081101561030457600080fd5b50356001600160a01b0316610b6b565b6102ec6004803603606081101561032a57600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561035457600080fd5b82018360208201111561036657600080fd5b803590602001918460018302840111600160201b8311171561038757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156103d957600080fd5b8201836020820111156103eb57600080fd5b803590602001918460018302840111600160201b8311171561040c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610be5945050505050565b61048d6004803603606081101561046357600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610e66565b6040518082600281111561049d57fe5b60ff16815260200191505060405180910390f35b6104df600480360360408110156104c757600080fd5b506001600160a01b0381358116916020013516610ebe565b60408051918252519081900360200190f35b61013a6004803603602081101561050757600080fd5b5035610ee9565b6104df6004803603602081101561052457600080fd5b50356001600160a01b0316610f10565b6105746004803603606081101561054a57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610f2b565b6040518085600281111561058457fe5b60ff168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b838110156105f85781810151838201526020016105e0565b505050509050019550505050505060405180910390f35b6102ec6004803603604081101561062557600080fd5b50803560ff1690602001356001600160a01b0316611062565b6105746004803603606081101561065457600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316611147565b6104df6004803603604081101561069457600080fd5b506001600160a01b03813581169160200135166111d1565b6102ec600480360360808110156106c257600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff166111fc565b6102ec6004803603608081101561070b57600080fd5b6001600160a01b0382358116926020810135909116916001600160401b036040830135169190810190608081016060820135600160201b81111561074e57600080fd5b82018360208201111561076057600080fd5b803590602001918460208302840111600160201b8311171561078157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550611670945050505050565b6001600160a01b038216600090815260036020526040812080546001600160401b0384169081106107ec57fe5b6000918252602090912001546001600160a01b03169392505050565b6001600160a01b0316600090815260026020526040902060010154151590565b6001600160a01b03808216600090815260026020526040808220805460065460018301548451639201de5560e01b8152600481019190915293519495606095869588959483169390921691639201de559160248083019288929190829003018186803b15801561089757600080fd5b505afa1580156108ab573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405260208110156108d457600080fd5b8101908080516040519392919084600160201b8211156108f357600080fd5b90830190602082018581111561090857600080fd5b8251600160201b81118282018810171561092157600080fd5b82525081516020918201929091019080838360005b8381101561094e578181015183820152602001610936565b50505050905090810190601f16801561097b5780820380516001836020036101000a031916815260200191505b5060408181526006546002890154639201de5560e01b8452600484015290516001600160a01b039091169450639201de55935060248083019350600092829003018186803b1580156109cc57600080fd5b505afa1580156109e0573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526020811015610a0957600080fd5b8101908080516040519392919084600160201b821115610a2857600080fd5b908301906020820185811115610a3d57600080fd5b8251600160201b811182820188101715610a5657600080fd5b82525081516020918201929091019080838360005b83811015610a83578181015183820152602001610a6b565b50505050905090810190601f168015610ab05780820380516001836020036101000a031916815260200191505b506040525050506003939093015491989097509195506001600160401b03169350915050565b6008546001600160a01b03163314610b2e576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b6001600160a01b0316600090815260026020526040902060030180546001600160401b038082166001011667ffffffffffffffff19909116179055565b6000546001600160a01b03163314610bc3576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b60018054808201825560009182527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038681169182179092556040805160808101825291825260065490516319f6a32560e31b8152602060048201818152885160248401528851949682880196949094169463cfb51928948a949293849360440192908601918190849084905b83811015610c9e578181015183820152602001610c86565b50505050905090810190601f168015610ccb5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610ce857600080fd5b505afa158015610cfc573d6000803e3d6000fd5b505050506040513d6020811015610d1257600080fd5b505181526006546040516319f6a32560e31b8152602060048201818152865160248401528651948201946001600160a01b039094169363cfb5192893889383926044909201919085019080838360005b83811015610d7a578181015183820152602001610d62565b50505050905090810190601f168015610da75780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610dc457600080fd5b505afa158015610dd8573d6000803e3d6000fd5b505050506040513d6020811015610dee57600080fd5b50518152600060209182018190526001600160a01b0395861681526002808352604091829020845181546001600160a01b031916981697909717875591830151600187015582015190850155606001516003909301805467ffffffffffffffff19166001600160401b03909416939093179092555050565b6001600160a01b038084166000908152600560209081526040808320938616835292905290812080546001600160401b038416908110610ea257fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b03918216600090815260046020908152604080832093909416825291909152205490565b60018181548110610ef657fe5b6000918252602090912001546001600160a01b0316905081565b6001600160a01b031660009081526003602052604090205490565b60008060006060610f3a611884565b6001600160a01b038089166000908152600560209081526040808320938b1683529290522080546001600160401b038816908110610f7457fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff166002811115610fab57fe5b6002811115610fb657fe5b815281546001600160401b0361010082048116602080850191909152600160481b90920416604080840191909152600184018054825181850281018501909352808352606090940193919290919083018282801561103357602002820191906000526020600020905b81548152602001906001019080831161101f575b505050919092525050815160208301516040840151606090940151919c909b5092995097509095505050505050565b6000546001600160a01b031633146110ab5760405162461bcd60e51b81526004018080602001828103825260458152602001806119716045913960600191505060405180910390fd5b60048260048111156110b957fe5b14156110df57600680546001600160a01b0319166001600160a01b038316179055611143565b60038260048111156110ed57fe5b141561111357600780546001600160a01b0319166001600160a01b038316179055611143565b600282600481111561112157fe5b141561114357600880546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03808416600090815260046020908152604080832093861683529290529081208054829182916060916111c091899189916001600160401b038a1690811061119257fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b0316610f2b565b935093509350935093509350935093565b6001600160a01b03918216600090815260056020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260036020526040812080546001600160401b03861690811061122957fe5b60009182526020808320909101546001600160a01b038881168452600483526040808520919092168085529252909120549091506001111561129c5760405162461bcd60e51b81526004018080602001828103825260278152602001806119b66027913960400191505060405180910390fd5b6001600160a01b038086166000908152600460209081526040808320938516835292905290812080546001600160401b0386169081106112d857fe5b600091825260208083206004830401546001600160a01b038a81168552600583526040808620918816865292529220805460039092166008026101000a9092046001600160401b031692508491908390811061133057fe5b906000526020600020906002020160000160006101000a81548160ff0219169083600281111561135c57fe5b02179055506001600160a01b03808716600090815260046020908152604080832093861683529290522054600114156114e2576001600160a01b0380871660009081526004602090815260408083209386168352929052208054806113bd57fe5b600082815260208082206004600019948501908104909101805460038084166008026101000a6001600160401b03021990911690915594556001600160a01b038a168252929092526040822080549091810190811061141857fe5b60009182526020808320909101546001600160a01b038a81168452600390925260409092208054919092169250906114549060001983016118ab565b506001600160a01b0387166000908152600360205260409020546001600160401b038616146114dc576001600160a01b038716600090815260036020526040902080548291906001600160401b0388169081106114ad57fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055505b50611668565b6001600160a01b03868116600090815260046020908152604080832093861683529290529081208054600019810190811061151957fe5b600091825260208083206004808404909101546001600160a01b038c811686529183526040808620928916865291909252909220805460039092166008026101000a9092046001600160401b03169250906115789060001983016118d4565b506001600160a01b038781166000908152600460209081526040808320938716835292905220805460001981019081106115ae57fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b03166001600160401b0316856001600160401b031614611666576001600160a01b03808816600090815260046020908152604080832093871683529290522080548291906001600160401b03881690811061162c57fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055505b505b505050505050565b60075460408051634209fff160e01b81526001600160a01b03868116600483015291519190921691634209fff1916024808301926020929190829003018186803b1580156116bd57600080fd5b505afa1580156116d1573d6000803e3d6000fd5b505050506040513d60208110156116e757600080fd5b50516116ef57fe5b6116f884610808565b6116fe57fe5b6001600160a01b0384811660009081526005602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff19169190849081111561178557fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff00199092169190911716178255606083015180516117f09260018501920190611908565b5050506001600160a01b03958616600081815260046020818152604080842099909a1680845298815289832080546001818101835591855282852093810490930180546001600160401b0398891660086003968716026101000a908102990219169790971790965592825282529687208054938401815587529095200180546001600160a01b031916909317909255505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b8154818355818111156118cf576000838152602090206118cf918101908301611953565b505050565b8154818355818111156118cf5760030160049004816003016004900483600052602060002091820191016118cf9190611953565b828054828255906000526020600020908101928215611943579160200282015b82811115611943578251825591602001919060010190611928565b5061194f929150611953565b5090565b61196d91905b8082111561194f5760008155600101611959565b9056fe4f7065726174696f6e206e6f7420616c6c6f7765642e204d7573742062652074686520646f6e6520627920746865206f776e6572206f662074686520636f6e74726163742e4e6f2070656e64696e6720726571756573747320666f72207468697320617574686f726974792ea265627a7a723158208084e3d2f546e2b5a806d69d14ded17eabaa3b4e017720e3a638e6e00e5a499064736f6c634300050b0032";

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

    public static final String FUNC_GETREQUESTLISTSIZE = "getRequestListSize";

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

    public RemoteCall<BigInteger> getRequestListSize(String authority, String requester) {
        final Function function = new Function(FUNC_GETREQUESTLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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
