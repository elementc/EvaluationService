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
    }).when('/signup',
    {
        templateUrl: 'components/views/signup.html',
        controller: 'SignupController'
    }).when('/user',
    {
        templateUrl: 'components/views/user.html',
        controller: 'UserController'
    }).otherwise(
    {
            redirectTo: '/'
    });
}]);
