package main

import (
	"fmt"
	"log"
	"math/big"

	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/accounts/abi/bind/backends"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core"
	"github.com/ethereum/go-ethereum/crypto"
)

func main() {
	key, _ := crypto.GenerateKey()
	auth := bind.NewKeyedTransactor(key)

	alloc := make(core.GenesisAlloc)
	alloc[auth.From] = core.GenesisAccount{Balance: big.NewInt(133700000)}
	sim := backends.NewSimulatedBackend(alloc)

	addr, tx, contract, err := DeployHelloWorld(auth, sim)
	if err != nil {
		log.Fatalf("could not deploy contract: %v", err)
	}
	fmt.Printf("Address: %s\n", addr)
	fmt.Printf("Tx: %s\n", tx.Value())
	fmt.Printf("Contract: %s\n", contract)

	result, _ := contract.HelloWorldTransactor.RenderHelloWorld(auth)
	fmt.Printf("\n Result: %s\n", common.Bytes2Hex(result.Data()))
	sim.Commit()

}
