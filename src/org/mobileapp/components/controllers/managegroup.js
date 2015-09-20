
app.controller('GroupController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
    function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
        $scope.courses = [];

        $scope.groups = [];

        $http.get(URLFactory.getGroupsURL()).success(function(groups){
            $scope.groups = groups;
            for (var i = 0; i < $scope.groups.length; i++){
                var group = $scope.groups[i];
                $http.get(URLFactory.getCourseURL(group.course_id)).success(function(course) {
                    group.course = course;
                });
            }
        }).error(function(){
            showToast('Error occurred while getting list of groups!');
        });

        $scope.dbgroups = [{course: 'SWE-5001', group: 'Group-A'}, {course: 'SWE-5002', group: 'Group-B'}, {course: 'Testing-1', group:'Group-C'}];


        $http.get(URLFactory.getAllCoursesURL()).success(function(courses) {
            $scope.courses = courses;
        });


        $scope.subscribe = function () {
            $location.path( 'home/');

        };
        $scope.changeCourse = function() {
            $scope.isCourseSelected="yes"
        }
        $scope.create = function () {
            $location.path( 'home/');

        };


    }]);



