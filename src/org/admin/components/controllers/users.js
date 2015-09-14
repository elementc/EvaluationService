app.controller('UsersController', ['$scope', '$http', '$location', 'URLFactory',
   function ($scope, $http, $location, URLFactory) {
       $scope.users = [];
       
       $http.get(URLFactory.getUsersURL()).success(function(users){
            $scope.users = users;
        });
       
       $scope.go = function(path){
            $location.path( 'user/' + path);
        };
}]);