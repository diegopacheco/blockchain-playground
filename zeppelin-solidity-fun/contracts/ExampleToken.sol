pragma solidity ^0.4.11;
import "zeppelin-solidity/contracts/token/ERC20/StandardToken.sol";

contract ExampleToken is StandardToken {
  string public name = "ExampleToken";
  string public symbol = "EGT";
  uint public decimals = 18;
  uint public INITIAL_SUPPLY = 10000 * (10 ** decimals);
  uint public totalSupply = 0;

  function ExampleToken() {
    totalSupply = INITIAL_SUPPLY;
    balances[msg.sender] = INITIAL_SUPPLY;
  }
  function getTotalSupply() public constant returns (uint) {
      return totalSupply;
  }
}
