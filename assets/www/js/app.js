// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('app', ['ionic', 'app.controllers', 'app.routes', 'app.services', 'app.directives', 'pouchdb', 'ImgCache', 'ngCordova'])

.config(['$ionicConfigProvider', 'ImgCacheProvider', function($ionicConfigProvider, ImgCacheProvider) {

    // set more options at once
    ImgCacheProvider.setOptions({
    	//debug: true,
    	usePersistentCache: true,
    	useDataURI: true,
		  skipURIencoding: true
    });

    $ionicConfigProvider.views.swipeBackEnabled(false);
    $ionicConfigProvider.scrolling.jsScrolling(false);
    $ionicConfigProvider.tabs.position('bottom'); // other values: top
    $ionicConfigProvider.tabs.style('standard');
    // $ionicConfigProvider.views.maxCache(20);
}])

.run(['$ionicPlatform', '$cordovaStatusbar', '$rootScope', '$ionicHistory', function($ionicPlatform, $cordovaStatusbar, $rootScope, $ionicHistory) {
    $ionicPlatform.ready(function() {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      if(window.cordova && window.cordova.plugins.Keyboard) {
        cordova.plugins.Keyboard.hideKeyboardAccessoryBar(false);
        cordova.plugins.Keyboard.disableScroll(true);
      }
      if(window.StatusBar) {
        // org.apache.cordova.statusbar required
        StatusBar.styleDefault();
      }
      //$cordovaStatusbar.hide();

      //screen.lockOrientation('portrait');
    });

    $rootScope.$on('$stateChangeError',
      function(event, toState, toParams, fromState, fromParams, error){
        console.log("stateChangeError: " + error);
        event.preventDefault();
    });


    $ionicPlatform.registerBackButtonAction(function () {
//      alert($ionicHistory.backView());
      if ($ionicHistory.backView()) {
        navigator.app.backHistory();
//        navigator.app.exitApp();
      } else {
        
      }
    }, 100);

    /*
    window.addEventListener("orientationchange", function(){
      console.log('Orientation changed to ' + screen.orientation);
    });
    if (window.cordova && ionic.Platform.isIOS()) {
      alert("statusTap");
      window.addEventListener("statusTap", function() {
        $ionicScrollDelegate.scrollTop(true);
      });
    }
    */
}])
