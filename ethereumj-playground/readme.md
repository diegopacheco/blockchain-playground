## Create a Wallet

```bash
web3j wallet create
```

## Create Wallet - Import from testRPC - account[9]

```bash
web3j wallet fromkey  f49ad554186f54d451a79e1929a487a1804d8b8d9f9e1c3f9a9c0bee9b07e328
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