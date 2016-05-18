
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
		// get the sheetmusic ID from URL
		var id = getParameterByName('page');
		
		if (id != null) {
			$http.get(urlSheetMusicView + "getSheetmusicBySheetID/" + id).success(function(response) {

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
					
					//pdf feldolgozas
					self.getDataFromPDF(pdfUint).then(function(result) {
					    
						console.log(result);
					    
					    for (var i = 0; i < result.length; i++) {
//							console.log(result[i]);
							processedData = buildBareNumbersWithPosition(result[i]);
							console.log(processedData);
					    }
						
//						angular.forEach(result, function(value,index){
//			                console.log(value[index]);
			                
//			                angular.forEach(value, function(v,i){
//				                console.log(i);
//				            });
//						});
						
//					    for (var key in result) {
//							if (result.hasOwnProperty(key)) {
//								console.log(key + " -> " + result[key]);
//								Object.keys(result[key]).forEach(function(k) {
//							        console.log(key);
//							    });
//							}
//				    	}
					    
					      
					});
					
					///////////////////

					// load audio
					var audiodata = atob(response.fileSound);
					audioArray = new Array(audiodata.length);
					for (var i = 0; i < audiodata.length; i++) {
						audioArray[i] = audiodata.charCodeAt(i);
					}
					var file = new Blob([ new Uint8Array(audioArray) ]);
					var link = document.createElement('a');
					link.href = window.URL.createObjectURL(file);
					//console.log(link.href);

					var audio = document.getElementById('audio');
					audio.src = link.href;
					audio.load();
					// call this to just preload the audio without playing
					// audio.play();
					
					
					//GET comments on the sheetmusic
					var smId = response.sheetMusicId;
					$scope.loadComments(smId);
					
					// Sheetmusic its Favorite of User's
					$scope.isFavoriteUserSheetmusic(smId);
					
					//time array - loading 
					audio.addEventListener('play', function(e) {
						timeInOnePage = audio.duration / maxPageNumber;
						
						timeArray = [timeInOnePage];
						for (var i = 1; i < maxPageNumber; i++) {
							timeArray[i] = timeArray[i-1]+timeInOnePage;
						}
						//console.log(timeArray);
						
					}, false);
					
					//when need to change the page - listen this with EventListener
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
	 * Verify the sheet music is favorite of user
	 */
	$scope.isFavoriteUserSheetmusic = function(smId) {
		$http.get(urlSheetMusicView + "favorite/isFavoriteUserSheetmusic/"+smId).success(function(response) {
			if (response == "false"){
				console.log(smId + ": favorite of user");
				$scope.addedToFavorite = true;
			}else{
				console.log(smId + ": not favorite of user");
				$scope.addedToFavorite = false;
			}
		});
	}
	
	
	/**
	 * Save sheetmusic to user's favorite
	 */
	$scope.saveToFavorites = function() {
		console.log("Save " + $scope.Sheetmusic.sheetMusicId);
		
		$http.put(urlSheetMusicView + "favorite/addToFavorites/" + $scope.Sheetmusic.sheetMusicId)
		.success(function(response) {
			$scope.isFavoriteUserSheetmusic($scope.Sheetmusic.sheetMusicId);
		});
	}
	
	
	/**
	 * Save sheetmusic to user's faavorite
	 */
	$scope.deleteFromFavorites = function() {
		console.log("Delete " + $scope.Sheetmusic.sheetMusicId);
		 $http({ 
			 url: urlSheetMusicView+'favorite/deleteFromFavorites/'+ $scope.Sheetmusic.sheetMusicId, 
             method: 'DELETE', 
		 }).then(function(response) {
			 $scope.isFavoriteUserSheetmusic($scope.Sheetmusic.sheetMusicId);
		 });
		
	}
	
	/**
	 * Loading the comments, when page is initialized
	 */
	$scope.loadComments = function(smId) {
		$http.get(urlSheetMusicView + "getComments/"+smId).success(function(response) {
			$scope.loadCommentInformations(response);
		});
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
			
			$scope.loadCommentInformations(response);

		});
		
	}
	
	/**
	 * Loading the response (comment informations) to view
	 */
	$scope.loadCommentInformations = function(response){
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
	}
	
	/**
	 * Download the files
	 */
	$scope.downloadSheetMusic = function() {
		downloadFile(pdfAsArray, "SheetMusic.pdf");
	}

	$scope.downloadSoundFile = function() {
		downloadFile(audioArray, "SoundFile.mp3");
	}
}
//////////////////////////////////////
//////////// Functions ///////////////
//////////////////////////////////////

/**
 * Processing PDF 
 * @param data pdf stream
 * @returns pdf as array - text, only numbers
 * 			array - an element on a page
 */
function getDataFromPDF(data) {
	return PDFJS.getDocument(data).then(function(pdf) {
        var pages = [];
        for (var i = 0; i < pdf.numPages; i++) {
            pages.push(i);
        }
        
        return Promise.all(pages.map(function(pageNumber) {
            return pdf.getPage(pageNumber + 1).then(function(page) {
                return page.getTextContent().then(function(textContent) {
                    return textContent.items.map(function(item) {
//                    	console.log(item);
                    	
                    	//select the bars(beat) marker numbers
                    	if (!isNaN(item.str) && item.height > 7.0 && item.height < 9.0){
                        	console.log(item);
                        	
                        	return [pageNumber + 1, item.str, item.transform[4], item.transform[5]+"\n\r"];
                    	}
                    }).join(' ');
                });
            });
        })).then(function(pages) {
        	return pages;//pages.join("\r\n");
        });
    });
    
}


/**
 * Parsing and filtering the data with page, bare numbers and positions on pdf 
 * 
 * @param resultFromPdf
 * @returns JSON with parsed data
 */
function buildBareNumbersWithPosition(resultFromPdf) {
	var endLine = resultFromPdf.split("\n\r");
	var prevNumber = 0;
	var jsonData = {};
	for (var i = 0; i < endLine.length; i++) {
		var splitComma = endLine[i].split(",");
//		console.log("str:"+splitComma[1]+ " prev:"+prevNumber);
//		console.log(parseInt(splitComma[1]) > prevNumber);
		
		if (parseInt(splitComma[1]) > prevNumber){
			console.log("page:"+splitComma[0] + "str: "+splitComma[1] + " x:"+splitComma[2]+ " y:"+splitComma[3]);
			jsonData[i] = {page:splitComma[0] , str:splitComma[1], x:splitComma[2], y:splitComma[3]};
		}
		
		if(splitComma[1] != undefined){
			prevNumber = splitComma[1];
		}
	}
	return jsonData;
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

/**
 * Clear the canvas surface (before repaint)
 */
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
function getParameterByName(name) {
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
