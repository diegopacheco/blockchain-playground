package com.github.diegopacheco.blockchain.java.ethereumj;

import java.util.Arrays;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;
import org.ethereum.config.SystemProperties;
import org.ethereum.core.Block;
import org.ethereum.crypto.ECKey;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.listener.EthereumListenerAdapter;
import org.springframework.context.annotation.Bean;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

/**
 * 
 * @author diegopacheco
 *
 */
public class SimpleMain extends EthereumListenerAdapter {
	
	private boolean syncDone = false;
	
	private static Ethereum ethereum = EthereumFactory.createEthereum(NodeConfig.class);
	
	 private static class NodeConfig {
     @Bean
     public SystemProperties systemProperties() {
    	 	return new SystemProperties(getConfig(0,"127.0.0.1:20000"));
     }
	 } 
	
  private static Config getConfig(int index, String discoveryNode) {
    return ConfigFactory.empty()
            .withValue("peer.discovery.enabled", value(true))
            .withValue("peer.discovery.external.ip", value("127.0.0.1"))
            .withValue("peer.discovery.bind.ip", value("127.0.0.1"))
            .withValue("peer.discovery.persist", value("false"))
            .withValue("peer.listen.port", value(20000 + index))
            .withValue("peer.privateKey", value(Hex.toHexString(ECKey.fromPrivate(("" + index).getBytes()).getPrivKeyBytes())))
            .withValue("peer.networkId", value(555))
            .withValue("sync.enabled", value(true))
            .withValue("database.incompatibleDatabaseBehavior", value("RESET"))
            .withValue("genesis", value("sample-genesis.json"))
            .withValue("database.dir", value("sampleDB-" + index))
            .withValue("peer.discovery.ip.list", value(discoveryNode != null ? Arrays.asList(discoveryNode) : Arrays.asList()));
  }
	
  private static ConfigValue value(Object value) {
    return ConfigValueFactory.fromAnyRef(value);
  }
	
	public static void main(String[] args) throws Exception {
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
