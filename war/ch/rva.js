'use strict';

angular.module('risevision.rva', [
  'ngRoute',
  'risevision.rva.services',
  'risevision.common.header',
  'risevision.common.header.templates'
])
.value('DISPLAYS_URL', 'https://apps.risevision.com/displays')
// Set up our mappings between URLs, templates, and controllers
.config(['$urlRouterProvider', '$stateProvider', '$locationProvider',
  function storeRouteConfig($urlRouterProvider, $stateProvider, 
    $locationProvider) {

    $locationProvider.html5Mode(false);

    $urlRouterProvider.otherwise("/");

    // Use $stateProvider to configure states.
    $stateProvider

    .state('apps.start', {
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
    .state('apps.editor', {
      url: '?cid',
      abstract: true
    })

    .state('apps.editor.list', {
      url: '/PRESENTATIONS',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('apps.editor.manage', {
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

    .state('apps.schedules', {
      url: '?cid',
      abstract: true,
    })
    .state('apps.schedules.list', {
      url: '/SCHEDULES',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('apps.schedules.manage', {
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

    .state('apps.gadgets', {
      url: '?cid',
      abstract: true,
    })
    .state('apps.gadgets.list', {
      url: '/GADGETS',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('apps.gadgets.manage', {
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

    .state('apps.settings', {
      url: '?cid',
      abstract: true,
    })    
    .state('apps.settings.company', {
      url: '/COMPANY_MANAGE',
      resolve: {
        canAccessApps: ['canAccessApps',
          function (canAccessApps) {
            return canAccessApps();
          }
        ]
      }
    })
    .state('apps.settings.portal', {
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
      link: '#/',
      states: ['apps.start']
    }, {
      title: 'Presentations',
      link: '#/PRESENTATIONS',
      states: [
        'apps.editor.list',
        'apps.editor.manage'
      ]
    }, {
      title: 'Gadgets',
      link: '#/GADGETS',
      states: [
        'apps.gadgets.list',
        'apps.gadgets.manage'
      ]
    }, {
      title: 'Displays',
      link: DISPLAYS_URL,
      cid: true,
      target: '_blank',
    }, {
      title: 'Schedules',
      link: '#/SCHEDULES',
      states: [
        'apps.schedules.list', 
        'apps.schedules.manage'
      ]
    }, {
      title: 'Settings',
      link: '#/COMPANY_MANAGE',
      states: ['apps.settings.company']
    }, {
      title: 'Network',
      link: '#/MANAGE_PORTAL',
      states: ['apps.settings.portal']
    }];
    
    $scope.navOptions = [];
    $scope.navSelected = 'apps.start';
    
    $scope.hideCH = false;

    $rootScope.$on('$stateChangeSuccess', function () {
      $scope.navSelected = $state.current.name;
      $scope.hideCH = $state.current.name ===
        'apps.launcher.unauthorized' ||
        $state.current.name === 'apps.launcher.unregistered' ||
        $state.current.name === 'apps.launcher.signin' ||
        $state.current.name === 'apps.launcher.signup';
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
angular.module('risevision.rva.services', [
  'risevision.common.header'
]);
angular.module("risevision.common.i18n.config", [])
  .constant("LOCALES_PREFIX", "ch/locales/translation_")
  .constant("LOCALES_SUFIX", ".json")
  .value('CORE_URL', 'https://custom-rise-auth-dot-rvacore-test.appspot.com/_ah/api'); // override default core value
;
