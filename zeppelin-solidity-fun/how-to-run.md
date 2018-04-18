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
Storage.deployed()
Storage.deployed().then(instance => instance.get.call()).then(result => storeData = result)
Storage.deployed().then(instance => instance.set.sendTransaction(42)).then(result => newStorageData = result)
Storage.deployed().then(instance => instance.get.call()).then(result => storeData = result)
```
```js
ExampleToken.deployed()
var myToken = ExampleToken.deployed();
myToken.then(instance => instance.getTotalSupply.call()).then(result => result)
```
