package com.github.diegopacheco.blockchain.pocs.ethereumj.simple;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		Credentials c = WalletUtils.loadCredentials("123456", "wallet/UTC--2018-06-01T06-41-04.272000000Z--647a58fd17196282d92f4a73d1db1e0337616226.json");
		String address = c.getAddress();
		System.out.println("Wallet Address " + address);
		System.out.println("Wallet Private Key: " +	c.getEcKeyPair().getPrivateKey());
		System.out.println("Wallet Public Key: " +	c.getEcKeyPair().getPublicKey());
		
		Web3j web3j = Web3j.build(new HttpService());
		System.out.println("web3j: " + web3j);
		
		BigInteger gasLimit =  web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"),true).send().getBlock().getGasLimit();
		System.out.println("Gas Limit: " + gasLimit);
		
		SimpleStorage ss = SimpleStorage.load(address, web3j,c, BigInteger.ONE, gasLimit);
		System.out.println("SimpleStorage: " + ss);
		
		TransactionReceipt tr = ss.set(BigInteger.TEN).send();
		System.out.println("TX set result: " + tr);
		
		BigInteger result = ss.get().send();
		System.out.println("Tx get Result: " + result);
	}
	
}
