package com.github.diegopacheco.blockchain.eth.java;

import java.io.File;
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

//
// more source code:  https://github.com/web3j/sample-project-gradle
//

/***
 * 
 * Steps:
 * 
 * 1. install testrpc: npm install -g ethereumjs-testrpc
 * 2. run testrpc. $ testrpc
 * 3. create a wallet from testrpc(copy first hash/pem/secret from testrpc output)
 * 4. download web3j https://gist.github.com/diegopacheco/1d23e7c1d29fbaaae4f1640c9c793d74
 * 5. import the wallet with $ bin/web3j wallet fromkey 4be2b66a4fa40bc260ec159e49a8ec727afa9126c6f403d083be0a684c85ea93
 * 6. Pass -DinfuraKey=<testrpc_first_hash_pem> -DmasterPWD=<the_wallet_pass> -DwalletFile=<localtion_to_.json_wallet_create_previous_step>
 * 7. Have fun.
 * 
 * @author diegopacheco
 * @version 1.0
 *
 */
public class MainSimple {
		
		private static final Logger log = LoggerFactory.getLogger(MainSimple.class);

		public static void main(String[] args) throws Throwable {
				
			 String infuraKey  = System.getProperty("infuraKey","");
			 String masterPWD  = System.getProperty("masterPWD","");
			 String walletFile = System.getProperty("walletFile","");
			
//			 Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/" + infuraKey)); 
			 Web3j web3j = Web3j.build(new HttpService("http://localhost:8545/" + infuraKey));
			 log.info("Connected to Ethereum client version: "  + web3j.web3ClientVersion().send().getWeb3ClientVersion());
			 
			 File f = new File("/home/diego/.ethereum/testnet/keystore/" + walletFile);
			 if (f.canRead()) {
			   System.out.println("is Readable");
			 }
		   Credentials credentials = WalletUtils.loadCredentials(masterPWD,"/home/diego/.ethereum/testnet/keystore/" + walletFile);
		   log.info("Credentials loaded " + credentials);
 
		   log.info("Sending 1 Wei (" + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
		   TransactionReceipt transferReceipt = Transfer.sendFunds(
	         web3j, credentials,
	         "ab092989301ec50c8a78515e188d45114543a9f2",     // you can put any address here
	         BigDecimal.ONE, Convert.Unit.WEI)  					  // 1 wei = 10^-18 Ether
	         .send();
		   log.info("Transaction complete: "  + transferReceipt.getTransactionHash());
			 
		}
}
