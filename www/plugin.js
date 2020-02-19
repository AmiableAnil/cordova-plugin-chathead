var exec = require('cordova/exec');

var PLUGIN_NAME = 'chathead';

var chathead = {

    showChatHead: function(identifier, did, profileId, studentId, stallId, ideaId, sid, type, success, error) {
        exec(success, error, PLUGIN_NAME, "showChatHead", ["showChatHead",identifier, did, profileId, studentId, stallId, ideaId, sid, type]);
    }
};


module.exports = chathead;
