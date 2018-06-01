## Create a Wallet

```bash
web3j wallet create
```

## Generate BIN file from Solidity Contract

```bash
solc Deliverables.sol --bin
```

## Generate ABI file from Solidity Contract

```bash
solc Deliverables.sol --abi
```

## How to generate java Code?

```bash
cd contracts/
web3j solidity generate Deliverables.bin Deliverables.abi -o ../src/main/java -p com.github.diegopacheco.blockchain.pocs.ethereumj.simple
```

## Run Private FAKE network

```bash
testrpc
```