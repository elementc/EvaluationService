app.controller('GroupEvaluationController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.groupEvaluationID = $routeParams.id;
       $scope.isNew = $scope.groupMemberID === undefined;
       
       $http.get(URLFactory.getGroupEvaluationURL($scope.groupEvaluationID)).success(function(groupEvaluation){
           $scope.groupEvaluation = groupEvaluation;
           
           $http.get(URLFactory.getEvaluationStageURL($scope.groupEvaluation.evaluation_stage_id)).success(function(evaluationStage){
               $scope.evaluationStage = evaluationStage;
            });
           
           $http.get(URLFactory.getUserURL($scope.groupEvaluation.evaluator_id)).success(function(evaluator){
               $scope.evaluator = evaluator;

            });
           
           
           $http.get(URLFactory.getGroupURL($scope.groupEvaluation.group_id)).success(function(group){
               $scope.group = group;
            });
        });
       
       
               
       $scope.delete = function(){
            $http.delete(URLFactory.getGroupEvaluationURL($scope.groupEvaluationID)).success(function(){
                 showToast('Group Evaluation deleted successfully!');
                 $location.path('group_evaluations');
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