
/**
 * Controlling sheetMusicViewer.html page
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

var urlSheetMusicView = "http://localhost:8080/BscProject/rest/sheet/";

function ViewController($scope, $http, $location, $window) {

	$scope.showLoader = true;
	$scope.init = function() {
		var id = getParameterByName('page');
		console.log("Get byID: " + id);
		
		if (id != null) {
			$http.get(urlSheetMusicView + "getSheetmusicBySheetID/" + id).success(
					function(response) {
						console.log(response);
						if (response != ""){
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
							
							
							//GET comments on the sheetmusic
							var smId = response.sheetMusicId;
							$http.get(urlSheetMusicView + "postComment/"+smId).success(function(response) {
								console.log(response);
	
								if (response != "null"){								
									//Show comment, only one or more
									$scope.commentsNumber = response.sheetmusicComment.length;
									if (response.sheetmusicComment.length === undefined){
										$scope.userFirstName = response.sheetmusicComment.user.userFirstName;
										$scope.userLastName = response.sheetmusicComment.user.userLastName;
										$scope.postedDate = response.sheetmusicComment.postedDate;
										
										$scope.comment = response.sheetmusicComment.comment;
										$scope.commentsNumber = 1;
										$scope.showMultiCommnet = false;
										$scope.showSingleCommnet = true;
									}else{
										$scope.sheetmusicComment = response.sheetmusicComment;
										$scope.showSingleCommnet = false;
										$scope.showMultiCommnet = true;
									}
								}else{
									$scope.commentsNumber = "No Comments Yet";
									$scope.showSingleCommnet = false;
									$scope.showMultiCommnet = false;
								}
							});
							
							//time array - when need to change the page
							audio.addEventListener('play', function(e) {
								timeInOnePage = audio.duration / maxPageNumber;
								
								timeArray = [timeInOnePage];
								for (var i = 1; i < maxPageNumber; i++) {
									timeArray[i] = timeArray[i-1]+timeInOnePage;
								}
								//console.log(timeArray);
								
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
						} else {
							$scope.errorMessage = "Sheetmusic not exists!"
							$scope.showLoader = true;
						}
					});
		} else {
			$scope.showLoader = false;
			$location.path('/sheetmusic');
		}
	}

	/**
	 * Save the comment
	 */
	$scope.saveComment = function() {
		console.log($scope.commentText);
		console.log($scope.Sheetmusic.sheetMusicId);

		var fd = new FormData();
		fd.append("id", parseInt($scope.Sheetmusic.sheetMusicId));
		fd.append("comment", $scope.commentText);
		
		$http.put(urlSheetMusicView + "postComment", fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(response) {
			if (response == null){
				alert("Only logged in users can write comments!");
			}
			console.log(response);
			
			if (response != null){
				
				$scope.commentsNumber = response.sheetmusicComment.length;
				if (response.sheetmusicComment.length === undefined){
					$scope.userFirstName = response.sheetmusicComment.user.userFirstName;
					$scope.userLastName = response.sheetmusicComment.user.userLastName;
					
					$scope.postedDate = response.sheetmusicComment.postedDate;

					$scope.comment = response.sheetmusicComment.comment;
					$scope.commentsNumber = 1;
					$scope.showMultiCommnet = false;
					$scope.showSingleCommnet = true;
					
				}else{
					$scope.sheetmusicComment = response.sheetmusicComment;
					$scope.showMultiCommnet = true;
					$scope.showSingleCommnet = false;
				}
			}else{
				$scope.commentsNumber = "No Comments Yet";
				$scope.showMultiCommnet = false;
				$scope.showSingleCommnet = false;
			}
		});
		
	}
	
	$scope.downloadSheetMusic = function() {
		downloadFile(pdfAsArray, "SheetMusic.pdf");
	}

	$scope.downloadSoundFile = function() {
		downloadFile(audioArray, "SoundFile.mp3");
	}
}

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

/**
 * Loading informations to view
 */
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
 * Get parameter from URL
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
