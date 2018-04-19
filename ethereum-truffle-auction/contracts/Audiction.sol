pragma solidity ^0.4.8;

contract Audiction {

    struct Bid {
        string item;
        uint initialPrice;
    }

    Bid bid;
    address public beneficiary;
    address public bestBid;
    uint public bestValue;
    bool public ended;
    mapping(address => Bid[]) public bids;

    function Audiction() public {
      beneficiary = msg.sender;
      bid = Bid("",0);
      ended = false;
    }

    function startBid(string auctionItem,uint initialPrice) public {
      beneficiary = msg.sender;
      bid = Bid(auctionItem,initialPrice);
      ended = false;
    }

    function placeBid(string auctionItem,uint bidValue) public {
      if (compareStrings(bid.item,auctionItem)){
          address bidder = msg.sender;
          Audiction a = Audiction(bidder);
          uint result = a.getBestValue();
          if (bidValue > result){
               bestBid = bidder;
               bestValue = bidValue;
          }
      }
    }

    function finishBid(string auctionItem) public returns (address){
      if (compareStrings(bid.item,auctionItem)){
        ended = true;
        return bestBid;
      }else{
        revert();
      }
    }

    function getBidItem() public returns (string){
       return bid.item;
    }

    function getBidInitialPrice() public returns (uint){
       return bid.initialPrice;
    }

    function getBestValue() public returns (uint){
        return bestValue;
    }

    function compareStrings(string a, string b) private returns (bool){
       return keccak256(a) == keccak256(b);
    }

}
