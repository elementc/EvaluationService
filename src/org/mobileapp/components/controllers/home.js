/**
 * Created by anitadevi on 9/10/15.
 */

app.controller('HomeController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {

        $scope.groups = [{name: 'Group-A', id: 1}, {name: 'Group-B', id: 2}, {name: 'Group-C', id: 3}];


        $scope.phases = [{name: 'Phase-1', id: 1}, {name: 'Phase-2', id: 2}, {name: 'Phase-3', id: 3}];
        $scope.submit = function () {
            $location.path( 'evaluation/');

        };

        $scope.changeGroup= function() {
            $scope.isGroupSelected="yes"
        }

        $scope.myPathVariable = 'group/';
    }]);



