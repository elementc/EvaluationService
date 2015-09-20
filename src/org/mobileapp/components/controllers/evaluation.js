/**
 * Created by anitadevi on 9/10/15.
 */

app.controller('SEController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.members = [{name: 'Member-A', id: 1}, {name: 'Member-B', id: 2}, {name: 'Member-C', id: 3}];

        $scope.isShown = function(task) {
            return task === $scope.task;
        };
        $scope.isShownParticipation = function(participation) {
            return participation === $scope.participation;
        };

        $scope.changeMember = function() {
            $scope.isMemberSelected="yes"
        }

        $scope.save = function () {
            $location.path( 'evaluation/');

        };


    }]);