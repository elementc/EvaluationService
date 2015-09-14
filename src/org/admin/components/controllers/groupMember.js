app.controller('GroupMemberController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.groupMemberID = $routeParams.id;
       $scope.isNew = $scope.groupMemberID === undefined;
       
       $http.get(URLFactory.getUsersURL()).success(function(users){
           $scope.users = users;  
           
           $http.get(URLFactory.getGroupsURL()).success(function(groups){
               $scope.groups = groups;  
               if(!$scope.isNew){
                   $http.get(URLFactory.getGroupMemberURL($scope.groupMemberID)).success(function(groupMember){
                       $scope.groupMember = groupMember;
                   }).error(function(){
                       $scope.groupMember = {};
                       $scope.isNew = true;
                       $scope.groupMember.id = 0;
                   });
               }else{
                   $scope.groupMember = {};
                   $scope.groupMember.id = 0;
               }
           });
        });
       
       
              
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getGroupMembersURL(), $scope.groupMember).success(function(){
                   showToast('GroupMember created successfully!'); 
                   $location.path('group_members');
               });
           }else{
                $http.put(URLFactory.getGroupMembersURL(), $scope.groupMember).success(function(){
                     showToast('GroupMember updated successfully!');
                     $location.path('group_members');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getGroupMemberURL($scope.groupMember.id)).success(function(){
                 showToast('GroupMember deleted successfully!');
                 $location.path('group_members');
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