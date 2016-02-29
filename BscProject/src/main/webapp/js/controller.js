var urlLogin = "http://localhost:8080/BscProject/rest/login/";

var user = null;

var App = angular.module('controllApp', []);
App.controller('Ctrl', function($scope, $http, $location, $window) {

	$scope.login = function(user) {
		// with user - angular model parameter
		if (user.userName != undefined && user.userPassword != undefined) {

			$http.post(urlLogin + "log", user).success(function(response) {
				console.log(response);
				$scope.message = response.Message;
				$scope.display_type = response.display_type;
				$window.location = 'pages/upload.html';
			});
		} else {
			$scope.message = "Username/password not filled!";
			$scope.display_type = "initial";
		}
	};
});

App.controller('CtrlReg', function($scope, $http) {
	$scope.registration = function(user) {
		if (!angular.isUndefined(user)) {
			if (user.userPassword == user.re_password) {
				$http.post(urlLogin + "reg", user).success(function(response) {
					console.log(response);
					$scope.regMessage = response.regMessage;
					$scope.regdisplay_type = response.display_type;
					$scope.color = response.color;
				});
			} else {
				$scope.regMessage = "Password is not correct!";
				$scope.regdisplay_type = "initial";
				$scope.color = "red";
			}
		} else {
			$scope.regMessage = "Fields must be filled!";
			$scope.regdisplay_type = "initial";
			$scope.color = "red";
		}
	};

});
