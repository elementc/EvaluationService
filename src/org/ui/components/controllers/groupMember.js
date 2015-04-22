app.controller('GroupMemberController', ['$scope', '$http', '$routeParams', '$mdToast', '$location', 'URLFactory',
   function ($scope, $http, $routeParams, $mdToast, $location, URLFactory) {
       $scope.groupMemberID = $routeParams.id;
       $scope.isNew = $scope.groupMemberID === undefined;
       
       $http.get(URLFactory.getUsersURL()).success(function(users){
           $scope.users = users;  
           
           $http.get(URLFactory.getGroupsURL()).success(function(groups){
               $scope.groups = groups;  
               if(!$scope.isNew){
                   $http.get(URLFactory.getGroupMemberURL($scope.groupMemberID)).success(function(groupMember){
                       $scope.groupMember = groupMember;
                   }).error(function(){
                       $scope.groupMember = {};
                       $scope.isNew = true;
                       $scope.groupMember.id = 0;
                   });
               }else{
                   $scope.groupMember = {};
                   $scope.groupMember.id = 0;
               }
           });
        });
       
       
              
       $scope.save = function(){
           if($scope.isNew){
               $http.post(URLFactory.getGroupMembersURL(), $scope.groupMember).success(function(){
                   showToast('GroupMember created successfully!'); 
                   $location.path('group_members');
               });
           }else{
                $http.put(URLFactory.getGroupMembersURL(), $scope.groupMember).success(function(){
                     showToast('GroupMember updated successfully!');
                     $location.path('group_members');
               });
           }
       };
       
       $scope.delete = function(){
            $http.delete(URLFactory.getGroupMemberURL($scope.groupMember.id)).success(function(){
                 showToast('GroupMember deleted successfully!');
                 $location.path('group_members');
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