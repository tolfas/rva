'use strict';

angular.module('risevision.rva', [
  'ngRoute',
  'risevision.common.header',
  'risevision.common.header.templates'
])
.controller('AppCtrl', ['$scope', '$window', '$location', 'userState',
  function ($scope, $window, $location, userState) {
    var navOptions = [{
      title: 'Start',
      link: '#/',
      states: ['root.common.start']
    }, {
      title: 'Presentations',
      link: '#/PRESENTATIONS',
      states: ['root.common.presentations']
    }, {
      title: 'Gadgets',
      link: '#/GADGETS',
      states: ['root.common.gadgets']
    }, {
      title: 'Displays',
      link: '#/DISPLAYS',
      states: ['root.common.displays']
    }, {
      title: 'Schedules',
      link: '#/SCHEDULES',
      states: ['root.common.schedules']
    }, {
      title: 'Settings',
      link: '#/COMPANY_MANAGE',
      states: ['root.common.settings']
    }, {
      title: 'Network',
      link: '#/MANAGE_PORTAL',
      states: ['root.common.network']
    }];
    
    $scope.navOptions = [];
    $scope.navSelected = 'root.common.start';
    
    $scope.$watch(function () {
        return userState.isRiseVisionUser();
      }, function(value) {
      if (value) {
        $window.rva__reportUserLoggedIn();
        
        updateNavOptions();
      }
      else if ($window.rva__reportUserLoggedOut) {
        $window.rva__reportUserLoggedOut();
        
        $scope.navOptions = [];
        
        $location.path('');
      }
    });
    
    $scope.$watch(function () {
        return userState.getSelectedCompanyId();
      }, function(value) {
      if (value) {
        if (userState.isSubcompanySelected()) {
          $window.rva__reportCompanyId(value);
        }
        else {
          $window.rva__resetCompany();
        }
      }
    });
    
    $scope.$watch(function () {
      return $location.path();
    }, function(value) {
      if (value.indexOf('PRESENTATIONS') > -1 || value.indexOf('PRESENTATION_MANAGE') > -1) {  
        $scope.navSelected = 'root.common.presentations';
      }
      else if (value.indexOf('GADGETS') > -1 || value.indexOf('GADGET_MANAGE') > -1) {  
        $scope.navSelected = 'root.common.gadgets';
      }
      else if (value.indexOf('DISPLAYS') > -1 || value.indexOf('DISPLAY_MANAGE') > -1) {  
        $scope.navSelected = 'root.common.displays';
      }
      else if (value.indexOf('SCHEDULES') > -1 || value.indexOf('SCHEDULE_MANAGE') > -1) {  
        $scope.navSelected = 'root.common.schedules';
      }
      else if (value.indexOf('COMPANY_MANAGE') > -1) {  
        $scope.navSelected = 'root.common.settings';
      }
      else if (value.indexOf('MANAGE_PORTAL') > -1) {  
        $scope.navSelected = 'root.common.network';
      }
      else {  
        $scope.navSelected = 'root.common.start';
      }
    });

    var updateNavOptions = function() {
      var visibleOptions = [];
      for (var i = 0; i < navOptions.length; i++) {
        if (navOptions[i].title === 'Presentations' && (userState.hasRole('ce') || userState.hasRole('cp'))) {
          visibleOptions.push(navOptions[i]);
        }
        else if ((navOptions[i].title === 'Gadgets' || navOptions[i].title === 'Schedules') && userState.hasRole('cp')) {
          visibleOptions.push(navOptions[i]);
        }
        else if (navOptions[i].title === 'Displays' && userState.hasRole('da')) {
          visibleOptions.push(navOptions[i]);
        }
        else if ((navOptions[i].title === 'Settings' || navOptions[i].title === 'Network') && userState.hasRole('ua')) {
          visibleOptions.push(navOptions[i]);
        }
      }
      
      $scope.navOptions = visibleOptions;
    }

  }
]) //ctr
//.value('CORE_URL', 'https://rvacore-test.appspot.com/_ah/api'); // override default core value
;