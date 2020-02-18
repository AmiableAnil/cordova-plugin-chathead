var exec = require('cordova/exec');

var PLUGIN_NAME = 'chathead';

var chathead = {

    showChatHead: function(identifier, did, profileId, studentId, stallId, ideaId, sid, success, error) {
        exec(success, error, PLUGIN_NAME, "showChatHead", ["showChatHead",identifier, did, profileId, studentId, stallId, ideaId, sid]);
    }
};


module.exports = chathead;
