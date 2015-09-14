app.controller('EvaluationStagesController', ['$scope', '$http', '$location', 'URLFactory',
   function ($scope, $http, $location, URLFactory) {
       
       $http.get(URLFactory.getCoursesURL()).success(function(courses){
            $scope.courses = courses;
            $http.get(URLFactory.getEvaluationStagesURL()).success(function(evaluationStages){
                $scope.evaluationStages = evaluationStages;
            });
       });
       
       $scope.findCourse = function(id){
           return $scope.courses.filter(
              function(data){return (data.id == id)}
           );
       }

       $scope.go = function(path){
            $location.path( 'evaluation_stage/' + path);
        };
}]);
