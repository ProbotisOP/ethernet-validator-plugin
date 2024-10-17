var exec = require('cordova/exec');

var EthernetValidator = {
    validateEthernet: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'EthernetValidator', 'validateEthernet', []);
    }
};

module.exports = EthernetValidator;
