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
Audiction.deployed().then(instance => instance.startBid.call("PS4",367).then())
Audiction.deployed().then(instance => instance.getBidItem.call().then())
Audiction.deployed().then(instance => instance.getBidInitialPrice.call().then())

Audiction.deployed().then(instance => instance.placeBid.call("PS4",2000).then())
Audiction.deployed().then(instance => instance.finishBid.call("PS4").then())
```
