package com.brunoarruda.hyperdcpabe.blockchain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class SmartDCPABERequests extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161129c38038061129c8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055611237806100656000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c806398fe2b661161008c578063e7a516b111610066578063e7a516b1146103a7578063e9f67ece146103e7578063f0255a8914610415578063f4d673091461045e576100cf565b806398fe2b6614610277578063b78ee7c41461029d578063c8c76f0614610378576100cf565b80630595a466146100d45780632af4c31e146101255780634a2eae6c1461014d57806372ba6003146101b15780638aa33ff31461022f5780638da5cb5b1461026f575b600080fd5b610109600480360360408110156100ea57600080fd5b5080356001600160a01b031690602001356001600160401b031661052a565b604080516001600160a01b039092168252519081900360200190f35b61014b6004803603602081101561013b57600080fd5b50356001600160a01b0316610573565b005b61018d6004803603606081101561016357600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b03166105ed565b6040518082600281111561019d57fe5b60ff16815260200191505060405180910390f35b6101df600480360360408110156101c757600080fd5b506001600160a01b0381358116916020013516610645565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561021b578181015183820152602001610203565b505050509050019250505060405180910390f35b61025d6004803603604081101561024557600080fd5b506001600160a01b03813581169160200135166106f0565b60408051918252519081900360200190f35b61010961071b565b61025d6004803603602081101561028d57600080fd5b50356001600160a01b031661072a565b6102dd600480360360608110156102b357600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610745565b604051808560028111156102ed57fe5b60ff168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b83811015610361578181015183820152602001610349565b505050509050019550505050505060405180910390f35b61014b6004803603604081101561038e57600080fd5b50803560ff1690602001356001600160a01b031661087c565b6102dd600480360360608110156103bd57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b031661093c565b61025d600480360360408110156103fd57600080fd5b506001600160a01b03813581169160200135166109c6565b61014b6004803603608081101561042b57600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff166109f1565b61014b6004803603608081101561047457600080fd5b6001600160a01b0382358116926020810135909116916001600160401b0360408301351691908101906080810160608201356401000000008111156104b857600080fd5b8201836020820111156104ca57600080fd5b803590602001918460208302840111640100000000831117156104ec57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550610e60945050505050565b6001600160a01b038216600090815260016020526040812080546001600160401b03841690811061055757fe5b6000918252602090912001546001600160a01b03169392505050565b6000546001600160a01b031633146105cb576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b038084166000908152600360209081526040808320938616835292905290812080546001600160401b03841690811061062957fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b0380831660009081526002602090815260408083209385168352928152908290208054835181840281018401909452808452606093928301828280156106e357602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116106a05790505b5050505050905092915050565b6001600160a01b03918216600090815260026020908152604080832093909416825291909152205490565b6000546001600160a01b031681565b6001600160a01b031660009081526001602052604090205490565b600080600060606107546110ef565b6001600160a01b038089166000908152600360209081526040808320938b1683529290522080546001600160401b03881690811061078e57fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff1660028111156107c557fe5b60028111156107d057fe5b815281546001600160401b0361010082048116602080850191909152600160481b90920416604080840191909152600184018054825181850281018501909352808352606090940193919290919083018282801561084d57602002820191906000526020600020905b815481526020019060010190808311610839575b505050919092525050815160208301516040840151606090940151919c909b5092995097509095505050505050565b6000546001600160a01b031633146108d4576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b60008260058111156108e257fe5b141561090857600580546001600160a01b0319166001600160a01b038316179055610938565b600482600581111561091657fe5b141561093857600480546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03808416600090815260026020908152604080832093861683529290529081208054829182916060916109b591899189916001600160401b038a1690811061098757fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b0316610745565b935093509350935093509350935093565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260016020526040812080546001600160401b038616908110610a1e57fe5b60009182526020808320909101546001600160a01b0388811684526002835260408085209190921680855292529091205490915060011115610a915760405162461bcd60e51b81526004018080602001828103825260278152602001806111dc6027913960400191505060405180910390fd5b6001600160a01b038086166000908152600260209081526040808320938516835292905290812080546001600160401b038616908110610acd57fe5b600091825260208083206004830401546001600160a01b038a81168552600380845260408087209289168752919093529093208054919092166008026101000a9092046001600160401b03169250849183908110610b2757fe5b906000526020600020906002020160000160006101000a81548160ff02191690836002811115610b5357fe5b02179055506001600160a01b0380871660009081526002602090815260408083209386168352929052205460011415610cd7576001600160a01b038087166000908152600260209081526040808320938616835292905220805480610bb457fe5b60008281526020808220600460001994850190810490910180546001600160401b03600860038516026101000a02191690559093556001600160a01b0389168152600190925260408220805490918101908110610c0d57fe5b60009182526020808320909101546001600160a01b038a8116845260019092526040909220805491909216925090610c49906000198301611116565b506001600160a01b0387166000908152600160205260409020546001600160401b03861614610cd1576001600160a01b038716600090815260016020526040902080548291906001600160401b038816908110610ca257fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055505b50610e58565b6001600160a01b038681166000908152600260209081526040808320938616835292905290812080546000198101908110610d0e57fe5b600091825260208083206004830401546001600160a01b038b81168552600283526040808620918916865292529220805460039092166008026101000a9092046001600160401b0316925090610d6890600019830161113f565b506001600160a01b03878116600090815260026020908152604080832093871683529290522080546000198101908110610d9e57fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b03166001600160401b0316856001600160401b031614610e56576001600160a01b03808816600090815260026020908152604080832093871683529290522080548291906001600160401b038816908110610e1c57fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055505b505b505050505050565b6004805460408051634209fff160e01b81526001600160a01b038781169482019490945290519290911691634209fff191602480820192602092909190829003018186803b158015610eb157600080fd5b505afa158015610ec5573d6000803e3d6000fd5b505050506040513d6020811015610edb57600080fd5b5051610ee357fe5b60055460408051631c2353e160e01b81526001600160a01b03878116600483015291519190921691631c2353e1916024808301926020929190829003018186803b158015610f3057600080fd5b505afa158015610f44573d6000803e3d6000fd5b505050506040513d6020811015610f5a57600080fd5b5051610f6257fe5b6001600160a01b0384811660009081526003602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff191691908490811115610fe957fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff00199092169190911716178255606083015180516110549260018501920190611173565b5050506001600160a01b03958616600081815260026020908152604080832098909916808352978152888220805460018181018355918452828420600482040180546001600160401b0398891660086003909416939093026101000a9283029890920219909116969096179095559181528382529687208054938401815587529095200180546001600160a01b031916909317909255505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b81548183558181111561113a5760008381526020902061113a9181019083016111be565b505050565b81548183558181111561113a57600301600490048160030160049004836000526020600020918201910161113a91906111be565b8280548282559060005260206000209081019282156111ae579160200282015b828111156111ae578251825591602001919060010190611193565b506111ba9291506111be565b5090565b6111d891905b808211156111ba57600081556001016111c4565b9056fe4e6f2070656e64696e6720726571756573747320666f722074686973206365727469666965722ea265627a7a723158204af8a446451e8d713eed9ea3870ffbda5299cd34f4c2ea958f73b498ebe7f3a664736f6c634300050b0032";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_GETPENDINGLIST = "getPendingList";

    public static final String FUNC_GETPENDINGLISTSIZE = "getPendingListSize";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETPENDINGREQUESTERLISTSIZE = "getPendingRequesterListSize";

    public static final String FUNC_GETREQUEST = "getRequest";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_GETPENDINGREQUEST = "getPendingRequest";

    public static final String FUNC_GETREQUESTLISTSIZE = "getRequestListSize";

    public static final String FUNC_PROCESSREQUEST = "processRequest";

    public static final String FUNC_ADDREQUEST = "addRequest";

    @Deprecated
    protected SmartDCPABERequests(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartDCPABERequests(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartDCPABERequests(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartDCPABERequests(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> getPendingRequesterAddress(String certifier, BigInteger requesterIndex) {
        final Function function = new Function(FUNC_GETPENDINGREQUESTERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getRequestStatus(String certifier, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUESTSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List> getPendingList(String certifier, String requester) {
        final Function function = new Function(FUNC_GETPENDINGLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint64>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> getPendingListSize(String certifier, String requester) {
        final Function function = new Function(FUNC_GETPENDINGLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getPendingRequesterListSize(String certifier) {
        final Function function = new Function(FUNC_GETPENDINGREQUESTERLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>> getRequest(String certifier, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
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

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, List<byte[]>>> getPendingRequest(String certifier, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETPENDINGREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
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

    public RemoteCall<BigInteger> getRequestListSize(String certifier, String requester) {
        final Function function = new Function(FUNC_GETREQUESTLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> processRequest(String certifier, BigInteger requesterIndex, BigInteger pendingIndex, BigInteger newStatus) {
        final Function function = new Function(
                FUNC_PROCESSREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex), 
                new org.web3j.abi.datatypes.generated.Uint64(pendingIndex), 
                new org.web3j.abi.datatypes.generated.Uint8(newStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addRequest(String certifier, String requester, BigInteger timestamp, List<byte[]> attrNames) {
        final Function function = new Function(
                FUNC_ADDREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(timestamp), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(attrNames, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SmartDCPABERequests load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABERequests(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartDCPABERequests load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartDCPABERequests(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartDCPABERequests load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartDCPABERequests(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartDCPABERequests load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartDCPABERequests(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
