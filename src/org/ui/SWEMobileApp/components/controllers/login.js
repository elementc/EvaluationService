app.controller('LoginController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
    $scope.login  = function(isValid) {
        var email=$scope.email;
        var password=$scope.password;
        if (isValid) {
            $location.path( 'home/');

        }

    };
}]);