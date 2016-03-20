/**
 * 
 */
var linkToMusic;
var pdfAsArray;
var audioArray;
var maxPageNumber;
var audio;
var timeInOnePage;
var actualPage = 1;
var ellapsedTime = 0;

var timeArray;
var audioDuration;

var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";

var App = angular.module('controllSheetMusic', []);
App.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {

	$scope.showLoader = true;
	$scope.init = function() {
		var id = getParameterByName('page');
		console.log("Get byID: " + id);

		if (id != null) {
			$http.get(urlSheetMusic + "getSheetmusicBySheetID/" + id).success(
					function(response) {
						console.log(response);
						$scope.showLoader = false;

						$scope.Sheetmusic = response;

						// load pdf
						var data = atob(response.filePdf);
						pdfAsArray = new Array(data.length);
						for (var i = 0; i < data.length; i++) {
							pdfAsArray[i] = data.charCodeAt(i);
						}

						var pdfUint = new Uint8Array(pdfAsArray);
						// loading the first page of PDF
						loadCanvas(actualPage, pdfUint);

						// load audio
						var audiodata = atob(response.fileSound);
						audioArray = new Array(audiodata.length);
						for (var i = 0; i < audiodata.length; i++) {
							audioArray[i] = audiodata.charCodeAt(i);
						}
						var file = new Blob([ new Uint8Array(audioArray) ]);
						var link = document.createElement('a');
						link.href = window.URL.createObjectURL(file);
						console.log(link.href);

						var audio = document.getElementById('audio');
						audio.src = link.href;
						audio.load();
						// call this to just preload the audio without playing
						// audio.play();

						// Loading datas about sheet music
						var d = new Date(response.uploadDate);
						dformat = [ (d.getMonth() + 1).padLeft(),
								d.getDate().padLeft(), d.getFullYear() ]
								.join('-') + ' ' + [ d.getHours().padLeft(),d.getMinutes().padLeft(),d.getSeconds().padLeft() ].join(':');

						$scope.Sheetmusic.uploadDate = dformat;

						//time array
						audio.addEventListener('play', function(e) {
							timeInOnePage = audio.duration / maxPageNumber;
							console.log(timeInOnePage);
							console.log(maxPageNumber);
							
							timeArray = [timeInOnePage];
							for (var i = 1; i < maxPageNumber; i++) {
								timeArray[i] = timeArray[i-1]+timeInOnePage;
							}
							console.log(timeArray);
							
						}, false);
						
						var prevPage = 1;
						audio.addEventListener('timeupdate', function(e) {
							$("#currentTime").text(audio.currentTime + ".sec");

							var pages = getIndexOfTimeArrayCurrentTime(timeArray, audio.currentTime, prevPage);
							//console.log("Pageindex: " + pages["0"].prev+ " next: "+ pages["0"].next + "; time: " + audio.currentTime);
							prevPage = pages["0"].next;
							if (pages["0"].next != pages["0"].prev){
								clearCanvas();
								loadCanvas(pages["0"].next, pdfUint);
							}
	
						}, false);
					});
		} else {
			$scope.showLoader = false;
			alert("Sheet music not selected!");
			$window.location = 'sheetmusic.html';
		}
	}

	$scope.load = function() {
		// var x = document.getElementById("audio").duration;
		// $scope.Sheetmusic.duration = x+".sec";
		// $("#audioDuration").text(x+".sec");
		// alert(x);
	}
	$scope.downloadSheetMusic = function() {
		downloadFile(pdfAsArray, "SheetMusic.pdf");
	}

	$scope.downloadSoundFile = function() {
		downloadFile(audioArray, "SoundFile.mp3");
	}

	$scope.palyMidiMusic = function() {
		linkToMusic = link.href;
		MIDIjs.play(linkToMusic);

		// midi file
		// link.download = "file.mid";
		// link.click();

		// MIDIjs.play(link.href);

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

/**
 * Get page number by Time
 * 
 * @param timeArray
 * @param currentTime
 * @param prevPage
 * @returns {Array} - current page and the previous page
 */
function getIndexOfTimeArrayCurrentTime(timeArray, currentTime, prevPage){
	for (var int = 1; int < timeArray.length; int++) {
		if (currentTime > timeArray[int-1] && currentTime < timeArray[int]){
			return [{prev : prevPage, next : int+1}];
		}
	}
	return [{prev:prevPage, next:1}];
}


function loadAudioDuration() {
	var audio = document.getElementById("audio");
	// $scope.Sheetmusic.duration = audio.duration;
	$("#audioDuration").text(audio.duration + ".sec");
	$("#currentTime").text(audio.currentTime + ".sec");
	
	// currentTime = $("#currentTime").val();
}
/**
 * Loading the sheet music in canvas with
 * 
 * @param page -
 *            number
 * @param pdfAsArray -
 *            pdf File
 */
function loadCanvas(page, pdfAsArray) {
	var pdf = PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		maxPageNumber = pdf.numPages;
		$("#pdfLength").text(maxPageNumber);
		
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

function clearCanvas(){
	var canvas = document.getElementById('pdfCanvas');
	var context = canvas.getContext('2d');
	context.clearRect(0, 0, canvas.width, canvas.height);
	console.log("Clear canvas");
}


/**
 * Downloading a file with parameters
 * 
 * @param file
 * @param filetype
 */
function downloadFile(file, filetype) {
	if (file != null) {
		var blob = new Blob([ new Uint8Array(file) ]);
		var link = document.createElement('a');
		link.href = window.URL.createObjectURL(blob);
		link.download = filetype;
		link.click();
	} else {
		alert("File not loaded!");
	}
}

/**
 * 
 * @param name
 * @param url
 * @returns Parameter value by name
 */
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
