var url = "http://localhost:8080/BscProject/rest/login/";
var user = null;

var App = angular.module('controllApp', []);
App.controller('Ctrl', function($scope, $http) {

	$scope.login = function(user) {
		// with user - angular model parameter
		$http.post(url + "log", user).success(function(response) {
			console.log(response);
			$scope.message = response.Message;
			$scope.display_type = response.display_type;
		});
	};
});

App.controller('CtrlReg', function($scope, $http) {
	$scope.registration = function(user) {
		 
			$http.post(url + "reg", user).success(function(response) {
				console.log(response);
				$scope.regMessage = response.regMessage;
				$scope.regdisplay_type = response.display_type;
				$scope.color = response.color;
			});

	};

});