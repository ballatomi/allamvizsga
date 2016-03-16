var urlLogin = "http://localhost:8080/BscProject/rest/login/";

var user = null;

var App = angular.module('controllApp', []);
App.controller('Ctrl', function($scope, $http, $location, $window) {

	$scope.showLoginAlert = false;
	$scope.login = function(user) {
		// with user - angular model parameter
		if (user.userName != undefined && user.userPassword != undefined) {

			$http.post(urlLogin + "log", user).success(function(response) {
				console.log(response);
				$scope.message = response.Message;
				$scope.showLoginAlert = true;
				// $scope.display_type = response.display_type;
				if (response.login == "true") {
					$window.location = 'pages/sheetmusic.html';
				}
			});
		} else {
			$scope.message = "Username/password not filled!";
			$scope.showLoginAlert = true;
			// $scope.display_type = "initial";
		}
	};
});

// var Applog = angular.module('controllSheetMusic', []);

App.controller('CtrlReg', function($scope, $http) {
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
					// $scope.regdisplay_type = response.display_type;
					$scope.color = response.color;
				});
			} else {
				$scope.regMessage = "Password is not correct!";
				$scope.showRegistrationAlert = true;
				// $scope.regdisplay_type = "initial";
				$scope.color = "red";
			}
		} else {
			$scope.regMessage = "Fields with * must be filled!";
			$scope.showRegistrationAlert = true;
			// $scope.regdisplay_type = "initial";
			$scope.color = "red";
			console.log("UIN");
		}
	};

});
