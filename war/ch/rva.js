'use strict';

angular.module('risevision.rva', [
  'risevision.common.header',
  'risevision.common.header.templates'
])
.controller('AppCtrl', ['$scope', '$window', '$location', 'userState',
  function ($scope, $window, $location, userState) {
    $scope.navOptions = [{
      title: 'Displays',
      link: '#/',
      states: ['root.common.displays']
    }, {
      title: 'Help',
      link: 'http://help.risevision.com/#/user/display/what-is-a-display',
      target: '_blank'
    }];
    $scope.navSelected = 'root.common.displays';
    
    var watch = $scope.$watch(function () {
        return userState.isLoggedIn();
      }, function(value) {
      if (value) {
        $window.reportUserLoggedIn();

        watch();
      }
    });
  }
]); //ctr
