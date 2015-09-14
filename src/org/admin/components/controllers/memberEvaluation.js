app.controller('MemberEvaluationController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.memberEvaluationID = $routeParams.id;
       $scope.isNew = $scope.groupMemberID === undefined;
       
       $http.get(URLFactory.getMemberEvaluationURL($scope.memberEvaluationID)).success(function(memberEvaluation){
           $scope.memberEvaluation = memberEvaluation;
           
           $http.get(URLFactory.getEvaluationStageURL($scope.memberEvaluation.evaluation_stage_id)).success(function(evaluationStage){
               $scope.evaluationStage = evaluationStage;
            });
           
           $http.get(URLFactory.getUserURL($scope.memberEvaluation.evaluator_id)).success(function(evaluator){
               $scope.evaluator = evaluator;
            });
           
           $http.get(URLFactory.getUserURL($scope.memberEvaluation.evaluatee_id)).success(function(evaluatee){
               $scope.evaluatee = evaluatee;
            });
           
           $http.get(URLFactory.getGroupURL($scope.memberEvaluation.groups_id)).success(function(group){
               $scope.group = group;
            });
        });
       
       
               
       $scope.delete = function(){
            $http.delete(URLFactory.getMemberEvaluationURL($scope.memberEvaluationID)).success(function(){
                 showToast('Member Evaluation deleted successfully!');
                 $location.path('member_evaluations');
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