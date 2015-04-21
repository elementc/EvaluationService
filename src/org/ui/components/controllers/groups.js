app.controller('GroupsController', ['$scope', '$http', '$location', 'URLFactory',
   function ($scope, $http, $location, URLFactory) {
       $http.get(URLFactory.getCoursesURL()).success(function(courses){
            $scope.courses = courses;
            $http.get(URLFactory.getGroupsURL()).success(function(groups){
                $scope.groups = groups;
            });
       });
       
       $scope.findCourse = function(id){
           return $scope.courses.filter(
              function(data){return (data.id == id)}
           );
       }

       $scope.go = function(path){
            $location.path( 'group/' + path);
        };
}]);
