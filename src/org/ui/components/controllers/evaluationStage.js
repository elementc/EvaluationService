app.controller('EvaluationStageController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.evaluationStageID = $routeParams.id;
       $scope.isNew = $scope.evaluationStageID === undefined;
       
       $http.get(URLFactory.getCoursesURL()).success(function(courses){
            $scope.courses = courses;   
           if(!$scope.isNew){
               $http.get(URLFactory.getEvaluationStageURL($scope.evaluationStageID)).success(function(evaluationStage){
                   $scope.evaluationStage = evaluationStage;
                   $scope.evaluationStage.start_date = new Date($scope.evaluationStage.start_date);
                   $scope.evaluationStage.end_date = new Date($scope.evaluationStage.end_date);
               }).error(function(){
                   $scope.evaluationStage = {};
                   $scope.isNew = true;
                   $scope.evaluationStage.id = 0;
                   $scope.evaluationStage.created_on = null;
               });
           }else{
               $scope.evaluationStage = {};
               $scope.evaluationStage.id = 0;
               $scope.evaluationStage.created_on = null;
           }
        });
       
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getEvaluationStagesURL(), $scope.evaluationStage).success(function(){
                   showToast('EvaluationStage created successfully!'); 
                   $location.path('evaluation_stages');
               });
           }else{
                $http.put(URLFactory.getEvaluationStagesURL(), $scope.evaluationStage).success(function(){
                     showToast('EvaluationStage updated successfully!');
                     $location.path('evaluation_stages');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getEvaluationStageURL($scope.evaluationStage.id)).success(function(){
                 showToast('EvaluationStage deleted successfully!');
                 $location.path('evaluation_stages');
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