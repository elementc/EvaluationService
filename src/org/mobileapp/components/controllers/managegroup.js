
app.controller('GroupController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.courses = [];
        //$scope.coursegroups = [];
        $scope.groups = [];

        $http.get(URLFactory.getGroupsURL()).success(function(groups){
            $scope.groups = groups;
            for (var i = 0; i < $scope.groups.length; i++){
                addCourse($scope.groups[i]);

            }
        }).error(function(){
            showToast('Error occurred while getting list of groups!');
        });

        var addCourse = function(group){
            $http.get(URLFactory.getCourseURL(group.course_id)).success(function(course) {
                group.course = course;
            });
        };

        $http.get(URLFactory.getAllCoursesURL()).success(function(courses) {
            $scope.courses = courses;
        });


        $scope.subscribe = function () {
            if ($scope.selectedGroup !== null){
                $http.get(URLFactory.getGroupsSubscribeURL($scope.selectedGroup.id)).success(function(){
                    showToast('Subscribed to group!');
                    $location.path( 'home/');
                });
            }
        };

        $scope.changeCourse = function() {
            $http.get(URLFactory.getGroupsByCourseURL($scope.selectedCourse.id)).success(function(groups) {
                $scope.coursegroups = groups;
            });
        };

        $scope.create = function () {
            if ($scope.selectedCourse !== null && $scope.groupName !== "" && $scope.groupName !== undefined){
                $http.get(URLFactory.getCreateGroupURL($scope.selectedCourse.id, $scope.groupName)).success(function(){
                    $location.path( 'home/');
                });
            }
            $location.path( 'home/');

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



