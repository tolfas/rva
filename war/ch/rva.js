'use strict';

angular.module('risevision.rva', [
  'risevision.common.header',
  'risevision.common.header.templates'
])
.value('DISPLAYS_URL', 'https://apps.risevision.com/displays')
.value("FORCE_GOOGLE_AUTH", true)
// Set up our mappings between URLs, templates, and controllers
.config(['$urlRouterProvider', '$stateProvider', '$locationProvider',
  function storeRouteConfig($urlRouterProvider, $stateProvider, 
    $locationProvider) {

    $locationProvider.html5Mode(false);
    $locationProvider.hashPrefix("");

    $urlRouterProvider.otherwise("/");

    // Use $stateProvider to configure states.
    $stateProvider.state('rva', {
      template: '<div ui-view></div>'
    })

    .state('rva.start', {
      url: '/',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })

    // editor
    .state('rva.editor', {
      url: '?cid',
      abstract: true
    })

    .state('rva.editor.list', {
      url: '/PRESENTATIONS',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('rva.editor.manage', {
      url: '/PRESENTATION_MANAGE/:id/:fromCompanyId',
      params: {
        id: {squash: true},
        fromCompanyId: {squash: true}
      },
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })

    .state('rva.schedules', {
      url: '?cid',
      abstract: true,
    })
    .state('rva.schedules.list', {
      url: '/SCHEDULES',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('rva.schedules.manage', {
      url: '/SCHEDULE_MANAGE/:id',
      params: {
        id: {squash: true}
      },
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })

    .state('rva.gadgets', {
      url: '?cid',
      abstract: true,
    })
    .state('rva.gadgets.list', {
      url: '/GADGETS',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('rva.gadgets.manage', {
      url: '/GADGET_MANAGE/:id',
      params: {
        id: {squash: true}
      },
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })

    .state('rva.settings', {
      url: '?cid',
      abstract: true,
    })    
    .state('rva.settings.company', {
      url: '/COMPANY_MANAGE',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('rva.settings.portal', {
      url: '/MANAGE_PORTAL',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
  }
])
.controller('AppCtrl', ['$scope', '$rootScope', '$window', '$location', 
  '$state', 'userState', 'segmentAnalytics', 'zendesk', 'DISPLAYS_URL',
  function ($scope, $rootScope, $window, $location, $state, userState, 
    segmentAnalytics, zendesk, DISPLAYS_URL) {
    var navOptions = [{
      title: 'Start',
      link: $state.href('rva.start'),
      cid: true,
      states: ['rva.start']
    }, {
      title: 'Presentations',
      link: $state.href('rva.editor.list'),
      cid: true,
      states: [
        'rva.editor.list',
        'rva.editor.manage'
      ]
    }, {
      title: 'Gadgets',
      link: $state.href('rva.gadgets.list'),
      cid: true,
      states: [
        'rva.gadgets.list',
        'rva.gadgets.manage'
      ]
    }, {
      title: 'Displays',
      link: DISPLAYS_URL,
      cid: true,
      target: '_blank',
    }, {
      title: 'Schedules',
      link: $state.href('rva.schedules.list'),
      cid: true,
      states: [
        'rva.schedules.list', 
        'rva.schedules.manage'
      ]
    }, {
      title: 'Settings',
      link: $state.href('rva.settings.company'),
      cid: true,
      states: ['rva.settings.company']
    }, {
      title: 'Network',
      link: $state.href('rva.settings.portal'),
      cid: true,
      states: ['rva.settings.portal']
    }];
    
    $scope.navOptions = [];
    $scope.navSelected = 'rva.start';
    
    $scope.hideCommonHeader = false;

    $rootScope.$on('$stateChangeSuccess', function () {
      $scope.navSelected = $state.current.name;
      $scope.hideCommonHeader = $state.current.name.indexOf('common.auth') !== -1;
    });
    
    $rootScope.$on('risevision.user.signedOut', function () {
      $state.go('common.auth.unauthorized');
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
        else if (navOptions[i].title === 'Start') {
        	visibleOptions.push(navOptions[i]);
        }
      }
      
      $scope.navOptions = visibleOptions;
    }
    
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
    
    var updateLinkCompanyId = function(companyId) {
      for (var i = 0; i < navOptions.length; i++) {
        var index = navOptions[i].link.indexOf("?cid=")
        if (index > -1) {
          navOptions[i].link = navOptions[i].link.substring(0, index + 5) + companyId;
        }
        else {
          navOptions[i].link = navOptions[i].link + "?cid=" + companyId;
        }
      }
    }
    
    $scope.$watch(function () {
        return userState.getSelectedCompanyId();
      }, function(value) {
      if (value) {
        updateLinkCompanyId(userState.getSelectedCompanyId());

        // if (userState.isSubcompanySelected()) {
        //   $window.rva__reportCompanyId(value);
        // }
        // else {
        //   $window.rva__resetCompany();
        // }
      }
    });
    
    $scope.$watch(function () {
      return $location.path();
    }, function(value) {
      zendesk.forceCloseAll();
    });

    // RVA calls this:
    // the RVA highjacks the route change handlers from the CH
    // so it sends notificatios for those events
    $window.rvChCtrl = {
      updateCompanyId: function(companyId) {
        if (companyId && companyId !== userState.getSelectedCompanyId()) {
          if (userState.getUserCompanyId() && companyId !== userState.getUserCompanyId()) {
            userState.switchCompany(companyId);
          }
          else {
            userState.resetCompany();
            $scope.$digest();
          }
        }
      },
      trackPageview: function(contentId) {
    	  // [AD] Disabling pageviews from RVA
    	  //segmentAnalytics.pageview(contentId);
    	}
    };
    
  }
]) //ctr
angular.module("risevision.common.i18n.config", [])
  .constant("LOCALES_PREFIX", "ch/locales/translation_")
  .constant("LOCALES_SUFIX", ".json");
//.value('CORE_URL', 'https://rvacore-test.appspot.com/_ah/api'); // override default core value
;
