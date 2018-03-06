package com.github.diegopacheco.blockchain.eth.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;

// https://github.com/web3j/sample-project-gradle
public class MainSimple {
		
		private static final Logger log = LoggerFactory.getLogger(MainSimple.class);

		public static void main(String[] args) throws Throwable {
				
			 String infuraKey  = System.getProperty("infuraKey","");
			 String masterPWD  = System.getProperty("masterPWD","");
			 String walletFile = System.getProperty("walletFile","");
			
//			 Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/" + infuraKey)); 
			 Web3j web3j = Web3j.build(new HttpService("http://localhost:8545/" + infuraKey));
			 log.info("Connected to Ethereum client version: "  + web3j.web3ClientVersion().send().getWeb3ClientVersion());
			
			 Admin admin = Admin.build(new HttpService("http://localhost:8545/" + infuraKey)); 
			 PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount("0x954e2e1253f2df0e97c1a9e71d35f8523a289eda", "4be2b66a4fa40bc260ec159e49a8ec727afa9126c6f403d083be0a684c85ea93").sendAsync().get();
			 if (personalUnlockAccount!=null &&  personalUnlockAccount.getRawResponse()!=null && personalUnlockAccount.accountUnlocked()) {
				 System.out.println("Account Unlock! ID: " + personalUnlockAccount.getId());
			 }else {
				 System.out.println("Error. Raw response is null! ");
			 }
			 
//		   Credentials credentials = WalletUtils.loadCredentials(masterPWD,"/home/diego/.ethereum/testnet/keystore/" + walletFile);
//		   log.info("Credentials loaded " + credentials);
 
//		   log.info("Sending 1 Wei (" + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
//		   TransactionReceipt transferReceipt = Transfer.sendFunds(
//	         web3j, credentials,
//	         "ab092989301ec50c8a78515e188d45114543a9f2",  // you can put any address here
//	         BigDecimal.ONE, Convert.Unit.WEI)  					  // 1 wei = 10^-18 Ether
//	         .send();
//		   log.info("Transaction complete: "  + transferReceipt.getTransactionHash());
			 
		}
}
