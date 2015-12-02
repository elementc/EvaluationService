app.factory('URLFactory', [function(){
    var factory = {};
    var baseURL = "http://" + window.location.hostname +":48484/evaluationsapi/";
    
    factory.getUsersURL = function(){
        return baseURL + "users/";
    };
    
    factory.getUserURL = function(id){
        return factory.getUsersURL() + id + '/';
    };

    factory.getResetPasswordURL = function(email){
        return factory.getUsersURL() + 'resetpassword/' + email;
    };
    
    factory.getCoursesURL = function(){
        return baseURL + "courses/";
    };
    
    factory.getCourseURL = function(id){
        return factory.getCoursesURL() + id + '/';
    };
    
    factory.getGroupsURL = function(){
        return baseURL + "groups/";
    };
    
    factory.getGroupURL = function(id){
        return factory.getGroupsURL() + id + '/';
    };
    factory.getGroupMembersURL = function(){
        return baseURL + "group_members/";
    };
    
    factory.getGroupMemberURL = function(id){
        return factory.getGroupMembersURL() + id + '/';
    };
    
    factory.getEvaluationStagesURL = function(){
        return baseURL + "evaluation_stages/";
    };
    
    factory.getEvaluationStageURL = function(id){
        return factory.getEvaluationStagesURL() + id + '/';
    };
    
    factory.getMemberEvaluationsURL = function(){
        return baseURL + "member_evaluations/";
    };
    
    factory.getMemberEvaluationURL = function(id){
        return factory.getMemberEvaluationsURL() + id + '/';
    };
    
    factory.getGroupEvaluationsURL = function(){
        return baseURL + "group_evaluations/";
    };
    
    factory.getGroupEvaluationURL = function(id){
        return factory.getGroupEvaluationsURL() + id + '/';
    };
    
    factory.getAuthURL = function(){
        return baseURL + "auth/";
    };
    return factory;
}]);