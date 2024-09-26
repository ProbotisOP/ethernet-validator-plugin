var exec = require('cordova/exec');

var EthValidator = {
    startNetworkMonitoring: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'EthValidator', 'startNetworkMonitoring', []);
    }
};

module.exports = EthValidator;