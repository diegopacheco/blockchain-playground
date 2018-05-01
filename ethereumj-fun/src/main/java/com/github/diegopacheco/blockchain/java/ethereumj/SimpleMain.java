package com.github.diegopacheco.blockchain.java.ethereumj;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ethereum.core.Block;
import org.ethereum.core.CallTransaction;
import org.ethereum.core.Transaction;
import org.ethereum.core.TransactionReceipt;
import org.ethereum.crypto.ECKey;
import org.ethereum.db.ByteArrayWrapper;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.listener.EthereumListenerAdapter;
import org.ethereum.samples.TestNetSample;
import org.ethereum.solidity.compiler.CompilationResult;
import org.ethereum.solidity.compiler.SolidityCompiler;
import org.ethereum.util.ByteUtil;
import org.ethereum.vm.program.ProgramResult;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * 
 * Local network: 
 * 
 * geth --datadir ./eth-data/ init src/main/resources/genesis/frontier.json
 * geth --port 40303 --datadir ./eth-data/ src/main/resources/genesis/frontier.json
 * 
 * @author diegopacheco
 *
 */
public class SimpleMain extends TestNetSample {
	
  @Autowired
  SolidityCompiler compiler;

  String contract =
          "contract IncContract {" +
          "  int i;" +
          "  function inc(int n) {" +
          "    i = i + n;" +
          "  }" +
          "  function get() returns (int) {" +
          "    return i;" +
          "  }" +
          "}";
	
  private Map<ByteArrayWrapper, TransactionReceipt> txWaiters = Collections.synchronizedMap(new HashMap<ByteArrayWrapper, TransactionReceipt>());
  
	private static class Config extends TestNetConfig {
		@Override
		@Bean
		public SimpleMain sampleBean() {
			return new SimpleMain();
		}
	}
	
	public static void main(String[] args) {
		EthereumFactory.createEthereum(Config.class);
	}
	
  @Override
  public void onSyncDone() throws Exception {
      ethereum.addListener(new EthereumListenerAdapter() {
          @Override
          public void onBlock(Block block, List<TransactionReceipt> receipts) {
          	SimpleMain.this.onBlock(block, receipts);
          }
      });

      logger.info("Compiling contract...");
      SolidityCompiler.Result result = compiler.compileSrc(contract.getBytes(), true, true,
              SolidityCompiler.Options.ABI, SolidityCompiler.Options.BIN);
      if (result.isFailed()) {
          throw new RuntimeException("Contract compilation failed:\n" + result.errors);
      }
      CompilationResult res = CompilationResult.parse(result.output);
      if (res.getContracts().isEmpty()) {
          throw new RuntimeException("Compilation failed, no contracts returned:\n" + result.errors);
      }
      CompilationResult.ContractMetadata metadata = res.getContracts().iterator().next();
      if (metadata.bin == null || metadata.bin.isEmpty()) {
          throw new RuntimeException("Compilation failed, no binary returned:\n" + result.errors);
      }

      logger.info("Sending contract to net and waiting for inclusion");
      TransactionReceipt receipt = sendTxAndWait(new byte[0], Hex.decode(metadata.bin));

      if (!receipt.isSuccessful()) {
          logger.error("Some troubles creating a contract: " + receipt.getError());
          return;
      }

      byte[] contractAddress = receipt.getTransaction().getContractAddress();
      logger.info("Contract created: " + Hex.toHexString(contractAddress));

      logger.info("Calling the contract function 'inc'");
      CallTransaction.Contract contract = new CallTransaction.Contract(metadata.abi);
      CallTransaction.Function inc = contract.getByName("inc");
      byte[] functionCallBytes = inc.encode(777);
      TransactionReceipt receipt1 = sendTxAndWait(contractAddress, functionCallBytes);
      if (!receipt1.isSuccessful()) {
          logger.error("Some troubles invoking the contract: " + receipt.getError());
          return;
      }
      logger.info("Contract modified!");

      ProgramResult r = ethereum.callConstantFunction(Hex.toHexString(contractAddress),
              contract.getByName("get"));
      Object[] ret = contract.getByName("get").decodeResult(r.getHReturn());
      logger.info("Current contract data member value: " + ret[0]);
  }
  
  private void onBlock(Block block, List<TransactionReceipt> receipts) {
    for (TransactionReceipt receipt : receipts) {
        ByteArrayWrapper txHashW = new ByteArrayWrapper(receipt.getTransaction().getHash());
        if (txWaiters.containsKey(txHashW)) {
            txWaiters.put(txHashW, receipt);
            synchronized (this) {
                notifyAll();
            }
        }
    }
  }
  
  protected TransactionReceipt sendTxAndWait(byte[] receiveAddress, byte[] data) throws InterruptedException {
    BigInteger nonce = ethereum.getRepository().getNonce(senderAddress);
    Transaction tx = new Transaction(
            ByteUtil.bigIntegerToBytes(nonce),
            ByteUtil.longToBytesNoLeadZeroes(ethereum.getGasPrice()),
            ByteUtil.longToBytesNoLeadZeroes(3_000_000),
            receiveAddress,
            ByteUtil.longToBytesNoLeadZeroes(0),
            data,
            ethereum.getChainIdForNextBlock());
    tx.sign(ECKey.fromPrivate(senderPrivateKey));
    logger.info("<=== Sending transaction: " + tx);
    ethereum.submitTransaction(tx);
    return waitForTx(tx.getHash());
  }

  protected TransactionReceipt waitForTx(byte[] txHash) throws InterruptedException {
    ByteArrayWrapper txHashW = new ByteArrayWrapper(txHash);
    txWaiters.put(txHashW, null);
    long startBlock = ethereum.getBlockchain().getBestBlock().getNumber();
    while(true) {
        TransactionReceipt receipt = txWaiters.get(txHashW);
        if (receipt != null) {
            return receipt;
        } else {
            long curBlock = ethereum.getBlockchain().getBestBlock().getNumber();
            if (curBlock > startBlock + 16) {
                throw new RuntimeException("The transaction was not included during last 16 blocks: " + txHashW.toString().substring(0,8));
            } else {
                logger.info("Waiting for block with transaction 0x" + txHashW.toString().substring(0,8) +
                        " included (" + (curBlock - startBlock) + " blocks received so far) ...");
            }
        }
        synchronized (this) {
            wait(20000);
        }
    }
  }

}
