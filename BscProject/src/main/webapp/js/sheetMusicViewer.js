/**
 * 
 */
var linkToMusic;
var pdfAsArray;
var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";

var App = angular.module('controllSheetMusic', []);
App.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {

	$scope.showLoader = true;
	$scope.init = function() {
		var id = getParameterByName('page');
		console.log("Get byID: " + id);

		$http.get(urlSheetMusic + "getSheetmusicBySheetID/" + id).success(
				function(response) {
					console.log(response);
					$scope.showLoader = false;

					$scope.sheetmusicName = response.name;

					// load pdf
					var data = atob(response.filePdf);
					var pdfAsArray = new Array(data.length);
					for (var i = 0; i < data.length; i++) {
						pdfAsArray[i] = data.charCodeAt(i);
					}

					var pdfUint = new Uint8Array(pdfAsArray);
					loadCanvas(1, pdfUint);

					// load audio

					var audiodata = atob(response.fileSound);
					var audioArray = new Array(audiodata.length);
					for (var i = 0; i < audiodata.length; i++) {
						 audioArray[i] = audiodata.charCodeAt(i);
					}
					var file = new Blob([ new Uint8Array(audioArray) ]);
					
					var link = document.createElement('a');
					link.href = window.URL.createObjectURL(file);
					console.log(link.href);
					
					linkToMusic = link.href;
					
					var audio = document.getElementById('audio');

					audio.src = link.href;
			        audio.load(); //call this to just preload the audio without playing
			        audio.play();
					
			        //midi file
			        //link.download = "file.mid";
					//link.click();
					
					//MIDIjs.play(link.href);

				});
	}

	$scope.palyMidiMusic = function() {
		MIDIjs.play(linkToMusic);
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

function loadCanvas(page, pdfAsArray) {
	var pdf = PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		maxPageNumber = pdf.numPages;
		pdf.getPage(page).then(function(page) {
			var scale = 1.0;
			var viewport = page.getViewport(scale);
			var canvas = document.getElementById('pdfCanvas');

			var context = canvas.getContext('2d');
			canvas.height = viewport.height;
			canvas.width = viewport.width;

			var renderContext = {
				canvasContext : context,
				viewport : viewport
			};
			page.render(renderContext);
		});
	});
}

function getParameterByName(name, url) {
	if (!url)
		url = window.location.href;
	url = url.toLowerCase(); // This is just to avoid case sensitiveness
	name = name.replace(/[\[\]]/g, "\\$&").toLowerCase();
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}
