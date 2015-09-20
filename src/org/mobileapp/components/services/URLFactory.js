app.factory('URLFactory', [function(){
    var factory = {};
    //var baseURL = "http://" + window.location.host +"/evaluationsapi/";
    var baseURL = "http://localhost:48484/evaluationsapi/";

    factory.getMobileServiceURL = function(){
        return baseURL + "mobile_service/"
    };

    factory.getAuthURL = function(){
        return baseURL + "auth/";
    };

    factory.getUserURL = function(){
        return factory.getMobileServiceURL() + "user/"
    };

    factory.getUserSignupURL = function(){
        return factory.getUserURL() + "signup/"
    };

    return factory;
}]);