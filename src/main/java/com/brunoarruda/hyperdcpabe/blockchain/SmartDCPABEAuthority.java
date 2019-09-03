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
    private static final String BINARY = "608060405234801561001057600080fd5b506040516119a93803806119a98339818101604052602081101561003357600080fd5b505160405161004190610092565b604051809103906000f08015801561005d573d6000803e3d6000fd5b50600680546001600160a01b039283166001600160a01b0319918216179091556007805493909216921691909117905561009f565b6103cc806115dd83390190565b61152f806100ae6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c80638aa33ff3116100715780638aa33ff31461042e57806391eca0c11461046e578063b78ee7c41461048b578063e7a516b114610566578063f0255a89146105a6578063f4d67309146105ef576100b4565b80631c2353e1146100b95780631d706777146100f3578063362f09c81461022957806337a92dc21461026b5780634a2eae6c146103a65780635fd43d511461040a575b600080fd5b6100df600480360360208110156100cf57600080fd5b50356001600160a01b03166106b9565b604080519115158252519081900360200190f35b6101196004803603602081101561010957600080fd5b50356001600160a01b03166106da565b60405180856001600160a01b03166001600160a01b031681526020018060200180602001846001600160401b03166001600160401b03168152602001838103835286818151815260200191508051906020019080838360005b8381101561018a578181015183820152602001610172565b50505050905090810190601f1680156101b75780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b838110156101ea5781810151838201526020016101d2565b50505050905090810190601f1680156102175780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b61024f6004803603602081101561023f57600080fd5b50356001600160401b03166108c7565b604080516001600160a01b039092168252519081900360200190f35b6103a46004803603606081101561028157600080fd5b6001600160a01b038235169190810190604081016020820135600160201b8111156102ab57600080fd5b8201836020820111156102bd57600080fd5b803590602001918460018302840111600160201b831117156102de57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561033057600080fd5b82018360208201111561034257600080fd5b803590602001918460018302840111600160201b8311171561036357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506108eb945050505050565b005b6103e6600480360360608110156103bc57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610b6e565b604051808260028111156103f657fe5b60ff16815260200191505060405180910390f35b610412610bc6565b604080516001600160401b039092168252519081900360200190f35b61045c6004803603604081101561044457600080fd5b506001600160a01b0381358116916020013516610bd5565b60408051918252519081900360200190f35b61024f6004803603602081101561048457600080fd5b5035610c00565b6104cb600480360360608110156104a157600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610c27565b604051808560028111156104db57fe5b60ff168152602001846001600160401b03166001600160401b03168152602001836001600160401b03166001600160401b0316815260200180602001828103825283818151815260200191508051906020019060200280838360005b8381101561054f578181015183820152602001610537565b505050509050019550505050505060405180910390f35b6104cb6004803603606081101561057c57600080fd5b5080356001600160a01b0390811691602081013590911690604001356001600160401b0316610d5e565b6103a4600480360360808110156105bc57600080fd5b5080356001600160a01b03169060208101356001600160401b03908116916040810135909116906060013560ff16610de8565b6103a46004803603608081101561060557600080fd5b6001600160a01b0382358116926020810135909116916001600160401b036040830135169190810190608081016060820135600160201b81111561064857600080fd5b82018360208201111561065a57600080fd5b803590602001918460208302840111600160201b8311171561067b57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550611167945050505050565b6001600160a01b031660009081526001602081905260409091200154151590565b6001600160a01b0380821660009081526001602081905260408083208054600654938201548351639201de5560e01b81526004810191909152925194956060958695889593831693921691639201de559160248083019288929190829003018186803b15801561074957600080fd5b505afa15801561075d573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561078657600080fd5b810190808051600160201b81111561079d57600080fd5b820160208101848111156107b057600080fd5b8151600160201b8111828201871017156107c957600080fd5b5050600654600287015460408051639201de5560e01b81526004810192909252519295506001600160a01b039091169350639201de559250602480820192600092909190829003018186803b15801561082157600080fd5b505afa158015610835573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052602081101561085e57600080fd5b810190808051600160201b81111561087557600080fd5b8201602081018481111561088857600080fd5b8151600160201b8111828201871017156108a157600080fd5b505060039690960154949b939a50949850506001600160401b0390921695509350505050565b6001600160401b03166000908152600360205260409020546001600160a01b031690565b600080546001810182558180527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630180546001600160a01b0319166001600160a01b038681169182179092556040805160808101825291825260065490516319f6a32560e31b8152602060048201818152885160248401528851949682880196949094169463cfb51928948a949293849360440192908601918190849084905b838110156109a357818101518382015260200161098b565b50505050905090810190601f1680156109d05780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b1580156109ed57600080fd5b505afa158015610a01573d6000803e3d6000fd5b505050506040513d6020811015610a1757600080fd5b505181526006546040516319f6a32560e31b8152602060048201818152865160248401528651948201946001600160a01b039094169363cfb5192893889383926044909201919085019080838360005b83811015610a7f578181015183820152602001610a67565b50505050905090810190601f168015610aac5780820380516001836020036101000a031916815260200191505b509250505060206040518083038186803b158015610ac957600080fd5b505afa158015610add573d6000803e3d6000fd5b505050506040513d6020811015610af357600080fd5b50518152600060209182018190526001600160a01b0395861681526001808352604091829020845181546001600160a01b031916981697909717875591830151918601919091558101516002850155606001516003909301805467ffffffffffffffff19166001600160401b03909416939093179092555050565b6001600160a01b038084166000908152600560209081526040808320938616835292905290812080546001600160401b038416908110610baa57fe5b600091825260209091206002909102015460ff16949350505050565b6002546001600160401b031681565b6001600160a01b03918216600090815260046020908152604080832093909416825291909152205490565b60008181548110610c0d57fe5b6000918252602090912001546001600160a01b0316905081565b60008060006060610c36611392565b6001600160a01b038089166000908152600560209081526040808320938b1683529290522080546001600160401b038816908110610c7057fe5b90600052602060002090600202016040518060800160405290816000820160009054906101000a900460ff166002811115610ca757fe5b6002811115610cb257fe5b815281546001600160401b0361010082048116602080850191909152600160481b909204166040808401919091526001840180548251818502810185019093528083526060909401939192909190830182828015610d2f57602002820191906000526020600020905b815481526020019060010190808311610d1b575b505050919092525050815160208301516040840151606090940151919c909b5092995097509095505050505050565b6001600160a01b0380841660009081526004602090815260408083209386168352929052908120805482918291606091610dd791899189916001600160401b038a16908110610da957fe5b90600052602060002090600491828204019190066008029054906101000a90046001600160401b0316610c27565b935093509350935093509350935093565b6001600160401b038084166000908152600360209081526040808320546001600160a01b03898116855260048452828520911680855292528220805491939091908616908110610e3457fe5b600091825260208083206004830401546001600160a01b038a81168552600583526040808620918816865292529220805460039092166008026101000a9092046001600160401b0316925084919083908110610e8c57fe5b906000526020600020906002020160000160006101000a81548160ff02191690836002811115610eb857fe5b02179055506001600160a01b0380871660009081526004602090815260408083209386168352929052205460011415610fb4576001600160a01b038087166000908152600460209081526040808320938616835292905220805480610f1957fe5b60008281526020902060046000199092019182040180546001600160401b03600860038516026101000a02191690559055845b6002546001600160401b039081169082161015610fae576001600160401b0360018201818116600090815260036020526040808220549390941681529290922080546001600160a01b0319166001600160a01b03909216919091179055610f4c565b5061115f565b6001600160a01b03808716600090815260046020908152604080832093861683529281529082902080548351818402810184019094528084526060939283018282801561105257602002820191906000526020600020906000905b82829054906101000a90046001600160401b03166001600160401b03168152602001906008019060208260070104928301926001038202915080841161100f5790505b509394508893505050505b6001825103816001600160401b031610156110c75781816001016001600160401b03168151811061108a57fe5b602002602001015182826001600160401b0316815181106110a757fe5b6001600160401b039092166020928302919091019091015260010161105d565b506001600160a01b038088166000908152600460209081526040808320938716835292815291902082516110fd928401906113b9565b506001600160a01b03808816600090815260046020908152604080832093871683529290522080548061112c57fe5b60008281526020902060046000199092019182040180546001600160401b03600860038516026101000a02191690559055505b505050505050565b60075460408051634209fff160e01b81526001600160a01b03868116600483015291519190921691634209fff1916024808301926020929190829003018186803b1580156111b457600080fd5b505afa1580156111c8573d6000803e3d6000fd5b505050506040513d60208110156111de57600080fd5b50516111e657fe5b6111ef846106b9565b6111f557fe5b6001600160a01b0384811660009081526005602090815260408083209387168352928152828220805484516080810186528481526001600160401b038816818501529485018490526060850186905260018082018084559285529290932084516002808602909201805495969395939490939192849260ff19169190849081111561127c57fe5b0217905550602082810151825460408501516001600160401b03908116600160481b0270ffffffffffffffff00000000000000000019919093166101000268ffffffffffffffff00199092169190911716178255606083015180516112e79260018501920190611471565b5050506001600160a01b0395861660009081526004602081815260408084209890991680845297815288832080546001818101835591855282852093810490930180546001600160401b0397881660086003968716026101000a908102908902199091161790556002805487168552929091529790912080546001600160a01b0319169096179095555083548082169095011667ffffffffffffffff19909416939093179091555050565b60408051608081019091528060008152600060208201819052604082015260609081015290565b828054828255906000526020600020906003016004900481019282156114615791602002820160005b8382111561142c57835183826101000a8154816001600160401b0302191690836001600160401b0316021790555092602001926008016020816007010492830192600103026113e2565b801561145f5782816101000a8154906001600160401b03021916905560080160208160070104928301926001030261142c565b505b5061146d9291506114b8565b5090565b8280548282559060005260206000209081019282156114ac579160200282015b828111156114ac578251825591602001919060010190611491565b5061146d9291506114e0565b6114dd91905b8082111561146d57805467ffffffffffffffff191681556001016114be565b90565b6114dd91905b8082111561146d57600081556001016114e656fea265627a7a723058204fd85b2d91dd485f861c8a0d456d77bf54daeacc6f0b441c8dc4d95166eebc5c64736f6c634300050a0032608060405234801561001057600080fd5b506103ac806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806370751f5b146100465780639201de55146100e5578063cfb5192814610102575b600080fd5b6100706004803603604081101561005c57600080fd5b5060ff19813516906020013560ff166101ba565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100aa578181015183820152602001610092565b50505050905090810190601f1680156100d75780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610070600480360360208110156100fb57600080fd5b5035610242565b6101a86004803603602081101561011857600080fd5b81019060208101813564010000000081111561013357600080fd5b82018360208201111561014557600080fd5b8035906020019184600183028401116401000000008311171561016757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610347945050505050565b60408051918252519081900360200190f35b60608160ff166040519080825280601f01601f1916602001820160405280156101ea576020820181803883390190505b50905060ff19831660005b8360ff1681101561023a5782516008820260020a830290819085908490811061021a57fe5b60200101906001600160f81b031916908160001a905350506001016101f5565b505092915050565b6040805160208082528183019092526060918291906020820181803883390190505090506000805b60208110156102c0576008810260020a85026001600160f81b03198116156102b7578084848151811061029957fe5b60200101906001600160f81b031916908160001a9053506001909201915b5060010161026a565b506060816040519080825280601f01601f1916602001820160405280156102ee576020820181803883390190505b50905060005b8281101561033e5783818151811061030857fe5b602001015160f81c60f81b82828151811061031f57fe5b60200101906001600160f81b031916908160001a9053506001016102f4565b50949350505050565b6000606082905060208151111561035a57fe5b805161036a575060009050610372565b505060208101515b91905056fea265627a7a72305820cca4bd2eb96bf7e7a2d5e2ca2bb6bb09492ec6c3e1fe3f1b3d3e8f63e9ac174c64736f6c634300050a0032";

    public static final String FUNC_ISCERTIFIER = "isCertifier";

    public static final String FUNC_GETCERTIFIER = "getCertifier";

    public static final String FUNC_GETPENDINGREQUESTERADDRESS = "getPendingRequesterAddress";

    public static final String FUNC_ADDCERTIFIER = "addCertifier";

    public static final String FUNC_GETREQUESTSTATUS = "getRequestStatus";

    public static final String FUNC_NUMREQUESTOWNERS = "numRequestOwners";

    public static final String FUNC_GETPENDINGLISTSIZE = "getPendingListSize";

    public static final String FUNC_CERTIFIERADDRESSES = "certifierAddresses";

    public static final String FUNC_GETREQUEST = "getRequest";

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

    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String userContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userContract)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String userContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userContract)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String userContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userContract)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SmartDCPABEAuthority> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String userContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userContract)));
        return deployRemoteCall(SmartDCPABEAuthority.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
