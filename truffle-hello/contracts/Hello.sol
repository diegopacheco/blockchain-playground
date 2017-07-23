  pragma solidity ^0.4.2;

contract Hello{
    uint public balance;

    function Hello(){
        balance = 1000;
    }

    function deposit(uint _value) returns (uint _newValue){
        balance += _value;
        return balance;
    }
}
