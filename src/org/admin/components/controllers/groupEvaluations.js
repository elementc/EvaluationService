app.controller('GroupEvaluationsController', ['$scope', '$http', '$location', 'URLFactory',
  function ($scope, $http, $location, URLFactory) {

    $http.get(URLFactory.getGroupsURL()).success(function(groups){
        $scope.groups = groups;

        $http.get(URLFactory.getUsersURL()).success(function(users){
            $scope.users = users;
             $http.get(URLFactory.getGroupMembersURL()).success(function(groupMembers){
                $scope.groupMembers = groupMembers;
                $http.get(URLFactory.getGroupEvaluationsURL()).success(function(groupEvaluations){
                    $scope.groupEvaluations = groupEvaluations;
                });
            });
        });
    });

    $scope.findUser = function(id){
       return $scope.users.filter(
          function(data){return (data.id == id)}
       );
    };

    $scope.findGroup = function(id){
       return $scope.groups.filter(
          function(data){return (data.id == id)}
       );
    }


    $http.get(URLFactory.getGroupMembersURL()).success(function(groupMembers){
        $scope.groupMembers = groupMembers;
    });

    $scope.go = function(path){
        $location.path( 'group_evaluation/' + path);
    };
}]);
