package com.github.diegopacheco.blockchain.java.ethereumj;

import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;
import org.ethereum.config.SystemProperties;
import org.ethereum.crypto.ECKey;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
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
public class SimpleMain {
	
	private static Ethereum ethereum = EthereumFactory.createEthereum(NodeConfig.class);
	
	 private static class NodeConfig {
     @Bean
     public SystemProperties systemProperties() {
    	 	return new SystemProperties(getConfig(0,"127.0.0.1:30303"));
     }
	 } 
	
  private static Config getConfig(int index, String discoveryNode) {
    return ConfigFactory.empty()
            .withValue("peer.discovery.enabled", value(true))
            .withValue("peer.discovery.external.ip", value("127.0.0.1"))
            .withValue("peer.discovery.bind.ip", value("127.0.0.1"))
            .withValue("peer.discovery.persist", value("false"))
            .withValue("peer.listen.port", value(30304))
            .withValue("peer.privateKey", value(Hex.toHexString(ECKey.fromPrivate(("" + "0xf8640b054236b7690fae34dbf955bae468737540").getBytes()).getPrivKeyBytes())))
            .withValue("peer.networkId", value(33))
            .withValue("sync.enabled", value(true))
            .withValue("database.incompatibleDatabaseBehavior", value("RESET"))
            .withValue("genesis", value("gen.json"))
            .withValue("database.dir", value("sampleDB-" + index))
            .withValue("peer.discovery.enabled", value("false"))
            .withValue("peer.discovery.ip.list", value(discoveryNode != null ? Arrays.asList(discoveryNode) : Arrays.asList()));
  }
	
  private static ConfigValue value(Object value) {
    return ConfigValueFactory.fromAnyRef(value);
  }
	
	public static void main(String[] args) throws Exception {
		Listener l = new Listener();
		l.setEthereum(ethereum);
		ethereum.addListener(l);
	}

	
}
