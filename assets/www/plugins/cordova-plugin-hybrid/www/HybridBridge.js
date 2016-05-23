cordova.define("cordova-plugin-hybrid.HybridBridge", function(require, exports, module) {
var exec = require('cordova/exec'),
    cordova = require('cordova');

function HybridBridge() {

}
HybridBridge.prototype.showList = function(urls, showTip, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "HybridBridge", "showList", [urls, showTip]);
};

module.exports = new HybridBridge();



});
