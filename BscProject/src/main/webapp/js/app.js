'use strict';

var app = angular.module('ngMusicApp', [], function($routeProvider,
		$locationProvider) {

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
	
	$routeProvider.when('/#', {
	});

	$routeProvider.otherwise({
		redirectTo : '/login'
	});

	// configure html5 to get links working
	// If you don't do this, you URLs will be base.com/#/home rather than
	// base.com/home
	// $locationProvider.html5Mode(true);
});