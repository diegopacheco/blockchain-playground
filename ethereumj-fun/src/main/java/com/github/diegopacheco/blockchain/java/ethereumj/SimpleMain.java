package com.github.diegopacheco.blockchain.java.ethereumj;

import java.math.BigInteger;
import java.util.List;

import org.ethereum.core.Block;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.listener.EthereumListenerAdapter;
import org.ethereum.net.rlpx.Node;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Numeric;

/**
 * 
 * @author diegopacheco
 *
 */
public class SimpleMain extends EthereumListenerAdapter {

	private boolean syncDone = false;
	private static Ethereum ethereum = EthereumFactory.createEthereum();

	public static void main(String[] args) throws Exception {
		
		final ECKeyPair keyPair = ECKeyPair.create(Numeric.toBigInt("0x6771f2757b6717a202735a599d8215ee15f111c4"));
		BigInteger publicKey = keyPair.getPublicKey();
		
		String url = "enode://" + publicKey + "@localhost:8545";
		ethereum.connect(new Node(url));
	}

	@Override
	public void onSyncDone(SyncState state) {
		System.out.println("onSyncDone " + state);
		if (!syncDone) {
			System.out.println(" ** SYNC DONE ** ");
			syncDone = true;
		}
	}

	@Override
	public void onBlock(Block block, @SuppressWarnings("rawtypes") List receipts) {
		if (syncDone) {
			System.out.println("Block difficulty: " + block.getDifficultyBI().toString());
			System.out.println("Block transactions: " + block.getTransactionsList().toString());
			System.out.println("Best block (last block): " + ethereum.getBlockchain().getBestBlock().toString());
			System.out.println("Total difficulty: " + ethereum.getBlockchain().getTotalDifficulty().toString());
		}
	}

}
