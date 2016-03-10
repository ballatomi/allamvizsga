var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";
var pdfAsArray;
var instrumentListId = [];
var App = angular.module('controllSheetMusic', []);
App.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {

	$scope.init = function() {
		$http.post(urlSheetMusic + "getAll").success(function(response) {
			console.log(response.sheetMusic);

			$scope.Sheetmusic = response.sheetMusic;
			//console.log(response.sheetMusic[2].filePdf); - pdf json-ban
			// byte-onkent
//			var instrumentSheetMusicList;
//			var instrumentSheetMusicListlength;
			
//			$http.post(urlSheetMusic + "getAllInstrumentSheetmusic").success(function(response) {
//				instrumentSheetMusicList = response.instrumentSheetmusic;
//				instrumentSheetMusicListlength = response.instrumentSheetmusic.length
//			});
			
			// Json-bol file-ra alakitas
			var respLength = response.sheetMusic.length;
			
			for (var respInd = 0; respInd < respLength; respInd++) {

				console.log(response.sheetMusic[respInd].name);
				var data = atob(response.sheetMusic[respInd].filePdf);
				var pdfAsArray = new Array(data.length);
				for (var i = 0; i < data.length; i++) {
					pdfAsArray[i] = data.charCodeAt(i);
				}
				var pdfUint = new Uint8Array(pdfAsArray);
				var id = response.sheetMusic[respInd].sheetMusicId;
				console.log("ID: " + id);
				loadCanvas(1, pdfUint, id);
				
//				for (var int = 0; int < instrumentSheetMusicListlength; int++) {
//					if (response.sheetMusic[respInd].sheetMusicId == instrumentSheetMusicList[int].sheetMusic.sheetMusicId){
//						$scope.Sheetmusic[respInd].instrument = instrumentSheetMusicList[int].instrument;
//						console.log(instrumentSheetMusicList[int].instrument.name);
//					}
//				}

			}
		});
	}

});

App.controller('logoutController', function($scope, $http, $location, $window) {
	$scope.logout = function(user) {
		$http.get(urlLogin + "logout").success(function(response) {
			console.log(response);
			$window.location = '../';
		});
	};
});

App.controller('uploadSheetCtrl',
		function($scope, $http, $timeout) {
			$scope.songGenreList = [];
			
			$("#btnLeft").click(function () {
			    var selectedItem = $("#rightValues option:selected");
			    $("#leftValues").append(selectedItem);
			    instrumentListId = ($( "#leftValues" ).val());
			});

			$("#btnRight").click(function () {
			    var selectedItem = $("#leftValues option:selected");
			    $("#rightValues").append(selectedItem);
			    instrumentListId.splice($( "#leftValues" ).val(), 1);
			});

			$("#rightValues").change(function (val) {
			    var selectedItem = $("#rightValues option:selected");
			    $("#txtRight").val(selectedItem.text());
			});
			
			$scope.init = function() {

				$http.post(urlSheetMusic + "getAllGenre").success(
						function(response) {
							console.log(response);
							$scope.songGenreList = response.songGenre;
						});

				$http.post(urlSheetMusic + "getAllInstrument").success(
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
						$scope.message = "Not filled correctly!";
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


function loadCanvas(page, pdfAsArray, musicId) {
	var pdf = PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		maxPageNumber = pdf.numPages;
		pdf.getPage(page).then(function(page) {
			var scale = 0.35;
			var viewport = page.getViewport(scale);
			var canvas = document.getElementById(musicId);

			var context = canvas.getContext('2d');
			canvas.height = viewport.height - viewport.height / 2;
			canvas.width = viewport.width;

			var renderContext = {
				canvasContext : context,
				viewport : viewport
			};
			page.render(renderContext);
		});
	});
}
