package com.github.diegopacheco.blockchain.pocs.ethereumj.simple;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
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
		
		//
		// SET
		//
		
    EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(c.getAddress(),DefaultBlockParameterName.LATEST).send();
    BigInteger count = ethGetTransactionCount.getTransactionCount();

    Function function = new Function(
    		"set",
    		Arrays.<Type>asList(new Uint256(BigInteger.TEN)), //Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String("10")),
    		Collections.<TypeReference<?>>emptyList());
    
    String encodedFunction = FunctionEncoder.encode(function);

    Transaction transaction = Transaction.createFunctionCallTransaction(c.getAddress(),count, ss.getGasPrice(), gasLimit, ss.getContractAddress(),  encodedFunction);
    System.out.println("TX: " + transaction.getData());
    
    EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).send();
    System.out.println("TX Response SET: " + transactionResponse.getId()  + " - " +transactionResponse.getJsonrpc());
    System.out.println("Error ? : " + transactionResponse.getError());
    
    //
    // GET
    //
    
    ethGetTransactionCount = web3j.ethGetTransactionCount(c.getAddress(),DefaultBlockParameterName.LATEST).send();
    count = ethGetTransactionCount.getTransactionCount();
    
    Function functionGet = new Function(
    		"get",
    		Arrays.<Type>asList(), 
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    
    String encodedFunctionGet = FunctionEncoder.encode(functionGet);

    Transaction transactionGet = Transaction.createFunctionCallTransaction(c.getAddress(),count, ss.getGasPrice(), gasLimit, ss.getContractAddress(),  encodedFunctionGet);
    System.out.println("TX: " + transaction.getData());
    
    EthSendTransaction transactionResponseGet = web3j.ethSendTransaction(transactionGet).send();
    System.out.println("TX Response GET: " + transactionResponseGet.getId()  + " - " + transactionResponseGet.getJsonrpc());
    System.out.println("TX Response GET: " + transactionResponseGet.getResult());
    System.out.println("TX Response GET: " + transactionResponseGet.getRawResponse());
    System.out.println("TX Response GET: " + transactionResponseGet.getJsonrpc());
    System.out.println("TX Response GET: " + transactionResponseGet.getTransactionHash());
    System.out.println("Error ? : " + transactionResponseGet.getError());
    
	}
	
}
