package com.github.diegopacheco.blockchain.eth.java;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

// https://github.com/web3j/sample-project-gradle
public class MainSimple {
		
		private static final Logger log = LoggerFactory.getLogger(MainSimple.class);

		public static void main(String[] args) throws Throwable {
				
			 String infuraKey  = System.getProperty("infuraKey","");
			 String masterPWD  = System.getProperty("masterPWD","");
			
			 Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/" + infuraKey)); 
			 log.info("Connected to Ethereum client version: "  + web3j.web3ClientVersion().send().getWeb3ClientVersion());
			
		   Credentials credentials = WalletUtils.loadCredentials(masterPWD,"/home/diego/.ethereum/testnet/keystore/");
		   log.info("Credentials loaded " + credentials);
		   
		   log.info("Sending 1 Wei (" + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
		   TransactionReceipt transferReceipt = Transfer.sendFunds(
	         web3j, credentials,
	         "ab092989301ec50c8a78515e188d45114543a9f2",  // you can put any address here
	         BigDecimal.ONE, Convert.Unit.WEI)  					  // 1 wei = 10^-18 Ether
	         .send();
		   log.info("Transaction complete: "  + transferReceipt.getTransactionHash());
			 
		}
}
