app.controller('GroupController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.groupID = $routeParams.id;
       $scope.isNew = $scope.groupID === undefined;
       
       $http.get(URLFactory.getCoursesURL()).success(function(courses){
            $scope.courses = courses;   
           if(!$scope.isNew){
               $http.get(URLFactory.getGroupURL($scope.groupID)).success(function(group){
                   $scope.group = group;
               }).error(function(){
                   $scope.group = {};
                   $scope.isNew = true;
                   $scope.group.id = 0;
                   $scope.group.created_on = null;
               });
           }else{
               $scope.group = {};
               $scope.group.id = 0;
               $scope.group.created_on = null;
           }
        });
       
       
              
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getGroupsURL(), $scope.group).success(function(){
                   showToast('Group created successfully!'); 
                   $location.path('groups');
               });
           }else{
                $http.put(URLFactory.getGroupsURL(), $scope.group).success(function(){
                     showToast('Group updated successfully!');
                     $location.path('groups');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getGroupURL($scope.group.id)).success(function(){
                 showToast('Group deleted successfully!');
                 $location.path('groups');
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