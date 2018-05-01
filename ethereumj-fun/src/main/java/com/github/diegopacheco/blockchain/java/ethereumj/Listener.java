package com.github.diegopacheco.blockchain.java.ethereumj;

import java.util.List;

import org.ethereum.core.Block;
import org.ethereum.facade.Ethereum;
import org.ethereum.listener.EthereumListenerAdapter;

public class Listener extends EthereumListenerAdapter{
	
	private boolean syncDone = false;
	private Ethereum ethereum;
	
	public Ethereum getEthereum() {
		return ethereum;
	}

	public void setEthereum(Ethereum ethereum) {
		this.ethereum = ethereum;
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
