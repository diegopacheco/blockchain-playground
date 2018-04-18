var Migrations = artifacts.require("./Migrations.sol");
var ExampleToken = artifacts.require("./ExampleToken.sol");
var StorageCon   = artifacts.require("./Storage.sol");
module.exports = function(deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(ExampleToken);
  deployer.deploy(StorageCon);
};
