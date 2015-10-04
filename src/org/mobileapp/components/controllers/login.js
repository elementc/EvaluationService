app.controller('LoginController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {

    $scope.signup = function(){
        $location.path( 'signup/');
    };

    $scope.go = function(path){
        $location.path(path);
    };

    $scope.login = function(){
        $http.post(URLFactory.getAuthURL(), $scope.loginform).success(function(){
            showToast('User logged in successfully!');
            $location.path('home/');
        }).error(function(e){
            if(e !== null && e.error !== undefined){
                showToast(e.error);
            }else{
                showToast('Internal Server Error. Contact Administrator!');
            }
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
}]);