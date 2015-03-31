var sur = sur || {};

sur.baseUrl = null;
sur.url = function(path) {
    return sur.baseUrl + path;
};