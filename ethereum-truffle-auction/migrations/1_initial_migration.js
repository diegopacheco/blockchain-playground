var Migrations = artifacts.require("./Migrations.sol");
var Audiction  = artifacts.require("./Audiction.sol");

module.exports = function(deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(Audiction);
};
