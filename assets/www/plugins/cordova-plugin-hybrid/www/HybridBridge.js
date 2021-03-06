cordova.define("cordova-plugin-hybrid.HybridBridge", function(require, exports, module) {
var exec = require('cordova/exec'),
    cordova = require('cordova');

function HybridBridge() {

}

HybridBridge.prototype.showList = function(urls, showTip, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "HybridBridge", "showList", [urls, showTip]);
};

HybridBridge.prototype.showAlert = function(title, desc, clean, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "HybridBridge", "showAlert", [title, desc, clean]);
};

module.exports = new HybridBridge();



});
