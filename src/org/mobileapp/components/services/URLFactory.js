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

    factory.getGroupsURL = function(){
        return factory.getMobileServiceURL() + "groups/";
    };

    factory.getCoursesURL = function(){
        return factory.getMobileServiceURL() + "courses/";
    };

    factory.getAllCoursesURL = function(){
        return factory.getMobileServiceURL() + "allcourses/";
    };

    factory.getCourseURL = function(id){
        return factory.getCoursesURL() + id + '/';
    };

    factory.getGroupsURL = function(){
        return factory.getMobileServiceURL() + "groups/";
    };

    factory.getGroupsByCourseURL = function(id){
        return factory.getGroupsURL() + "bycourseid/" + id + "/";
    };

    return factory;
}]);