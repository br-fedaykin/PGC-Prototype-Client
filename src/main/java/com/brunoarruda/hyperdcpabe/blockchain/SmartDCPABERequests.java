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
import org.web3j.abi.datatypes.Utf8String;
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
public class SmartDCPABERequests extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b50604051620016b7380380620016b783398101604081905262000034916200006d565b600080546001600160a01b0319166001600160a01b0392909216919091179055620000c2565b80516200006781620000a8565b92915050565b6000602082840312156200008057600080fd5b60006200008e84846200005a565b949350505050565b60006001600160a01b03821662000067565b620000b38162000096565b8114620000bf57600080fd5b50565b6115e580620000d26000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806398fe2b661161007157806398fe2b6614610134578063b78ee7c414610154578063c53b614714610178578063c8c76f061461018b578063e9f67ece1461019e578063f0255a89146101b1576100a9565b80630595a466146100ae5780632af4c31e146100d75780634a2eae6c146100ec57806372ba60031461010c5780638da5cb5b1461012c575b600080fd5b6100c16100bc3660046110e1565b6101c4565b6040516100ce919061138d565b60405180910390f35b6100ea6100e5366004610fbc565b61020f565b005b6100ff6100fa36600461101c565b610264565b6040516100ce91906113ac565b61011f61011a366004610fe2565b6102be565b6040516100ce919061139b565b6100c1610369565b610147610142366004610fbc565b610378565b6040516100ce919061142c565b61016761016236600461101c565b610397565b6040516100ce9594939291906113ba565b6100ea610186366004611069565b61054f565b6100ea610199366004611184565b6107e7565b6101476101ac366004610fe2565b610879565b6100ea6101bf366004611111565b6108a4565b6001600160a01b038216600090815260016020526040812080546001600160401b0384169081106101f157fe5b6000918252602090912001546001600160a01b031690505b92915050565b6000546001600160a01b031633146102425760405162461bcd60e51b81526004016102399061141c565b60405180910390fd5b600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001600160a01b038084166000908152600360209081526040808320938616835292905290812080546001600160401b0384169081106102a057fe5b600091825260209091206002909102015460ff1690505b9392505050565b6001600160a01b03808316600090815260026020908152604080832093851683529281529082902080548351818402810184019094528084526060939283018282801561035c57602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b0316815260200190600801906020826007010492830192600103820291508084116103195790505b5050505050905092915050565b6000546001600160a01b031681565b6001600160a01b0381166000908152600160205260409020545b919050565b60008060008060606103a7610ce8565b6001600160a01b03808a166000908152600360209081526040808320938c1683529290522080546001600160401b0389169081106103e157fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff16600281111561041857fe5b600281111561042357fe5b815281546001600160401b0361010082048116602080850191909152600160481b90920416604080840191909152600184018054825181850281018501909352808352606090940193919290919060009084015b828210156105225760008481526020908190208301805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529283018282801561050e5780601f106104e35761010080835404028352916020019161050e565b820191906000526020600020905b8154815290600101906020018083116104f157829003601f168201915b505050505081526020019060010190610477565b50505091525050805160208201516040830151606090930151919c999b5099509097909650945050505050565b60048054604051634209fff160e01b81526001600160a01b0390911691634209fff19161057e9187910161138d565b60206040518083038186803b15801561059657600080fd5b505afa1580156105aa573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506105ce9190810190611166565b6105d457fe5b600554604051631c2353e160e01b81526001600160a01b0390911690631c2353e19061060490879060040161138d565b60206040518083038186803b15801561061c57600080fd5b505afa158015610630573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106549190810190611166565b61065a57fe5b6001600160a01b0384811660009081526003602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff1916919084908111156106e157fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff001990921691909117161782556060830151805161074c9260018501920190610d0f565b5050506001600160a01b03958616600081815260026020908152604080832098909916808352978152888220805460018181018355918452828420600482040180546001600160401b0398891660086003909416939093026101000a9283029890920219909116969096179095559181528382529687208054938401815587529095200180546001600160a01b031916909317909255505050565b6000546001600160a01b031633146108115760405162461bcd60e51b81526004016102399061141c565b600082600581111561081f57fe5b141561084557600580546001600160a01b0319166001600160a01b038316179055610875565b600482600581111561085357fe5b141561087557600480546001600160a01b0319166001600160a01b0383161790555b5050565b6001600160a01b03918216600090815260036020908152604080832093909416825291909152205490565b6001600160a01b038416600090815260016020526040812080546001600160401b0386169081106108d157fe5b60009182526020808320909101546001600160a01b0388811684526002835260408085209190921680855292529091205490915060016001600160401b038216101561092f5760405162461bcd60e51b81526004016102399061140c565b6001600160a01b038087166000908152600260209081526040808320938616835292905290812080546001600160401b03871690811061096b57fe5b600091825260208083206004830401546001600160a01b038b8116855260038084526040808720928a168752919093529093208054919092166008026101000a9092046001600160401b031692508591839081106109c557fe5b906000526020600020906002020160000160006101000a81548160ff021916908360028111156109f157fe5b0217905550816001600160401b031660011415610b73576001600160a01b038088166000908152600260209081526040808320938716835292905220805490610a3e906000198301610d6c565b506001600160a01b038716600090815260016020526040812080546000198101908110610a6757fe5b60009182526020808320909101546001600160a01b038b8116845260019092526040909220805491909216925090610aa3906000198301610da5565b506001600160a01b0388166000908152600160205260409020546001600160401b03881614610b6d576001600160a01b038816600090815260016020526040902080548291906001600160401b038a16908110610afc57fe5b600091825260208083209190910180546001600160a01b0319166001600160a01b03948516179055918a16815260019091526040908190205490517ffc2b62628f29b7ff88b0a776bc2ff1dca670be937c520f9ee832120866869e2e91610b64918a9061143a565b60405180910390a15b50610cdf565b6001600160a01b038088166000908152600260209081526040808320938716835292905290812080546001600160401b03600019860116908110610bb357fe5b600091825260208083206004830401546001600160a01b038c81168552600283526040808620918a16865292529220805460039092166008026101000a9092046001600160401b0316925090610c0d906000198301610d6c565b50600183036001600160401b0316866001600160401b031614610cdd576001600160a01b03808916600090815260026020908152604080832093881683529290522080548291906001600160401b038916908110610c6757fe5b90600052602060002090600491828204019190066008026101000a8154816001600160401b0302191690836001600160401b031602179055507f4c2907326878d2db295840cca7243664407575228cb84192bfd02687cb1a39606001840387604051610cd492919061143a565b60405180910390a15b505b50505050505050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b828054828255906000526020600020908101928215610d5c579160200282015b82811115610d5c5782518051610d4c918491602090910190610dc9565b5091602001919060010190610d2f565b50610d68929150610e43565b5090565b815481835581811115610da0576003016004900481600301600490048360005260206000209182019101610da09190610e69565b505050565b815481835581811115610da057600083815260209020610da0918101908301610e69565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e0a57805160ff1916838001178555610e37565b82800160010185558215610e37579182015b82811115610e37578251825591602001919060010190610e1c565b50610d68929150610e69565b610e6691905b80821115610d68576000610e5d8282610e83565b50600101610e49565b90565b610e6691905b80821115610d685760008155600101610e6f565b50805460018160011615610100020316600290046000825580601f10610ea95750610ec7565b601f016020900490600052602060002090810190610ec79190610e69565b50565b803561020981611562565b600082601f830112610ee657600080fd5b8135610ef9610ef48261147b565b611455565b81815260209384019390925082018360005b83811015610f375781358601610f218882610f62565b8452506020928301929190910190600101610f0b565b5050505092915050565b805161020981611576565b80356102098161157f565b80356102098161158c565b600082601f830112610f7357600080fd5b8135610f81610ef48261149b565b91508082526020830160208301858383011115610f9d57600080fd5b610fa8838284611512565b50505092915050565b803561020981611599565b600060208284031215610fce57600080fd5b6000610fda8484610eca565b949350505050565b60008060408385031215610ff557600080fd5b60006110018585610eca565b925050602061101285828601610eca565b9150509250929050565b60008060006060848603121561103157600080fd5b600061103d8686610eca565b935050602061104e86828701610eca565b925050604061105f86828701610fb1565b9150509250925092565b6000806000806080858703121561107f57600080fd5b600061108b8787610eca565b945050602061109c87828801610eca565b93505060406110ad87828801610fb1565b92505060608501356001600160401b038111156110c957600080fd5b6110d587828801610ed5565b91505092959194509250565b600080604083850312156110f457600080fd5b60006111008585610eca565b925050602061101285828601610fb1565b6000806000806080858703121561112757600080fd5b60006111338787610eca565b945050602061114487828801610fb1565b935050604061115587828801610fb1565b92505060606110d587828801610f57565b60006020828403121561117857600080fd5b6000610fda8484610f41565b6000806040838503121561119757600080fd5b60006110018585610f4c565b60006102b783836112a2565b60006111bb8383611384565b505060200190565b6111cc816114d5565b82525050565b60006111dd826114c8565b6111e781856114cc565b9350836020820285016111f9856114c2565b8060005b85811015611233578484038952815161121685826111a3565b9450611221836114c2565b60209a909a01999250506001016111fd565b5091979650505050505050565b600061124b826114c8565b61125581856114cc565b9350611260836114c2565b8060005b8381101561128e57815161127888826111af565b9750611283836114c2565b925050600101611264565b509495945050505050565b6111cc81611507565b60006112ad826114c8565b6112b781856114cc565b93506112c781856020860161151e565b6112d08161154e565b9093019392505050565b60006112e76027836114cc565b7f4e6f2070656e64696e6720726571756573747320666f722074686973206365728152663a34b334b2b91760c91b602082015260400192915050565b60006113306036836114cc565b7f4f7065726174696f6e206e6f7420616c6c6f7765642e2055736572206d757374815275103132903a34329031b7b73a3930b1ba1037bbb732b960511b602082015260400192915050565b6111cc81610e66565b6111cc816114fb565b6020810161020982846111c3565b602080825281016102b78184611240565b602081016102098284611299565b60a081016113c88288611299565b6113d56020830187611384565b6113e26040830186611384565b6113ef6060830185611384565b818103608083015261140181846111d2565b979650505050505050565b60208082528101610209816112da565b6020808252810161020981611323565b60208101610209828461137b565b604081016114488285611384565b6102b76020830184611384565b6040518181016001600160401b038111828210171561147357600080fd5b604052919050565b60006001600160401b0382111561149157600080fd5b5060209081020190565b60006001600160401b038211156114b157600080fd5b506020601f91909101601f19160190565b60200190565b5190565b90815260200190565b6000610209826114ef565b151590565b8061039281611558565b6001600160a01b031690565b6001600160401b031690565b6000610209826114e5565b82818337506000910152565b60005b83811015611539578181015183820152602001611521565b83811115611548576000848401525b50505050565b601f01601f191690565b60038110610ec757fe5b61156b816114d5565b8114610ec757600080fd5b61156b816114e0565b60068110610ec757600080fd5b60038110610ec757600080fd5b61156b816114fb56fea365627a7a72305820bbbf1c17cf6d9b242e55ce16cc3de1edc81e8c6f579530cc67d9100ad10a709b6c6578706572696d656e74616cf564736f6c634300050a0040";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_CHANGEOWNERSHIP = "changeOwnership";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_GETPENDINGLIST = "getPendingList";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETPENDINGREQUESTERLISTSIZE = "getPendingRequesterListSize";

    public static final String FUNC_GETREQUEST = "getRequest";

    public static final String FUNC_ADDREQUEST = "addRequest";

    public static final String FUNC_SETCONTRACTDEPENDENCIES = "setContractDependencies";

    public static final String FUNC_GETREQUESTLISTSIZE = "getRequestListSize";

    public static final String FUNC_PROCESSREQUEST = "processRequest";

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

    public RemoteCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<String>>> getRequest(String certifier, String requester, BigInteger index) {
        final Function function = new Function(FUNC_GETREQUEST,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier),
                new org.web3j.abi.datatypes.Address(requester),
                new org.web3j.abi.datatypes.generated.Uint64(index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<String>>>(
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<String>>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, List<String>>(
                                (BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                convertToNative((List<Utf8String>) results.get(4).getValue()));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addRequest(String certifier, String requester, BigInteger timestamp, List<String> attrNames) {
        final Function function = new Function(
                FUNC_ADDREQUEST,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(certifier),
                new org.web3j.abi.datatypes.Address(requester),
                new org.web3j.abi.datatypes.generated.Uint64(timestamp),
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(attrNames, org.web3j.abi.datatypes.Utf8String.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
