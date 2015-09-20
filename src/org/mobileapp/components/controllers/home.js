app.controller('HomeController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {

        $scope.groups = [];

        $http.get(URLFactory.getGroupsURL()).success(function(groups){
            $scope.groups = groups;
        }).error(function(){
            showToast('Error occurred while getting list of groups!');
        });


        $scope.phases = []; //[{name: 'Phase-1', id: 1}, {name: 'Phase-2', id: 2}, {name: 'Phase-3', id: 3}];
        $scope.submit = function () {
            //$location.path( 'evaluation/');

        };

        $scope.changeGroup= function() {
            $scope.isGroupSelected="yes"
        }

        $scope.myPathVariable = 'group/';
    }]);



