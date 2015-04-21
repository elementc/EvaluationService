app.controller('UserController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.userID = $routeParams.id;
       $scope.user = {};
       $scope.isNew = $scope.userID === undefined;
       
       if(!$scope.isNew){
           $http.get(URLFactory.getUserURL($scope.userID)).success(function(user){
                $scope.user = user;
            }).error(function(){
               $scope.isNew = true;
               $scope.user.id = 0;
               $scope.user.created_on = null;
            });
       }else{
           $scope.user.id = 0;
           $scope.user.created_on = null;
       }
       
       
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getUsersURL(), $scope.user).success(function(){
                   showToast('User created successfully!'); 
                   $location.path('users');
               });
           }else{
                $http.put(URLFactory.getUsersURL(), $scope.user).success(function(){
                     showToast('User updated successfully!');
                     $location.path('users');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getUserURL($scope.user.id)).success(function(){
                 showToast('User deleted successfully!');
                 $location.path('users');
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