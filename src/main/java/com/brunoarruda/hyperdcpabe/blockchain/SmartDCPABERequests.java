package com.brunoarruda.hyperdcpabe.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516111b23803806111b28339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b031990921691909117905561114d806100656000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806398fe2b661161007157806398fe2b6614610211578063b78ee7c414610249578063c8c76f061461033d578063e9f67ece1461036c578063f0255a891461039a578063f4d67309146103e3576100a9565b80630595a466146100ae5780632af4c31e146100ff5780634a2eae6c1461012757806372ba60031461018b5780638da5cb5b14610209575b600080fd5b6100e3600480360360408110156100c457600080fd5b5080356001600160a01b031690602001356001600160401b03166104af565b604080516001600160a01b039092168252519081900360200190f35b6101256004803603602081101561011557600080fd5b50356001600160a01b03166104f8565b005b6101676004803603606081101561013d57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610563565b6040518082600281111561017757fe5b60ff16815260200191505060405180910390f35b6101b9600480360360408110156101a157600080fd5b506001600160a01b03813581169160200135166105bb565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156101f55781810151838201526020016101dd565b505050509050019250505060405180910390f35b6100e3610666565b6102376004803603602081101561022757600080fd5b50356001600160a01b0316610675565b60408051918252519081900360200190f35b6102896004803603606081101561025f57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610690565b6040518086600281111561029957fe5b60ff168152602001856001600160401b03166001600160401b03168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561032557818101518382015260200161030d565b50505050905001965050505050505060405180910390f35b6101256004803603604081101561035357600080fd5b50803560ff1690602001356001600160a01b03166107ca565b6102376004803603604081101561038257600080fd5b506001600160a01b038135811691602001351661087b565b610125600480360360808110156103b057600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff166108a6565b610125600480360360808110156103f957600080fd5b6001600160a01b0382358116926020810135909116916001600160401b03604083013516919081019060808101606082013564010000000081111561043d57600080fd5b82018360208201111561044f57600080fd5b8035906020019184602083028401116401000000008311171561047157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550610d40945050505050565b6001600160a01b038216600090815260016020526040812080546001600160401b0384169081106104dc57fe5b6000918252602090912001546001600160a01b03169392505050565b6000546001600160a01b031633146105415760405162461bcd60e51b81526004018080602001828103825260368152602001806110e36036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b038084166000908152600360209081526040808320938616835292905290812080546001600160401b03841690811061059f57fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b03808316600090815260026020908152604080832093851683529281529082902080548351818402810184019094528084526060939283018282801561065957602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116106165790505b5050505050905092915050565b6000546001600160a01b031681565b6001600160a01b031660009081526001602052604090205490565b60008060008060606106a0610fcf565b6001600160a01b03808a166000908152600360209081526040808320938c1683529290522080546001600160401b0389169081106106da57fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff16600281111561071157fe5b600281111561071c57fe5b815281546001600160401b0361010082048116602080850191909152600160481b90920416604080840191909152600184018054825181850281018501909352808352606090940193919290919083018282801561079957602002820191906000526020600020905b815481526020019060010190808311610785575b505050919092525050815160208301516040840151606090940151919d9a9c509a5091989197509095505050505050565b6000546001600160a01b031633146108135760405162461bcd60e51b81526004018080602001828103825260368152602001806110e36036913960400191505060405180910390fd5b600082600581111561082157fe5b141561084757600580546001600160a01b0319166001600160a01b038316179055610877565b600482600581111561085557fe5b141561087757600480546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260016020526040812080546001600160401b0386169081106108d357fe5b60009182526020808320909101546001600160a01b0388811684526002835260408085209190921680855292529091205490915060016001600160401b03821610156109505760405162461bcd60e51b81526004018080602001828103825260278152602001806110bc6027913960400191505060405180910390fd5b6001600160a01b038087166000908152600260209081526040808320938616835292905290812080546001600160401b03871690811061098c57fe5b600091825260208083206004830401546001600160a01b038b8116855260038084526040808720928a168752919093529093208054919092166008026101000a9092046001600160401b031692508591839081106109e657fe5b906000526020600020906002020160000160006101000a81548160ff02191690836002811115610a1257fe5b0217905550816001600160401b031660011415610ba1576001600160a01b038088166000908152600260209081526040808320938716835292905220805490610a5f906000198301610ff6565b506001600160a01b038716600090815260016020526040812080546000198101908110610a8857fe5b60009182526020808320909101546001600160a01b038b8116845260019092526040909220805491909216925090610ac490600019830161102f565b506001600160a01b0388166000908152600160205260409020546001600160401b03881614610b9b576001600160a01b038816600090815260016020526040902080548291906001600160401b038a16908110610b1d57fe5b600091825260208083209190910180546001600160a01b0319166001600160a01b03948516179055918a168152600182526040908190205481516001600160401b039182168152908a169281019290925280517ffc2b62628f29b7ff88b0a776bc2ff1dca670be937c520f9ee832120866869e2e9281900390910190a15b50610d37565b6001600160a01b038088166000908152600260209081526040808320938716835292905290812080546001600160401b03600019860116908110610be157fe5b600091825260208083206004830401546001600160a01b038c81168552600283526040808620918a16865292529220805460039092166008026101000a9092046001600160401b0316925090610c3b906000198301610ff6565b50600183036001600160401b0316866001600160401b031614610d35576001600160a01b03808916600090815260026020908152604080832093881683529290522080548291906001600160401b038916908110610c9557fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055507f4c2907326878d2db295840cca7243664407575228cb84192bfd02687cb1a3960600184038760405180836001600160401b03166001600160401b03168152602001826001600160401b03166001600160401b031681526020019250505060405180910390a15b505b50505050505050565b6004805460408051634209fff160e01b81526001600160a01b038781169482019490945290519290911691634209fff191602480820192602092909190829003018186803b158015610d9157600080fd5b505afa158015610da5573d6000803e3d6000fd5b505050506040513d6020811015610dbb57600080fd5b5051610dc357fe5b60055460408051631c2353e160e01b81526001600160a01b03878116600483015291519190921691631c2353e1916024808301926020929190829003018186803b158015610e1057600080fd5b505afa158015610e24573d6000803e3d6000fd5b505050506040513d6020811015610e3a57600080fd5b5051610e4257fe5b6001600160a01b0384811660009081526003602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff191691908490811115610ec957fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff0019909216919091171617825560608301518051610f349260018501920190611053565b5050506001600160a01b03958616600081815260026020908152604080832098909916808352978152888220805460018181018355918452828420600482040180546001600160401b0398891660086003909416939093026101000a9283029890920219909116969096179095559181528382529687208054938401815587529095200180546001600160a01b031916909317909255505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b81548183558181111561102a57600301600490048160030160049004836000526020600020918201910161102a919061109e565b505050565b81548183558181111561102a5760008381526020902061102a91810190830161109e565b82805482825590600052602060002090810192821561108e579160200282015b8281111561108e578251825591602001919060010190611073565b5061109a92915061109e565b5090565b6110b891905b8082111561109a57600081556001016110a4565b9056fe4e6f2070656e64696e6720726571756573747320666f722074686973206365727469666965722e4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a265627a7a723058201be9ed5a87f17f931de242fa38c5bf44e2316acf7055ec914f0e0e2d26d52c1b64736f6c634300050a0032";

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

    public static final Event PENDINGREQUESTINDEXCHANGED_EVENT = new Event("pendingRequestIndexChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}));
    ;

    public static final Event PENDINGREQUESTERINDEXCHANGED_EVENT = new Event("pendingRequesterIndexChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}));
    ;

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

    public List<PendingRequestIndexChangedEventResponse> getPendingRequestIndexChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PENDINGREQUESTINDEXCHANGED_EVENT, transactionReceipt);
        ArrayList<PendingRequestIndexChangedEventResponse> responses = new ArrayList<PendingRequestIndexChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PendingRequestIndexChangedEventResponse typedResponse = new PendingRequestIndexChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PendingRequestIndexChangedEventResponse> pendingRequestIndexChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PendingRequestIndexChangedEventResponse>() {
            @Override
            public PendingRequestIndexChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PENDINGREQUESTINDEXCHANGED_EVENT, log);
                PendingRequestIndexChangedEventResponse typedResponse = new PendingRequestIndexChangedEventResponse();
                typedResponse.log = log;
                typedResponse.oldIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PendingRequestIndexChangedEventResponse> pendingRequestIndexChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PENDINGREQUESTINDEXCHANGED_EVENT));
        return pendingRequestIndexChangedEventFlowable(filter);
    }

    public List<PendingRequesterIndexChangedEventResponse> getPendingRequesterIndexChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PENDINGREQUESTERINDEXCHANGED_EVENT, transactionReceipt);
        ArrayList<PendingRequesterIndexChangedEventResponse> responses = new ArrayList<PendingRequesterIndexChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PendingRequesterIndexChangedEventResponse typedResponse = new PendingRequesterIndexChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PendingRequesterIndexChangedEventResponse> pendingRequesterIndexChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PendingRequesterIndexChangedEventResponse>() {
            @Override
            public PendingRequesterIndexChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PENDINGREQUESTERINDEXCHANGED_EVENT, log);
                PendingRequesterIndexChangedEventResponse typedResponse = new PendingRequesterIndexChangedEventResponse();
                typedResponse.log = log;
                typedResponse.oldIndex = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newIndex = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PendingRequesterIndexChangedEventResponse> pendingRequesterIndexChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PENDINGREQUESTERINDEXCHANGED_EVENT));
        return pendingRequesterIndexChangedEventFlowable(filter);
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

    public static class PendingRequestIndexChangedEventResponse {
        public Log log;

        public BigInteger oldIndex;

        public BigInteger newIndex;
    }

    public static class PendingRequesterIndexChangedEventResponse {
        public Log log;

        public BigInteger oldIndex;

        public BigInteger newIndex;
    }
}
