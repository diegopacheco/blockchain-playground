pragma solidity ^0.4.0;

contract Deliverables {
  
  address public owner;
  mapping (address => Deliverable) deliverables;

  struct Deliverable {
    address id;
    uint status;
  }

  modifier onlyByOwner() {
    if (msg.sender != owner) revert();
    _;
  }

  function Deliverables() public {
    owner = msg.sender;
  }

  function store(address id) onlyByOwner public {
    deliverables[id] = Deliverable(id, 0);
  }

  function statusFor(address id) public constant returns(uint status) {
    return deliverables[id].status;
  }

  function delivered(address id) public onlyByOwner {
    deliverables[id].status = 1;
  }
}
