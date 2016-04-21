'use strict';

/* Controllers */

var urlLogin = "http://localhost:8080/BscProject/rest/login/";


function LoginController($scope, $http, $location) {
		$scope.showLoginAlert = false;
	
	$scope.login = function(user) {
		// with user - angular model parameter
		if (user.userName != undefined && user.userPassword != undefined) {
			$http.post(urlLogin + "log", user).success(function(response) {
				console.log(response);
				$scope.message = response.Message;
				$scope.showLoginAlert = true;
				if (response.login == "true") {
					$location.path('/sheetmusic');
				}
			});
		} else {
			$scope.message = "Username/password not filled!";
			$scope.showLoginAlert = true;
		}
	};
	
	$scope.showRegistrationAlert = false;
	$scope.registration = function(user) {
		console.log(user);
		console.log(user.userName);
		console.log(angular.isUndefined(user.userPassword));
		console.log(user.re_password);
		console.log(user.userMail);

		if (!angular.isUndefined(user.userName)
				&& !angular.isUndefined(user.userPassword)
				&& !angular.isUndefined(user.re_password)
				&& !angular.isUndefined(user.userMail)) {
			if (user.userPassword == user.re_password) {
				$http.post(urlLogin + "reg", user).success(function(response) {
					console.log(response);
					$scope.regMessage = response.regMessage;
					$scope.showRegistrationAlert = true;
					$scope.color = response.color;
				});
			} else {
				$scope.regMessage = "Password is not correct, not match!";
				$scope.showRegistrationAlert = true;
				$scope.color = "red";
			}
		} else {
			$scope.regMessage = "Fields with * must be filled!";
			$scope.showRegistrationAlert = true;
			$scope.color = "red";
		}
	};
};	


/**
 * Controlling logout user
 */

 function logoutController($scope, $http, $location, $window) {
//angular.module('ngMusicApp.loginControllers').controller('logoutController', function($scope, $http, $location, $window) {
	$scope.getLoggedUser = function() {
		$http.get(urlLogin + "loggedIn").success(function(response) {
			console.log(response);
			
			if (response != ""){
				$scope.loggedUser = response.userName;
			}else{
				$scope.loggedUser = "Guest user"
				$location.path('/notLogged');	
			}
		});
	};
	
	$scope.logout = function(user) {
		$http.get(urlLogin + "logout").success(function(response) {
			console.log(response);
			$location.path('/login');
		});
	};
 };
//});
 


