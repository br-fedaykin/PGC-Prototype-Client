package utils;

import com.brunoarruda.hyperdcpabe.blockchain.BlockchainConnection;

public class StubBlockChain extends BlockchainConnection {

    public StubBlockChain(String url, String contractFilesAddress, String contractAuthorityAddress) {
        super(url, contractFilesAddress, contractAuthorityAddress, null, null);
        // TODO Auto-generated constructor stub
    }
}