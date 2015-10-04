app.controller('EvaluationController', ['$rootScope', '$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($rootScope, $scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.members = [{name: 'Member-A', id: 1}, {name: 'Member-B', id: 2}, {name: 'Member-C', id: 3}];

        if($rootScope.stage == undefined || $rootScope.stage == null || $rootScope.group == undefined || $rootScope.group == null){
            $location.path("/home")
        }

        $scope.isShown = function(task) {
            return task === $scope.task;
        };
        $scope.isShownParticipation = function(participation) {
            return participation === $scope.participation;
        };

        $scope.changeMember = function() {
            $scope.isMemberSelected="yes"
        };

        $scope.save = function () {
            $location.path( 'evaluation/');

        };

        var showToast = function(content) {
            var toast = $mdToast.simple()
                .content(content)
                .action('OK')
                .highlightAction(false)
                .position('top right');
            $mdToast.show(toast);
     };
    }]);