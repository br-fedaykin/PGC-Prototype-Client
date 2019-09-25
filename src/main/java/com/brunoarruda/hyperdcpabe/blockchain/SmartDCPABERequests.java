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
import org.web3j.tuples.generated.Tuple5;
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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161118a38038061118a8339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b0319909216919091179055611125806100656000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806398fe2b661161007157806398fe2b6614610211578063b78ee7c414610249578063c8c76f061461033d578063e9f67ece1461036c578063f0255a891461039a578063f4d67309146103e3576100a9565b80630595a466146100ae5780632af4c31e146100ff5780634a2eae6c1461012757806372ba60031461018b5780638da5cb5b14610209575b600080fd5b6100e3600480360360408110156100c457600080fd5b5080356001600160a01b031690602001356001600160401b03166104cb565b604080516001600160a01b039092168252519081900360200190f35b6101256004803603602081101561011557600080fd5b50356001600160a01b0316610514565b005b6101676004803603606081101561013d57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b031661058e565b6040518082600281111561017757fe5b60ff16815260200191505060405180910390f35b6101b9600480360360408110156101a157600080fd5b506001600160a01b03813581169160200135166105e6565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156101f55781810151838201526020016101dd565b505050509050019250505060405180910390f35b6100e3610691565b6102376004803603602081101561022757600080fd5b50356001600160a01b03166106a0565b60408051918252519081900360200190f35b6102896004803603606081101561025f57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b03166106bb565b6040518086600281111561029957fe5b60ff168152602001856001600160401b03166001600160401b03168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561032557818101518382015260200161030d565b50505050905001965050505050505060405180910390f35b6101256004803603604081101561035357600080fd5b50803560ff1690602001356001600160a01b03166107f5565b6102376004803603604081101561038257600080fd5b506001600160a01b03813581169160200135166108b5565b610125600480360360808110156103b057600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff166108e0565b6104af600480360360808110156103f957600080fd5b6001600160a01b0382358116926020810135909116916001600160401b03604083013516919081019060808101606082013564010000000081111561043d57600080fd5b82018360208201111561044f57600080fd5b8035906020019184602083028401116401000000008311171561047157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550610d4f945050505050565b604080516001600160401b039092168252519081900360200190f35b6001600160a01b038216600090815260016020526040812080546001600160401b0384169081106104f857fe5b6000918252602090912001546001600160a01b03169392505050565b6000546001600160a01b0316331461056c576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b038084166000908152600360209081526040808320938616835292905290812080546001600160401b0384169081106105ca57fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b03808316600090815260026020908152604080832093851683529281529082902080548351818402810184019094528084526060939283018282801561068457602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116106415790505b5050505050905092915050565b6000546001600160a01b031681565b6001600160a01b031660009081526001602052604090205490565b60008060008060606106cb610fdd565b6001600160a01b03808a166000908152600360209081526040808320938c1683529290522080546001600160401b03891690811061070557fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff16600281111561073c57fe5b600281111561074757fe5b815281546001600160401b0361010082048116602080850191909152600160481b9092041660408084019190915260018401805482518185028101850190935280835260609094019391929091908301828280156107c457602002820191906000526020600020905b8154815260200190600101908083116107b0575b505050919092525050815160208301516040840151606090940151919d9a9c509a5091989197509095505050505050565b6000546001600160a01b0316331461084d576040805162461bcd60e51b815260206004820152601660248201527527b832b930ba34b7b7103737ba1030b63637bbb2b21760511b604482015290519081900360640190fd5b600082600581111561085b57fe5b141561088157600580546001600160a01b0319166001600160a01b0383161790556108b1565b600482600581111561088f57fe5b14156108b157600480546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260016020526040812080546001600160401b03861690811061090d57fe5b60009182526020808320909101546001600160a01b03888116845260028352604080852091909216808552925290912054909150600111156109805760405162461bcd60e51b81526004018080602001828103825260278152602001806110ca6027913960400191505060405180910390fd5b6001600160a01b038086166000908152600260209081526040808320938516835292905290812080546001600160401b0386169081106109bc57fe5b600091825260208083206004830401546001600160a01b038a81168552600380845260408087209289168752919093529093208054919092166008026101000a9092046001600160401b03169250849183908110610a1657fe5b906000526020600020906002020160000160006101000a81548160ff02191690836002811115610a4257fe5b02179055506001600160a01b0380871660009081526002602090815260408083209386168352929052205460011415610bc6576001600160a01b038087166000908152600260209081526040808320938616835292905220805480610aa357fe5b60008281526020808220600460001994850190810490910180546001600160401b03600860038516026101000a02191690559093556001600160a01b0389168152600190925260408220805490918101908110610afc57fe5b60009182526020808320909101546001600160a01b038a8116845260019092526040909220805491909216925090610b38906000198301611004565b506001600160a01b0387166000908152600160205260409020546001600160401b03861614610bc0576001600160a01b038716600090815260016020526040902080548291906001600160401b038816908110610b9157fe5b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055505b50610d47565b6001600160a01b038681166000908152600260209081526040808320938616835292905290812080546000198101908110610bfd57fe5b600091825260208083206004830401546001600160a01b038b81168552600283526040808620918916865292529220805460039092166008026101000a9092046001600160401b0316925090610c5790600019830161102d565b506001600160a01b03878116600090815260026020908152604080832093871683529290522080546000198101908110610c8d57fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b03166001600160401b0316856001600160401b031614610d45576001600160a01b03808816600090815260026020908152604080832093871683529290522080548291906001600160401b038816908110610d0b57fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055505b505b505050505050565b6004805460408051634209fff160e01b81526001600160a01b0387811694820194909452905160009390921691634209fff191602480820192602092909190829003018186803b158015610da257600080fd5b505afa158015610db6573d6000803e3d6000fd5b505050506040513d6020811015610dcc57600080fd5b5051610dd457fe5b60055460408051631c2353e160e01b81526001600160a01b03888116600483015291519190921691631c2353e1916024808301926020929190829003018186803b158015610e2157600080fd5b505afa158015610e35573d6000803e3d6000fd5b505050506040513d6020811015610e4b57600080fd5b5051610e5357fe5b6001600160a01b0385811660009081526003602090815260408083209388168352928152828220805484516080810186528481526001600160401b038916818501529485018490526060850187905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff191691908490811115610eda57fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff0019909216919091171617825560608301518051610f459260018501920190611061565b5050506001600160a01b038088166000818152600260209081526040808320948b16808452948252808320805460018181018355918552838520600482040180546001600160401b03808c1660086003909516949094026101000a93840293021916919091179055938352838252822080549384018155825290200180546001600160a01b0319169091179055509050949350505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b815481835581811115611028576000838152602090206110289181019083016110ac565b505050565b81548183558181111561102857600301600490048160030160049004836000526020600020918201910161102891906110ac565b82805482825590600052602060002090810192821561109c579160200282015b8281111561109c578251825591602001919060010190611081565b506110a89291506110ac565b5090565b6110c691905b808211156110a857600081556001016110b2565b9056fe4e6f2070656e64696e6720726571756573747320666f722074686973206365727469666965722ea265627a7a723158203c12c65df610e66c16758809c037b5bf87dc708c4e67996bcfe0c4579d9a86ba64736f6c634300050b0032";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_GETPENDINGLIST = "getPendingList";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETPENDINGREQUESTERLISTSIZE = "getPendingRequesterListSize";

    public static final String FUNC_GETREQUEST = "getRequest";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

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

    public RemoteCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>>> getRequest(String certifier, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier), 
                new org.web3j.abi.datatypes.Address(requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>>>(
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                convertToNative((List<Bytes32>) results.get(4).getValue()));
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
