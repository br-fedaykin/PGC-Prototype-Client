package com.brunoarruda.hyperdcpabe.blockchain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
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
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
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
 * <p>Generated with web3j version 4.7.0.
 */

@SuppressWarnings("rawtypes")
public class SmartDCPABERequests extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516112343803806112348339818101604052602081101561003357600080fd5b5051600080546001600160a01b039092166001600160a01b03199092169190911790556111cf806100656000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806398fe2b661161007157806398fe2b661461020e578063b78ee7c414610246578063c8c76f0614610340578063e7366ffd1461036f578063e9f67ece1461043e578063f0255a891461046c576100a9565b80630595a466146100ae5780632af4c31e146100ff5780634a2eae6c1461012757806372ba6003146101885780638da5cb5b14610206575b600080fd5b6100e3600480360360408110156100c457600080fd5b5080356001600160a01b031690602001356001600160401b03166104b5565b604080516001600160a01b039092168252519081900360200190f35b6101256004803603602081101561011557600080fd5b50356001600160a01b03166104fe565b005b6101676004803603606081101561013d57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610569565b6040518082600281111561017757fe5b815260200191505060405180910390f35b6101b66004803603604081101561019e57600080fd5b506001600160a01b03813581169160200135166105c1565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156101f25781810151838201526020016101da565b505050509050019250505060405180910390f35b6100e361066c565b6102346004803603602081101561022457600080fd5b50356001600160a01b031661067b565b60408051918252519081900360200190f35b6102866004803603606081101561025c57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610696565b6040518086600281111561029657fe5b8152602001856001600160401b03168152602001846001600160401b03168152602001836001600160401b0316815260200180602001828103825283818151815260200191508051906020019080838360005b838110156103015781810151838201526020016102e9565b50505050905090810190601f16801561032e5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6101256004803603604081101561035657600080fd5b50803560ff1690602001356001600160a01b031661080a565b6101256004803603608081101561038557600080fd5b6001600160a01b0382358116926020810135909116916001600160401b0360408301351691908101906080810160608201356401000000008111156103c957600080fd5b8201836020820111156103db57600080fd5b803590602001918460018302840111640100000000831117156103fd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506108bb945050505050565b6102346004803603604081101561045457600080fd5b506001600160a01b0381358116916020013516610b56565b6101256004803603608081101561048257600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff16610b81565b6001600160a01b038216600090815260016020526040812080546001600160401b0384169081106104e257fe5b6000918252602090912001546001600160a01b03169392505050565b6000546001600160a01b031633146105475760405162461bcd60e51b81526004018080602001828103825260368152602001806111646036913960400191505060405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b038084166000908152600360209081526040808320938616835292905290812080546001600160401b0384169081106105a557fe5b600091825260209091206002909102015460ff16949350505050565b6001600160a01b03808316600090815260026020908152604080832093851683529281529082902080548351818402810184019094528084526060939283018282801561065f57602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b03168152602001906008019060208260070104928301926001038202915080841161061c5790505b5050505050905092915050565b6000546001600160a01b031681565b6001600160a01b031660009081526001602052604090205490565b60008060008060606106a6611074565b6001600160a01b03808a166000908152600360209081526040808320938c1683529290522080546001600160401b0389169081106106e057fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff16600281111561071757fe5b600281111561072257fe5b815281546101008082046001600160401b03908116602080860191909152600160481b90930416604080850191909152600180860180548351600293821615909502600019011691909104601f810185900485028401850190925281835260609094019391928301828280156107d95780601f106107ae576101008083540402835291602001916107d9565b820191906000526020600020905b8154815290600101906020018083116107bc57829003601f168201915b505050919092525050815160208301516040840151606090940151919d9a9c509a5091989197509095505050505050565b6000546001600160a01b031633146108535760405162461bcd60e51b81526004018080602001828103825260368152602001806111646036913960400191505060405180910390fd5b600082600581111561086157fe5b141561088757600580546001600160a01b0319166001600160a01b0383161790556108b7565b600482600581111561089557fe5b14156108b757600480546001600160a01b0319166001600160a01b0383161790555b5050565b600460009054906101000a90046001600160a01b03166001600160a01b0316634209fff1846040518263ffffffff1660e01b815260040180826001600160a01b0316815260200191505060206040518083038186803b15801561091d57600080fd5b505afa158015610931573d6000803e3d6000fd5b505050506040513d602081101561094757600080fd5b505161094f57fe5b60055460408051631c2353e160e01b81526001600160a01b03878116600483015291519190921691631c2353e1916024808301926020929190829003018186803b15801561099c57600080fd5b505afa1580156109b0573d6000803e3d6000fd5b505050506040513d60208110156109c657600080fd5b50516109ce57fe5b6001600160a01b0384811660009081526003602090815260408083209387168352928152828220805484516080810186528481526001600160401b0388168185015294850184905260608501869052600180820183559184529190922083516002808402909201805493959490939192849260ff191691908490811115610a5157fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff0019909216919091171617825560608301518051610abc926001850192019061109b565b5050506001600160a01b039485166000818152600260209081526040808320979098168083529681528782208054600180820183559184528284206004820401805460039092166008026101000a6001600160401b038181021990931697909216919091029590951790945591815282825295862080549283018155865290942090930180546001600160a01b0319169092179091555050565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260016020526040812080546001600160401b038616908110610bae57fe5b60009182526020808320909101546001600160a01b0388811684526002835260408085209190921680855292529091205490915060016001600160401b0382161015610c2b5760405162461bcd60e51b815260040180806020018281038252602781526020018061113d6027913960400191505060405180910390fd5b6001600160a01b038087166000908152600260209081526040808320938616835292905290812080546001600160401b038716908110610c6757fe5b600091825260208083206004830401546001600160a01b038b8116855260038084526040808720928a168752919093529093208054919092166008026101000a9092046001600160401b03169250859183908110610cc157fe5b906000526020600020906002020160000160006101000a81548160ff02191690836002811115610ced57fe5b0217905550816001600160401b031660011415610ebd576001600160a01b038088166000908152600260209081526040808320938716835292905220805480610d3257fe5b60008281526020808220600460001994850190810490910180546001600160401b03600860038516026101000a02191690559093556001600160a01b038a168152600190925260408220805490918101908110610d8b57fe5b60009182526020808320909101546001600160a01b038b8116845260019092526040909220805491909216925080610dbf57fe5b60008281526020808220830160001990810180546001600160a01b03191690559092019092556001600160a01b038a1682526001905260409020546001600160401b03881614610eb7576001600160a01b038816600090815260016020526040902080548291906001600160401b038a16908110610e3957fe5b600091825260208083209190910180546001600160a01b0319166001600160a01b03948516179055918a168152600182526040908190205481516001600160401b039182168152908a169281019290925280517ffc2b62628f29b7ff88b0a776bc2ff1dca670be937c520f9ee832120866869e2e9281900390910190a15b5061106b565b6001600160a01b038088166000908152600260209081526040808320938716835292905290812080546001600160401b03600019860116908110610efd57fe5b600091825260208083206004830401546001600160a01b038c81168552600283526040808620918a16865292529220805460039092166008026101000a9092046001600160401b0316925080610f4f57fe5b60019003818190600052602060002090600491828204019190066008026101000a8154906001600160401b0302191690559055600183036001600160401b0316866001600160401b031614611069576001600160a01b03808916600090815260026020908152604080832093881683529290522080548291906001600160401b038916908110610fdb57fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055507f4c2907326878d2db295840cca7243664407575228cb84192bfd02687cb1a3960600184038760405180836001600160401b03168152602001826001600160401b031681526020019250505060405180910390a15b505b50505050505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b828054600181600116156101000203166002900490600052602060002090601f0160209004810192826110d15760008555611117565b82601f106110ea57805160ff1916838001178555611117565b82800160010185558215611117579182015b828111156111175782518255916020019190600101906110fc565b50611123929150611127565b5090565b5b80821115611123576000815560010161112856fe4e6f2070656e64696e6720726571756573747320666f722074686973206365727469666965722e4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d7573742062652074686520636f6e7472616374206f776e6572a2646970667358221220102283125f868bd93069713740346cbd6c35b57065b1a156e767503bb65c172e64736f6c63430007050033";

    public static final String FUNC_ADDREQUEST = "addRequest";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETPENDINGLIST = "getPendingList";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_GETPENDINGREQUESTERLISTSIZE = "getPendingRequesterListSize";

    public static final String FUNC_GETREQUEST = "getRequest";

    public static final String FUNC_GETREQUESTLISTSIZE = "getRequestListSize";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PROCESSREQUEST = "processRequest";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

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
        return web3j.ethLogFlowable(filter).map(new Function<Log, PendingRequestIndexChangedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, PendingRequesterIndexChangedEventResponse>() {
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

    public RemoteFunctionCall<TransactionReceipt> addRequest(String certifier, String requester, BigInteger timestamp, String attrNames) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.Address(160, requester), 
                new org.web3j.abi.datatypes.generated.Uint64(timestamp), 
                new org.web3j.abi.datatypes.Utf8String(attrNames)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getPendingList(String certifier, String requester) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPENDINGLIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.Address(160, requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint64>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getPendingRequesterAddress(String certifier, BigInteger requesterIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPENDINGREQUESTERADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPendingRequesterListSize(String certifier) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPENDINGREQUESTERLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>> getRequest(String certifier, String requester, BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.Address(160, requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>>(function,
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getRequestListSize(String certifier, String requester) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREQUESTLISTSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.Address(160, requester)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getRequestStatus(String certifier, String requester, BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETREQUESTSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.Address(160, requester), 
                new org.web3j.abi.datatypes.generated.Uint64(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> processRequest(String certifier, BigInteger requesterIndex, BigInteger pendingIndex, BigInteger newStatus) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PROCESSREQUEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, certifier), 
                new org.web3j.abi.datatypes.generated.Uint64(requesterIndex), 
                new org.web3j.abi.datatypes.generated.Uint64(pendingIndex), 
                new org.web3j.abi.datatypes.generated.Uint8(newStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractDependencies(BigInteger contractType, String addr) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETCONTRACTDEPENDENCIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(contractType), 
                new org.web3j.abi.datatypes.Address(160, addr)), 
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
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABERequests> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, root)));
        return deployRemoteCall(SmartDCPABERequests.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class PendingRequestIndexChangedEventResponse extends BaseEventResponse {
        public BigInteger oldIndex;

        public BigInteger newIndex;
    }

    public static class PendingRequesterIndexChangedEventResponse extends BaseEventResponse {
        public BigInteger oldIndex;

        public BigInteger newIndex;
    }
}
