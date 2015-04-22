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


       window.loadGroupData = function(id) {
           if(id === undefined || id == 0){
               return;
           }
           var data = [
                "Arsenic",
                "Desensitizing Titanium",
                "Code Lemurs",
                "Team Hated",
                "Slaking",
                "Jiggle My Puff",
                "Caterpie",
                "Thunder Chickens",
                "Gekkenhuis",
                "Anonymous",
                "Null_Pointer",
                "Team Goto",
                "Team ComeFrom",
                "Mewtwo",
                "Anything Will Be FINE",
                "Droids"
           ];

           for(var i = 0; i < data.length; i++){
               var g = data[i];
               var group = {};
               group.id = 0;
               group.created_on = null;
               group.course_id = id;
               group.name = g;
               $http.post(URLFactory.getGroupsURL(), group).success(function(){});
           }
           return true;
       };
}]);