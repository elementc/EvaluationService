app.controller('CourseController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.courseID = $routeParams.id;
       $scope.course = {};
       $scope.isNew = $scope.courseID === undefined;
       
       if(!$scope.isNew){
           $http.get(URLFactory.getCourseURL($scope.courseID)).success(function(course){
                $scope.course = course;
            }).error(function(){
               $scope.isNew = true;
               $scope.course.id = 0;
               $scope.course.created_on = null;
            });
       }else{
            
           $scope.course.id = 0;
           $scope.course.created_on = null;
       }
       
       
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getCoursesURL(), $scope.course).success(function(){
                   showToast('Course created successfully!'); 
                   $location.path('courses');
               });
           }else{
                $http.put(URLFactory.getCoursesURL(), $scope.course).success(function(){
                     showToast('Course updated successfully!');
                     $location.path('courses');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getCourseURL($scope.course.id)).success(function(){
                 showToast('Course deleted successfully!');
                 $location.path('courses');
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