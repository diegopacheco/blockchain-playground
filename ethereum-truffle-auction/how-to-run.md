## Console 1
```bash
npm install -g ethereumjs-testrpc
testrpc
```  

## Console 2
```bash
npm install -g truffle
npm install
truffle compile
truffle migrate
truffle deploy --reset
truffle console
```
On Truffle Console...
```js
Audiction.deployed()
Audiction.deployed().then(instance => instance.startBid("PS4",967))
Audiction.deployed().then(instance => instance.getBidItem.call())
Audiction.deployed().then(instance => instance.getBidInitialPrice.call())

Audiction.deployed().then(instance => instance.placeBid("PS4",2000))
Audiction.deployed().then(instance => instance.finishBid("PS4"))
Audiction.deployed().then(instance => instance.getBestValue.call())
```
