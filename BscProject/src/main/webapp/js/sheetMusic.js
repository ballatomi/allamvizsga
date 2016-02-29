var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";

var App = angular.module('controllSheetMusic', []);
App.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {

	$scope.init = function() {
		$http.post(urlSheetMusic + "getAll").success(function(response) {
			console.log(response.sheetMusic);

			$scope.Sheetmusic = response.sheetMusic;
			// console.log(response.sheetmusic[2].filePdf); - pdf json-ban
			// byte-onkent

			// Json-bol file-ra alakitas
			var data = atob(response.sheetMusic[1].filePdf);
			var pdfAsArray = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				pdfAsArray[i] = data.charCodeAt(i);
			}

			if (pdfAsArray != null) {
				pdfAsArray = new Uint8Array(pdfAsArray);
				var blob = new Blob([ pdfAsArray ]);
				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = "filePdf.pdf";
				// link.click();
			} else {
				alert("PDF file not selected!");
			}
		});
	}

});

App.controller('uploadSheetCtrl',
		function($scope, $http, $timeout) {
			$scope.songGenreList = [];
			$scope.init = function() {
				$http.post(urlSheetMusic + "getAllGenre").success(
						function(response) {
							console.log(response);
							$scope.songGenreList = response.songGenre;
						});
			}

			$scope.uploadButtonText = "Upload";

			$scope.uploadSheet = function(sheet) {
				var filepdf = document
						.getElementById("upload-file-selector-pdf").files;
				var fileSound = document
						.getElementById("upload-file-selector-sound").files;

				// sheet.filePdf = file[0];
				console.log("File pdf: " + filepdf[0]);

				try {
					if (sheet.name != undefined) {

						var fd = new FormData();
						fd.append("filepdf", filepdf[0]);
						fd.append("filesound", fileSound[0]);
						fd.append("name", sheet.name);

						if (isNaN(sheet.selectedGenre)) {
							sheet.selectedGenre = '20';
						}
						fd.append("genre_id", parseInt(sheet.selectedGenre));

						if (sheet.length == undefined) {
							sheet.length = 1;
						}
						fd.append("length", parseInt(sheet.length));
						fd.append("license", sheet.license);

						// uploading spinner
						$scope.uploadButtonText = "Uploading";

						$http.post(urlSheetMusic + "upload", fd, {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).success(function(response) {
							console.log(response);
							$scope.message = response.message;
							$scope.display_type = response.display_type;
							$scope.alert_type = response.alert_type;

							$timeout(function() {
								$scope.uploadButtonText = "Upload";
							});
						});

					} else {
						$scope.message = "Not filled!";
						$scope.display_type = "initial";
						$scope.alert_type = "alert alert-danger";
					}
				} catch (e) {
					$scope.message = "Error! " + e;
					$scope.display_type = "initial";
					$scope.alert_type = "alert alert-danger";
				}
			}
		});
