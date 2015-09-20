app.controller('LoginController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
    $scope.login  = function(isValid) {

        $scope.signup = function(){
            $location.path( 'signup/');
        };

        if (isValid) {
            $location.path( 'home/');

        }

        $scope.login = function(){
            $http.post(URLFactory.getAuthURL(), $scope.loginform).success(function(){
                showToast('User logged in successfully!');
                $location.path('home/');
            }).error(function(){
                showToast('Invalid username or password!');
            });
        };

        var showToast = function(content) {
            var toast = $mdToast.simple()
                .content(content)
                .action('OK')
                .highlightAction(false)
                .position('top right');
            $mdToast.show(toast);
        };
    };
}]);