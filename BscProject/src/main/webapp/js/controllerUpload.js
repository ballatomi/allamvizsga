

/**
 * Controlling upload sheet music
 */
var urlSheetMusicUpload = "http://localhost:8080/BscProject/rest/";

function UploadController ($scope, $http, $timeout) {
		$scope.songGenreList = [];

		$("#btnLeft").click(function() {
			var selectedItem = $("#rightValues option:selected");
			$("#leftValues").append(selectedItem);
			instrumentListId = ($("#leftValues").val());
		});

		$("#btnRight").click(function() {
			var selectedItem = $("#leftValues option:selected");
			$("#rightValues").append(selectedItem);
			instrumentListId.splice($("#leftValues").val(), 1);
		});

		$("#rightValues").change(function(val) {
			var selectedItem = $("#rightValues option:selected");
			$("#txtRight").val(selectedItem.text());
		});

		$scope.showUploadAlert = false;
		$scope.init = function() {

			$http.get(urlSheetMusicUpload + "sheet/get/getAllGenre").success(
					function(response) {
						console.log(response);
						$scope.songGenreList = response.songGenre;
					});

			$http.get(urlSheetMusicUpload + "sheet/get/getAllInstrument").success(
					function(response) {
						console.log(response);
						$scope.instrumentsList = response.instrument;
						var dataSource = response.instrument;

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
			console.log(instrumentListId);

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
					fd.append("instrumentList", instrumentListId);

					console.log(fd);
					// uploading spinner
					$scope.uploadButtonText = "Uploading";

					$http.post(urlSheetMusicUpload + "sheetUpload/upload", fd, {

						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					}).success(function(response) {
						console.log(response);
						$scope.message = response.message;
						$scope.showUploadAlert = true;
						$scope.alert_type = response.alert_type;

						$timeout(function() {
							$scope.uploadButtonText = "Upload";
						});
					});

				} else {
					$scope.message = "Not filled correctly!";
					$scope.showUploadAlert = true;
					$scope.alert_type = "alert alert-danger";
				}
			} catch (e) {
				$scope.message = "Error! " + e;
				$scope.showUploadAlert = true;
				$scope.alert_type = "alert alert-danger";
			}
		}
};
