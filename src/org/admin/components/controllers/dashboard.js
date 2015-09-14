app.controller('DashboardController', ['$scope', '$http', '$mdToast', '$location', 'URLFactory', function($scope, $http, $mdToast, $location, URLFactory){
    $scope.authForm = {};
    $scope.login = function(){
        $http.post(URLFactory.getAuthURL(), $scope.authForm).success(function(user){
            showToast('User logged in successfully!');
            $location.path( 'user/' + user.id);
        }).error(function(){
            showToast('Login failed!');
            $scope.authForm = {};
        });
        
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
