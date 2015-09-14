app.controller('CoursesController', ['$scope', '$http', '$location', 'URLFactory',
   function ($scope, $http, $location, URLFactory) {
      $scope.courses = [];
       
       $http.get(URLFactory.getCoursesURL()).success(function(courses){
            $scope.courses = courses;
        });
       
       $scope.go = function(path){
            $location.path( 'course/' + path);
        };
       
       
}]);