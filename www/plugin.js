var exec = require('cordova/exec');

var PLUGIN_NAME = 'chathead';

var chathead = {

    showChatHead: function(success, error) {
        exec(success, error, PLUGIN_NAME, "showChatHead", ["showChatHead"]);
    }
};


module.exports = chathead;
