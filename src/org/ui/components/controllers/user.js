app.controller('UserController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.userID = $routeParams.id;
       $scope.user = {};

       $http.get(URLFactory.getUserURL()).success(function(user){
           $scope.user = user;
       }).error(function(){
           $scope.isNew = true;
           $scope.user.id = 0;
           $scope.user.created_on = null;
       });


       $scope.save = function(){
           $http.post(URLFactory.getUserURL(), $scope.user).success(function(){
               showToast('User updated successfully!');
               $location.path('user');
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