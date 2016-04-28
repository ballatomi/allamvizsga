'use strict';

var app = angular.module('ngMusicApp', [ 'ngMusicApp.filters',
		'ngMusicApp.directives', 'ngMusicApp.services'],
		function($routeProvider, $locationProvider) {

			$routeProvider.when('/login', {
				templateUrl : 'partials/login.html',
				controller : LoginController
			});

			$routeProvider.when('/sheetmusic', {
				templateUrl : 'partials/sheetmusicMain.html',
				controller : ctrlSheetViewer
			});

			$routeProvider.when('/header', {
				templateUrl : 'partials/header.html',
				controller : logoutController
			});

			$routeProvider.when('/notLogged', {
				templateUrl : 'partials/PermissionDenied.html',
			});

			$routeProvider.when('/upload', {
				templateUrl : 'partials/sheetmusicUpload.html',
				controller : UploadController
			});

			$routeProvider.when('/smView', {
				templateUrl : 'partials/sheetmusicView.html',
				controller : ViewController
			});
			
			///TODO
			$routeProvider.when('/profile', {
				templateUrl : 'partials/userProfile.html',
				controller : UserPersonalController
			});

			$routeProvider.when('/about', {
				templateUrl : 'partials/about.html',
			});


			$routeProvider.when('/#', {});

			$routeProvider.otherwise({
				redirectTo : '/login'
			});

		});