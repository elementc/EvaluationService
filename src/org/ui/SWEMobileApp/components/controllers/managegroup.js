/**
 * Created by anitadevi on 9/10/15.
 */

app.controller('GroupController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.courses = [{name: 'SWE-5001', id: 1}, {name: 'SWE-5002', id: 2}, {name: 'Testing-1', id: 3}];

        $scope.groups = [{name: 'Group-A', id: 1}, {name: 'Group-B', id: 2}, {name: 'Group-C', id: 3}];

        $scope.dbgroups = [{course: 'SWE-5001', group: 'Group-A'}, {course: 'SWE-5002', group: 'Group-B'}, {course: 'Testing-1', group:'Group-C'}];



        $scope.subscribe = function () {
            $location.path( 'home/');

        };
        $scope.changeCourse = function() {
            $scope.isCourseSelected="yes"
        }
        $scope.create = function () {
            $location.path( 'home/');

        };


    }]);



