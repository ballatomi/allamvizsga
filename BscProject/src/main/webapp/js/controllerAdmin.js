'use strict';

/**
 * Controlling admin page
 */

function AdminController($scope, $http, $location, $window) {

	/**
	 * Load the initial elements to page
	 */
	$scope.init = function() {
		
		$scope.loadInstruments();
		$scope.loadGenres();
		$scope.loadUsers();
		
	}

	$scope.loadInstruments = function() {
		$http.get(urlSheetMusic + "get/getAllInstrument").success(
				function(response) {
					$scope.instrumentsList = response.instrument;
				});
	}
	
	$scope.loadGenres = function() {
		$http.get(urlSheetMusic + "get/getAllGenre").success(
				function(response) {
					$scope.songGenreList = response.songGenre;
				});
	}
	
	$scope.loadUsers = function() {
		$http.get(urlSheetMusic + "get/getAllUsers").success(
				function(response) {
					console.log(response);
					$scope.usersList = response.user;
				});
	}
	
	$scope.changeUserRight = function(id, rigth) {
		console.log(id + " " + rigth);
		$http.post(urlSheetMusic + "userRight/" + id + "/" + rigth).success(
				function(response) {
					$scope.usersList = response.user;
				});
	}
	
	/**
	 * Add new Genre
	 */
	$scope.addNewGenre = function() {
		if ($scope.genreName != undefined) {
			console.log($scope.genreName);
			$http.put(
					urlSheetMusicView + "addGenre/"
							+ $scope.genreName).success(
					function(response) {
						console.log(response);

						$scope.loadGenres();
						$scope.MessageGenre = response.response;
						
					});
		}else {
			$scope.MessageGenre = "Genre name is undefined!";
		}
	}
	
	
	/**
	 * Delete song genre
	 */
	$scope.removeGenre = function(id) {
		console.log("Delete genre: " + id);
		$http({
			url : urlSheetMusicView + 'deleteGenre/' + id,
			method : 'DELETE',
		}).then(function(response) {
			$scope.loadGenres();
			$scope.MessageGenre = response.response;
		});
	};
	
	/**
	 * Add new intrument
	 */
	$scope.addNewInstrument = function() {
		if ($scope.instrumentName != undefined) {
			console.log($scope.instrumentName);

			$http.put(
					urlSheetMusicView + "addInstrument/"
							+ $scope.instrumentName).success(
					function(response) {
						console.log(response);
						$scope.ErrorMessage = response.response;
						$scope.loadInstruments();
					});
		} else {
			$scope.ErrorMessage = "Instrument name is undefined!";
		}
	};

	/**
	 * Delete an instrument, and delete the instrument sheetmusic connection
	 */
	$scope.removeInstrument = function(id) {
		console.log("Delete Instrument: " + id);
		$http({
			url : urlSheetMusicView + 'deleteInstrument/' + id,
			method : 'DELETE',
		}).then(function(response) {
			$scope.loadInstruments();
			$scope.ErrorMessage = response.response;
		});
	};
	
	/**
	 * Get loggedIn user
	 */
	$scope.getLoggedUser = function() {
		$http.get(urlLogin + "loggedIn").success(function(response) {
			console.log(response);

			if (response != "") {
				if (response.userRight == 0) {
					$scope.loggedUser = response.userName;
				} else {
					$location.path('/permission');
				}
			} else {
				$scope.loggedUser = "Guest user"
				$location.path('/notLogged');
			}
		});
	};

	/**
	 * Log out user
	 */
	$scope.logout = function(user) {
		$http.get(urlLogin + "logout").success(function(response) {
			console.log(response);
			$location.path('/login');
		});
	};

}