// Code generated - DO NOT EDIT.
// This file is a generated binding and any manual changes will be lost.

package main

import (
	"strings"

	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/accounts/abi/bind"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/core/types"
)

// HelloWorldABI is the input ABI used to generate the binding from.
const HelloWorldABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"renderHelloWorld\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"

// HelloWorldBin is the compiled bytecode used for deploying new contracts.
const HelloWorldBin = `0x6060604052341561000f57600080fd5b61014e8061001e6000396000f3006060604052600436106100405763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663942ae0a78114610045575b600080fd5b341561005057600080fd5b6100586100cf565b60405160208082528190810183818151815260200191508051906020019080838360005b8381101561009457808201518382015260200161007c565b50505050905090810190601f1680156100c15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100d7610110565b60408051908101604052600a81527f68656c6c6f576f726c64000000000000000000000000000000000000000000006020820152905090565b602060405190810160405260008152905600a165627a7a723058202094f6f2a473f38908ac05d59394ac9563af76f51464802b0c133fbb53a0c8b40029`

// DeployHelloWorld deploys a new Ethereum contract, binding an instance of HelloWorld to it.
func DeployHelloWorld(auth *bind.TransactOpts, backend bind.ContractBackend) (common.Address, *types.Transaction, *HelloWorld, error) {
	parsed, err := abi.JSON(strings.NewReader(HelloWorldABI))
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	address, tx, contract, err := bind.DeployContract(auth, parsed, common.FromHex(HelloWorldBin), backend)
	if err != nil {
		return common.Address{}, nil, nil, err
	}
	return address, tx, &HelloWorld{HelloWorldCaller: HelloWorldCaller{contract: contract}, HelloWorldTransactor: HelloWorldTransactor{contract: contract}}, nil
}

// HelloWorld is an auto generated Go binding around an Ethereum contract.
type HelloWorld struct {
	HelloWorldCaller     // Read-only binding to the contract
	HelloWorldTransactor // Write-only binding to the contract
}

// HelloWorldCaller is an auto generated read-only Go binding around an Ethereum contract.
type HelloWorldCaller struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// HelloWorldTransactor is an auto generated write-only Go binding around an Ethereum contract.
type HelloWorldTransactor struct {
	contract *bind.BoundContract // Generic contract wrapper for the low level calls
}

// HelloWorldSession is an auto generated Go binding around an Ethereum contract,
// with pre-set call and transact options.
type HelloWorldSession struct {
	Contract     *HelloWorld       // Generic contract binding to set the session for
	CallOpts     bind.CallOpts     // Call options to use throughout this session
	TransactOpts bind.TransactOpts // Transaction auth options to use throughout this session
}

// HelloWorldCallerSession is an auto generated read-only Go binding around an Ethereum contract,
// with pre-set call options.
type HelloWorldCallerSession struct {
	Contract *HelloWorldCaller // Generic contract caller binding to set the session for
	CallOpts bind.CallOpts     // Call options to use throughout this session
}

// HelloWorldTransactorSession is an auto generated write-only Go binding around an Ethereum contract,
// with pre-set transact options.
type HelloWorldTransactorSession struct {
	Contract     *HelloWorldTransactor // Generic contract transactor binding to set the session for
	TransactOpts bind.TransactOpts     // Transaction auth options to use throughout this session
}

// HelloWorldRaw is an auto generated low-level Go binding around an Ethereum contract.
type HelloWorldRaw struct {
	Contract *HelloWorld // Generic contract binding to access the raw methods on
}

// HelloWorldCallerRaw is an auto generated low-level read-only Go binding around an Ethereum contract.
type HelloWorldCallerRaw struct {
	Contract *HelloWorldCaller // Generic read-only contract binding to access the raw methods on
}

// HelloWorldTransactorRaw is an auto generated low-level write-only Go binding around an Ethereum contract.
type HelloWorldTransactorRaw struct {
	Contract *HelloWorldTransactor // Generic write-only contract binding to access the raw methods on
}

// NewHelloWorld creates a new instance of HelloWorld, bound to a specific deployed contract.
func NewHelloWorld(address common.Address, backend bind.ContractBackend) (*HelloWorld, error) {
	contract, err := bindHelloWorld(address, backend, backend)
	if err != nil {
		return nil, err
	}
	return &HelloWorld{HelloWorldCaller: HelloWorldCaller{contract: contract}, HelloWorldTransactor: HelloWorldTransactor{contract: contract}}, nil
}

// NewHelloWorldCaller creates a new read-only instance of HelloWorld, bound to a specific deployed contract.
func NewHelloWorldCaller(address common.Address, caller bind.ContractCaller) (*HelloWorldCaller, error) {
	contract, err := bindHelloWorld(address, caller, nil)
	if err != nil {
		return nil, err
	}
	return &HelloWorldCaller{contract: contract}, nil
}

// NewHelloWorldTransactor creates a new write-only instance of HelloWorld, bound to a specific deployed contract.
func NewHelloWorldTransactor(address common.Address, transactor bind.ContractTransactor) (*HelloWorldTransactor, error) {
	contract, err := bindHelloWorld(address, nil, transactor)
	if err != nil {
		return nil, err
	}
	return &HelloWorldTransactor{contract: contract}, nil
}

// bindHelloWorld binds a generic wrapper to an already deployed contract.
func bindHelloWorld(address common.Address, caller bind.ContractCaller, transactor bind.ContractTransactor) (*bind.BoundContract, error) {
	parsed, err := abi.JSON(strings.NewReader(HelloWorldABI))
	if err != nil {
		return nil, err
	}
	return bind.NewBoundContract(address, parsed, caller, transactor, nil), nil
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_HelloWorld *HelloWorldRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _HelloWorld.Contract.HelloWorldCaller.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_HelloWorld *HelloWorldRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _HelloWorld.Contract.HelloWorldTransactor.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_HelloWorld *HelloWorldRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _HelloWorld.Contract.HelloWorldTransactor.contract.Transact(opts, method, params...)
}

// Call invokes the (constant) contract method with params as input values and
// sets the output to result. The result type might be a single field for simple
// returns, a slice of interfaces for anonymous returns and a struct for named
// returns.
func (_HelloWorld *HelloWorldCallerRaw) Call(opts *bind.CallOpts, result interface{}, method string, params ...interface{}) error {
	return _HelloWorld.Contract.contract.Call(opts, result, method, params...)
}

// Transfer initiates a plain transaction to move funds to the contract, calling
// its default method if one is available.
func (_HelloWorld *HelloWorldTransactorRaw) Transfer(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _HelloWorld.Contract.contract.Transfer(opts)
}

// Transact invokes the (paid) contract method with params as input values.
func (_HelloWorld *HelloWorldTransactorRaw) Transact(opts *bind.TransactOpts, method string, params ...interface{}) (*types.Transaction, error) {
	return _HelloWorld.Contract.contract.Transact(opts, method, params...)
}

// RenderHelloWorld is a paid mutator transaction binding the contract method 0x942ae0a7.
//
// Solidity: function renderHelloWorld() returns(string)
func (_HelloWorld *HelloWorldTransactor) RenderHelloWorld(opts *bind.TransactOpts) (*types.Transaction, error) {
	return _HelloWorld.contract.Transact(opts, "renderHelloWorld")
}

// RenderHelloWorld is a paid mutator transaction binding the contract method 0x942ae0a7.
//
// Solidity: function renderHelloWorld() returns(string)
func (_HelloWorld *HelloWorldSession) RenderHelloWorld() (*types.Transaction, error) {
	return _HelloWorld.Contract.RenderHelloWorld(&_HelloWorld.TransactOpts)
}

// RenderHelloWorld is a paid mutator transaction binding the contract method 0x942ae0a7.
//
// Solidity: function renderHelloWorld() returns(string)
func (_HelloWorld *HelloWorldTransactorSession) RenderHelloWorld() (*types.Transaction, error) {
	return _HelloWorld.Contract.RenderHelloWorld(&_HelloWorld.TransactOpts)
}
