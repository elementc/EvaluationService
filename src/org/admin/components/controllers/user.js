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


       window.loadGroupMemberData = function(){
           var data = [

               //"Arsenic - Xavier Merino - xmerino2012@my.fit.edu",
               //"Arsenic - Alyssa Marcoux - amarcoux2013@my.fit.edu",
               //"Arsenic - Jocsan Macias - maciasj2013@my.fit.edu",
               //"Arsenic - Maher Basha - mbasha2013@my.fit.edu",
               //"Desensitizing Titanium - William Nyffenegger - wnyffenegger2013@my.fit.edu",
               //"Desensitizing Titanium - Jocday Macias - jmacias2013@my.fit.edu",
               //"Desensitizing Titanium - Gurkirat Kainth - gkainth2013@my.fit.edu",
               //"Desensitizing Titanium - Dalal Alajaji - dalajaji2011@my.fit.edu",
               //"Code Lemurs - Nicholas Klofta - nklofta2012@my.fit.edu",
               //"Code Lemurs - Kevin Spanier - kspanier2013@my.fit.edu",
               //"Code Lemurs - Yat Pang - apang2013@my.fit.edu",
               //"Code Lemurs - Christopher Pangalos - cpangalos2012@my.fit.edu",
               //"Team Hated - Abdullah Alsaraf - aalsaraf2011@my.fit.edu",
               //"Team Hated - Hamoud AlQahtani - halqahta2007@my.fit.edu",
               //"Team Hated - Martynas Mickus - mmickus2012@my.fit.edu",
               //"Team Hated - Sally Tan Yu - stanyu2013@my.fit.edu",
               //"Slaking - Charles Petersen - cpetersen2012@my.fit.edu",
               //"Slaking - Dhanish Mehta - mehtad2012@my.fit.edu",
               //"Slaking - Michael Stratton - mstratton2012@my.fit.edu",
               //"Slaking - Harrison Cord - hcord2012@my.fit.edu",
               //"Jiggle My Puff - Timothy Von Friesen - tvonfriesen2012@my.fit.edu",
               //"Jiggle My Puff - Otto Irwin - oirwin2012@my.fit.edu",
               //"Jiggle My Puff - Charles Endicott - cendicott2013@my.fit.edu",
               //"Jiggle My Puff - Hosam Alsubhi - halsubhi2013@my.fit.edu",
               //"Caterpie - Corin Lobo - clobo2012@my.fit.edu",
               //"Caterpie - Jacob Bowers - jbowers2013@my.fit.edu",
               //"Caterpie - Stephen Fournier - sfournier2013@my.fit.edu",
               //"Caterpie - Joshua Selom Adadevoh - jadadevoh2008@fit.edu",
               //"Thunder Chickens - Abhijay Mahajan - amahajan2013@my.fit.edu",
               //"Thunder Chickens - Austin Haggard - ahaggard2013@my.fit.edu",
               //"Thunder Chickens - Ahmad Hijazi - ahijazi2012@my.fit.edu",
               //"Gekkenhuis - Milica Knezevic - mknezevic2013@my.fit.edu",
               //"Gekkenhuis - Joseph Torano - jtorano2013@my.fit.edu",
               //"Gekkenhuis - Tyler Petroske - tpetroske2013@my.fit.edu",
               //"Gekkenhuis - Mohammad Shamsah - mshamsah2011@my.fit.edu",
               //"Anonymous - Louay El Biche - lelbiche2013@my.fit.edu",
               //"Anonymous - Strahinja Markovic - smarkovic2013@my.fit.edu",
               //"Anonymous - Thiago Pina Soares - tpinasoares2015@my.fit.edu",
               //"Anonymous - Jacob Gollert - jgollert2013@my.fit.edu",
               //"Null_Pointer - Nick Namba - nnamba2013@my.fit.edu",
               //"Null_Pointer - Aaron Nies - anies2014@my.fit.edu",
               //"Null_Pointer - Santiago Roig - sroig2013@my.fit.edu",
               //"Null_Pointer - Rafael Trujillo - rtrujillo2012@my.fit.edu",
               //"Team Goto - Oluwafunmiwo Sholola - osholola2013@my.fit.edu",
               //"Team Goto - Nathaniel Watson - watsonn2013@my.fit.edu",
               //"Team ComeFrom - Nick Whitlock - nwhitlock2013@my.fit.edu",
               //"Team ComeFrom - Mohammed Al Habsi - malhabsi2013@my.fit.edu",
               //"Mewtwo - Michael Tishman - mtishman2013@my.fit.edu",
               //"Mewtwo - Heather Lemieux - hlemieux2011@my.fit.edu",
               //"Mewtwo - Brian Dumont - bdumont2012@my.fit.edu",
               //"Mewtwo - Gregory Moody - gmoody2013@my.fit.edu",
               //"Anything Will Be FINE - Brandon Binkley - bbinkley2010@my.fit.edu",
               //"Anything Will Be FINE - Stephan Stross - sstross2012@my.fit.edu",
               //"Anything Will Be FINE - Mariane Regina Afonso Vieira - mafonsovieir2015@my.fit.edu",
               //"Anything Will Be FINE - Abdulrazzaq Meer - ameer2011@my.fit.edu",
               //"Droids - Jess Farmer - farmerj2013@my.fit.edu",
               //"Droids - Dale Drinks - ddrinks2013@my.fit.edu",
               //"Droids - Adam Hill - ahill2013@my.fit.edu"

               "Arsenic - 1 - Xavier Merino - xmerino2012@my.fit.edu",
               "Arsenic - 1 - Alyssa Marcoux - amarcoux2013@my.fit.edu",
               "Arsenic - 1 - Jocsan Macias - maciasj2013@my.fit.edu",
               "Arsenic - 1 - Maher Basha - mbasha2013@my.fit.edu",
               "Desensitizing Titanium - 2 - William Nyffenegger - wnyffenegger2013@my.fit.edu",
               "Desensitizing Titanium - 2 - Jocday Macias - jmacias2013@my.fit.edu",
               "Desensitizing Titanium - 2 - Gurkirat Kainth - gkainth2013@my.fit.edu",
               "Desensitizing Titanium - 2 - Dalal Alajaji - dalajaji2011@my.fit.edu",
               "Code Lemurs - 3 - Nicholas Klofta - nklofta2012@my.fit.edu",
               "Code Lemurs - 3 - Kevin Spanier - kspanier2013@my.fit.edu",
               "Code Lemurs - 3 - Yat Pang - apang2013@my.fit.edu",
               "Code Lemurs - 3 - Christopher Pangalos - cpangalos2012@my.fit.edu",
               "Team Hated - 4 - Abdullah Alsaraf - aalsaraf2011@my.fit.edu",
               "Team Hated - 4 - Hamoud AlQahtani - halqahta2007@my.fit.edu",
               "Team Hated - 4 - Martynas Mickus - mmickus2012@my.fit.edu",
               "Team Hated - 4 - Sally Tan Yu - stanyu2013@my.fit.edu",
               "Slaking - 6 - Charles Petersen - cpetersen2012@my.fit.edu",
               "Slaking - 6 - Dhanish Mehta - mehtad2012@my.fit.edu",
               "Slaking - 6 - Michael Stratton - mstratton2012@my.fit.edu",
               "Slaking - 6 - Harrison Cord - hcord2012@my.fit.edu",
               "Jiggle My Puff - 5 - Timothy Von Friesen - tvonfriesen2012@my.fit.edu",
               "Jiggle My Puff - 5 - Otto Irwin - oirwin2012@my.fit.edu",
               "Jiggle My Puff - 5 - Charles Endicott - cendicott2013@my.fit.edu",
               "Jiggle My Puff - 5 - Hosam Alsubhi - halsubhi2013@my.fit.edu",
               "Caterpie - 7 - Corin Lobo - clobo2012@my.fit.edu",
               "Caterpie - 7 - Jacob Bowers - jbowers2013@my.fit.edu",
               "Caterpie - 7 - Stephen Fournier - sfournier2013@my.fit.edu",
               "Caterpie - 7 - Joshua Selom Adadevoh - jadadevoh2008@fit.edu",
               "Thunder Chickens - 8 - Abhijay Mahajan - amahajan2013@my.fit.edu",
               "Thunder Chickens - 8 - Austin Haggard - ahaggard2013@my.fit.edu",
               "Thunder Chickens - 8 - Ahmad Hijazi - ahijazi2012@my.fit.edu",
               "Gekkenhuis - 9 - Milica Knezevic - mknezevic2013@my.fit.edu",
               "Gekkenhuis - 9 - Joseph Torano - jtorano2013@my.fit.edu",
               "Gekkenhuis - 9 - Tyler Petroske - tpetroske2013@my.fit.edu",
               "Gekkenhuis - 9 - Mohammad Shamsah - mshamsah2011@my.fit.edu",
               "Anonymous - 10 - Louay El Biche - lelbiche2013@my.fit.edu",
               "Anonymous - 10 - Strahinja Markovic - smarkovic2013@my.fit.edu",
               "Anonymous - 10 - Thiago Pina Soares - tpinasoares2015@my.fit.edu",
               "Anonymous - 10 - Jacob Gollert - jgollert2013@my.fit.edu",
               "Null_Pointer - 13 - Nick Namba - nnamba2013@my.fit.edu",
               "Null_Pointer - 13 - Aaron Nies - anies2014@my.fit.edu",
               "Null_Pointer - 13 - Santiago Roig - sroig2013@my.fit.edu",
               "Null_Pointer - 13 - Rafael Trujillo - rtrujillo2012@my.fit.edu",
               "Team Goto - 11 - Oluwafunmiwo Sholola - osholola2013@my.fit.edu",
               "Team Goto - 11 - Nathaniel Watson - watsonn2013@my.fit.edu",
               "Team ComeFrom - 12 - Nick Whitlock - nwhitlock2013@my.fit.edu",
               "Team ComeFrom - 12 - Mohammed Al Habsi - malhabsi2013@my.fit.edu",
               "Mewtwo - 15 - Michael Tishman - mtishman2013@my.fit.edu",
               "Mewtwo - 15 - Heather Lemieux - hlemieux2011@my.fit.edu",
               "Mewtwo - 15 - Brian Dumont - bdumont2012@my.fit.edu",
               "Mewtwo - 15 - Gregory Moody - gmoody2013@my.fit.edu",
               "Anything Will Be FINE - 14 - Brandon Binkley - bbinkley2010@my.fit.edu",
               "Anything Will Be FINE - 14 - Stephan Stross - sstross2012@my.fit.edu",
               "Anything Will Be FINE - 14 - Mariane Regina Afonso Vieira - mafonsovieir2015@my.fit.edu",
               "Anything Will Be FINE - 14 - Abdulrazzaq Meer - ameer2011@my.fit.edu",
               "Droids - 16 - Jess Farmer - farmerj2013@my.fit.edu",
               "Droids - 16 - Dale Drinks - ddrinks2013@my.fit.edu",
               "Droids - 16 - Adam Hill - ahill2013@my.fit.edu"

           ];

           for(var i = 0; i < data.length; i++){
               var info = data[i].split('-');
               var user = {};
               user.email = info [3].trim();
               user.password = "password";
               func(user, info[1].trim());

           }
           return true;
       };

       var func = function(user, info){
           $http.post(URLFactory.getAuthURL(), user).success(function(userinfo){
               var groupmember = {};
               groupmember.id = 0;
               groupmember.group_id = info;
               groupmember.user_id = userinfo.id;
               $http.post(URLFactory.getGroupMembersURL(), groupmember).success(function(){
               });
           });
       }

}]);