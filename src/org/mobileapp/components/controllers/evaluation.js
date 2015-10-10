app.controller('EvaluationController', ['$rootScope', '$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($rootScope, $scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.members = [{name: 'Member-A', id: 1}, {name: 'Member-B', id: 2}, {name: 'Member-C', id: 3}];

        $scope.self = {};
        $scope.member = {};
        $scope.groupEvaluation = {};
        $scope.selected = {};

        if($rootScope.stage == undefined || $rootScope.stage == null || $rootScope.group == undefined || $rootScope.group == null){
            $location.path("/home");
            return;
        }

        $http.get(URLFactory.getGroupMembersURL($rootScope.group.id)).success(function(e){
            $scope.groupMembers = e;
        }).error(function(e){
            if(e !== null && e.error !== undefined){
                showToast(e.error);
            }else{
                showToast('Internal Server Error. Contact Administrator!');
            }
        });

        if($rootScope.user == undefined || $scope.user == null){
            $http.get(URLFactory.getUserURL()).success(function(e){
                $rootScope.user = e;
            });
        }

        $scope.isShown = function(task) {
            return task === $scope.task;
        };
        $scope.isShownParticipation = function(participation) {
            return participation === $scope.participation;
        };

        $scope.changeMember = function() {
            $scope.isMemberSelected="yes"
        };

        $scope.save = function () {
            $location.path( 'evaluation/');

        };

        $scope.submitSelf = function(){
            $scope.self.evaluator_id = $rootScope.user.id;
            $scope.self.evaluatee_id = $rootScope.user.id;
            $scope.self.evaluation_stage_id = $rootScope.stage.id;
            $scope.self.groups_id = $rootScope.group.id;
            $http.post(URLFactory.getMemberEvaluationsURL(), $scope.self).success(function(e){
                $scope.self = {};
                showToast("Evaluation Submitted Successfully!");
            }).error(function(e){
                if(e !== null && e.error !== undefined){
                    showToast(e.error);
                }else{
                    showToast('Internal Server Error. Contact Administrator!');
                }
            });
        };

        $scope.submitMember = function(){
            $scope.member.evaluator_id = $rootScope.user.id;
            $scope.member.evaluatee_id = $scope.selected.groupMember.id;
            $scope.member.evaluation_stage_id = $rootScope.stage.id;
            $scope.member.groups_id = $rootScope.group.id;
            $http.post(URLFactory.getMemberEvaluationsURL(), $scope.member).success(function(e){
                $scope.member = {};
                showToast("Evaluation Submitted Successfully!");
            }).error(function(e){
                if(e !== null && e.error !== undefined){
                    showToast(e.error);
                }else{
                    showToast('Internal Server Error. Contact Administrator!');
                }
            });
        };

        $scope.submitGroup = function(){
            $scope.groupEvaluation.evaluator_id = $rootScope.user.id;
            $scope.groupEvaluation.evaluation_stage_id = $rootScope.stage.id;
            $scope.groupEvaluation.group_id = $rootScope.group.id;
            $http.post(URLFactory.getGroupEvaluationsURL(), $scope.groupEvaluation).success(function(e){
                $scope.groupEvaluation = {};
                showToast("Evaluation Submitted Successfully!");
            }).error(function(e){
                if(e !== null && e.error !== undefined){
                    showToast(e.error);
                }else{
                    showToast('Internal Server Error. Contact Administrator!');
                }
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