var app = angular.module('Evaluations', ['ngMaterial' , 'ngRoute']);

app.config(['$routeProvider' , '$locationProvider', '$httpProvider', '$mdThemingProvider', function($routeProvider , $locationProvider, $httpProvider, $mdThemingProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push(function ($q, $location) {
             return {
                 request: function (config) {
                     return config || $q.when(config);
                 },
                 responseError: function (response) {
                     if (response.status === 403) {
                         $location.path('/');
                     }
                     return $q.reject(response);
                 }
             };
         });

    $mdThemingProvider.theme('default').primaryPalette('teal').accentPalette('blue');


	   //$locationProvider.html5Mode(true);
    //$locationProvider.hashPrefix('!');

    $routeProvider.when('/',
    {
        templateUrl: 'components/views/dashboard.html',
        controller: 'DashboardController'
    }).when('/users',
    {
        templateUrl: 'components/views/users.html',
        controller: 'UsersController'
    }).when('/user/:id?',
    {
        templateUrl: 'components/views/user.html',
        controller: 'UserController'
    }).when('/courses',
    {
        templateUrl: 'components/views/courses.html',
        controller: 'CoursesController'
    }).when('/course/:id?',
    {
        templateUrl: 'components/views/course.html',
        controller: 'CourseController'
    }).when('/groups',
    {
        templateUrl: 'components/views/groups.html',
        controller: 'GroupsController'
    }).when('/group/:id?',
    {
        templateUrl: 'components/views/group.html',
        controller: 'GroupController'
    }).when('/group_members',
    {
        templateUrl: 'components/views/groupMembers.html',
        controller: 'GroupMembersController'
    }).when('/group_member/:id?',
    {
        templateUrl: 'components/views/groupMember.html',
        controller: 'GroupMemberController'
    }).when('/evaluation_stages',
    {
        templateUrl: 'components/views/evaluationStages.html',
        controller: 'EvaluationStagesController'
    }).when('/evaluation_stage/:id?',
    {
        templateUrl: 'components/views/evaluationStage.html',
        controller: 'EvaluationStageController'
    }).when('/member_evaluations',
    {
        templateUrl: 'components/views/memberEvaluations.html',
        controller: 'MemberEvaluationsController'
    }).when('/member_evaluation/:id',
    {
        templateUrl: 'components/views/memberEvaluation.html',
        controller: 'MemberEvaluationController'
    }).when('/group_evaluations',
    {
        templateUrl: 'components/views/groupEvaluations.html',
        controller: 'GroupEvaluationsController'
    }).when('/group_evaluation/:id',
    {
        templateUrl: 'components/views/groupEvaluation.html',
        controller: 'GroupEvaluationController'
    }).otherwise(
    {
            redirectTo: '/'
    });
}]);
