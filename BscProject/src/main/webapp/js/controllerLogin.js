'use strict';

/* Controllers */

var urlLogin = "http://localhost:8080/BscProject/rest/login/";


function LoginController($scope, $http, $location) {
	$scope.showLoginAlert = false;
	$scope.login = function(user) {
		
		//regenerate session
		sessionStorage.clear();
		
		// with user - angular model parameter
		if (user.userName != undefined && user.userPassword != undefined) {
			$http.post(urlLogin + "log", user).success(function(response) {
				$scope.loginMessage = response.Message;
				$scope.showLoginAlert = true;
				
				if (response.login == "true") {
					$http.get(urlLogin + "loggedIn").success(function(response) {
						if (response.userRight == 0){
							$location.path('/admin');
						}else{
							$location.path('/sheetmusic');
						}
					});
				}
			});
		} else {
			$scope.loginMessage = "Username/password not filled!";
			$scope.showLoginAlert = true;
		}
	};
	
	$scope.showRegistrationAlert = false;
	$scope.registration = function(user) {
		console.log(user);

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
 


