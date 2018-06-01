package com.github.diegopacheco.blockchain.pocs.ethereumj.simple;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

public class Main {
	
	public static void main(String[] args) throws Throwable {
		
		Credentials c = WalletUtils.loadCredentials("123456", "wallet/UTC--2018-06-01T05-35-28.178000000Z--41837a2f9b3ee55165bd2692878ddfcec44330fe.json");
		String address = c.getAddress();
		System.out.println("Wallet Address " + address);
		System.out.println("Wallet Private Key: " +	c.getEcKeyPair().getPrivateKey());
		System.out.println("Wallet Public Key: " +	c.getEcKeyPair().getPublicKey());
		
		Web3j web3j = Web3j.build(new HttpService());
		System.out.println("web3j: " + web3j);
		
		BigInteger gasLimit =  web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"),true).send().getBlock().getGasLimit();
		System.out.println("Gas Limit: " + gasLimit);
		
		Deliverables deliverables = Deliverables.load(address, web3j,c, BigInteger.ONE, BigInteger.ONE);
		System.out.println("Deliverables: " + deliverables);
		
		String hash = web3j.web3Sha3("CONTENTS").send().getResult();
		System.out.println("Hash: " + hash);
		
		Address deliverable = new Address(address);
		System.out.println("Address: " + deliverable);
		
	  TransactionReceipt result = deliverables.store(address.toString()).send();
		System.out.println("Tx Result: " + result);
	  
	}
	
}
