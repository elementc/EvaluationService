app.controller('UserController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.userID = $routeParams.id;
       $scope.user = {};
       $scope.isNew = $scope.userID === undefined;
       
       if(!$scope.isNew){
           $http.get(URLFactory.getUserURL($scope.userID)).success(function(user){
                $scope.user = user;
            }).error(function(){
               $scope.isNew = true;
               $scope.user.id = 0;
               $scope.user.created_on = null;
            });
       }else{
           $scope.user.id = 0;
           $scope.user.created_on = null;
       }
       
       
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getUsersURL(), $scope.user).success(function(){
                   showToast('User created successfully!'); 
                   $location.path('users');
               });
           }else{
                $http.put(URLFactory.getUsersURL(), $scope.user).success(function(){
                     showToast('User updated successfully!');
                     $location.path('users');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getUserURL($scope.user.id)).success(function(){
                 showToast('User deleted successfully!');
                 $location.path('users');
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


       window.loadUserData = function(){
           var data = [
           "Xavier Merino - xmerino2012@my.fit.edu",
           "Alyssa Marcoux - amarcoux2013@my.fit.edu",
           "Jocsan Macias - maciasj2013@my.fit.edu",
           "Maher Basha - mbasha2013@my.fit.edu",
           "William Nyffenegger - wnyffenegger2013@my.fit.edu",
           "Jocday Macias - jmacias2013@my.fit.edu",
           "Gurkirat Kainth - gkainth2013@my.fit.edu",
           "Dalal Alajaji - dalajaji2011@my.fit.edu",
           "Nicholas Klofta - nklofta2012@my.fit.edu",
           "Kevin Spanier - kspanier2013@my.fit.edu",
           "Yat Pang - apang2013@my.fit.edu",
           "Christopher Pangalos - cpangalos2012@my.fit.edu",
           "Abdullah Alsaraf - aalsaraf2011@my.fit.edu",
           "Hamoud AlQahtani - halqahta2007@my.fit.edu",
           "Martynas Mickus - mmickus2012@my.fit.edu",
           "Sally Tan Yu - stanyu2013@my.fit.edu",
           "Charles Petersen - cpetersen2012@my.fit.edu",
           "Dhanish Mehta - mehtad2012@my.fit.edu",
           "Michael Stratton - mstratton2012@my.fit.edu",
           "Harrison Cord - hcord2012@my.fit.edu",
           "Timothy Von Friesen - tvonfriesen2012@my.fit.edu",
           "Otto Irwin - oirwin2012@my.fit.edu",
           "Charles Endicott - cendicott2013@my.fit.edu",
           "Hosam Alsubhi - halsubhi2013@my.fit.edu",
           "Corin Lobo - clobo2012@my.fit.edu",
           "Jacob Bowers - jbowers2013@my.fit.edu",
           "Stephen Fournier - sfournier2013@my.fit.edu",
           "Joshua Selom Adadevoh - jadadevoh2008@fit.edu",
           "Abhijay Mahajan - amahajan2013@my.fit.edu",
           "Austin Haggard - ahaggard2013@my.fit.edu",
           "Ahmad Hijazi - ahijazi2012@my.fit.edu",
           "Milica Knezevic - mknezevic2013@my.fit.edu",
           "Joseph Torano - jtorano2013@my.fit.edu",
           "Tyler Petroske - tpetroske2013@my.fit.edu",
           "Mohammad Shamsah - mshamsah2011@my.fit.edu",
           "Louay El Biche - lelbiche2013@my.fit.edu",
           "Strahinja Markovic - smarkovic2013@my.fit.edu",
           "Thiago Pina Soares - tpinasoares2015@my.fit.edu",
           "Jacob Gollert - jgollert2013@my.fit.edu",
           "Nick Namba - nnamba2013@my.fit.edu",
           "Aaron Nies - anies2014@my.fit.edu",
           "Santiago Roig - sroig2013@my.fit.edu",
           "Rafael Trujillo - rtrujillo2012@my.fit.edu",
           "Oluwafunmiwo Sholola - osholola2013@my.fit.edu",
           "Nathaniel Watson - watsonn2013@my.fit.edu",
           "Nick Whitlock - nwhitlock2013@my.fit.edu",
           "Mohammed Al Habsi - malhabsi2013@my.fit.edu",
           "Michael Tishman - mtishman2013@my.fit.edu",
           "Heather Lemieux - hlemieux2011@my.fit.edu",
           "Brian Dumont - bdumont2012@my.fit.edu",
           "Gregory Moody - gmoody2013@my.fit.edu",
           "Brandon Binkley - bbinkley2010@my.fit.edu",
           "Stephan Stross - sstross2012@my.fit.edu",
           "Mariane Regina Afonso Vieira - mafonsovieir2015@my.fit.edu",
           "Abdulrazzaq Meer - ameer2011@my.fit.edu",
           "Jess Farmer - farmerj2013@my.fit.edu",
           "Dale Drinks - ddrinks2013@my.fit.edu",
           "Adam Hill - ahill2013@my.fit.edu"

           ];

           for(var i = 0; i < data.length; i++){
               var u = data[i].split('-');
               var user = {};
               user.id = 0;
               user.created_on = null;
               user.fullname = u[0].trim();
               user.email = u[1].trim();
               user.password = "password";
               user.need_password_reset = true;
               $http.post(URLFactory.getUsersURL(), user).success(function(){});
           }
           return true;
       };

}]);