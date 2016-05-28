
/**
 * Controlling sheetMusicViewer.html page
 * 
 * 	
 * 	Loading sheet music and informations about, sound file, comments on sheet music
 *  and write new comments
 *  Downloading sheet music, and sound file, set sheet music to favorites
 *  
 *  Playing sound file change automatically pages of sheet music, 
 *  and follow the playing with an indicator 
 *  
 */

var linkToMusic;
var pdfAsArray;
var audioArray;
var maxPageNumber;
var audio;

var actualPage = 1;
var ellapsedTime = 0;
var audioDuration;

var tactsNumber = 0;
var timeInOneTact;
var tactArray = [];

var urlSheetMusicView = "http://localhost:8080/BscProject/rest/sheet/";

function ViewController($scope, $http, $location, $window) {

	$scope.showLoader = true;
	$scope.init = function() {
		// get the sheetmusic ID from URL
		var id = getParameterFromURLByName('page');
		
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
					
					processedData = [];
					/**
					 * Processing pdf file, getting data (line informations, 
					 * numbers in each line, and coordinates) 
					 */
					self.getDataFromPDF(pdfUint).then(function(result) {
					    
					    for (var i = 0; i < result.length; i++) {
							processedData[i] = buildTactNumbersWithPosition(result[i]);
					    }
					    processedData = addFirstLineToLines_CalculateHight(processedData);

					    processedData = divideLines(processedData);
					    console.log(processedData);

						console.log("Tacts number " + tactsNumber);
						
					});
					
					///////////////////

					/**
					 * Loading sound file from response
					 * converting to Blob (format for binary data)
					 * with Uint8Array (is similar with Array) where each item is an 8 bit (unsigned integer)
					 */
					var audiodata = atob(response.fileSound);
					audioArray = new Array(audiodata.length);
					for (var i = 0; i < audiodata.length; i++) {
						audioArray[i] = audiodata.charCodeAt(i);
					}
					var file = new Blob([ new Uint8Array(audioArray) ]);
					var link = document.createElement('a');
					link.href = window.URL.createObjectURL(file);
					
					var audio = document.getElementById('audio');
					audio.src = link.href;
					audio.load(); // call this to just preload the audio without playing
					// audio.play();

					
					//GET comments by the sheetmusic
					var smId = response.sheetMusicId;
					$scope.loadComments(smId);
					
					// Sheetmusic it's Favorite of User's
					$scope.isFavoriteUserSheetmusic(smId);
					
					/**
					 *  Event listener to loading sound file  
					 *  make an array with time for each tact
					 *  
					 *  time during in a tact - 
					 *  calculating by length of audio file 
					 *  and maximum number of tacts
					 *  
					 *  time = sound.length / maxTactNumber
					 *  	
					 */
					audio.addEventListener('play', function(e) {
						
						var timeInOneTact = audio.duration / tactsNumber;
						console.log("timeInOneTact: "+timeInOneTact);
						
						tactArray = [0, timeInOneTact];
						for (var i = 1; i < tactsNumber+1; i++) {
							tactArray[i] = tactArray[i-1] + timeInOneTact;
						}
						console.log(tactArray);
						
					}, false);
					
					//when need to change the page - listen this with EventListener
					var prevPage = 1;
					var previousTact = 0;
					var previousLine = 1;
					var actualTactInLine = 1;
					var isFirst = true;
					
					/**
					 * Handle the indicator on sheet music
					 * 
					 * put a rectangle to actually place 
					 * 
					 * - get actual tact by time
					 * - get actual line by tact
					 * - put rectangle to actual line 
					 * 
					 */
					
					audio.addEventListener('timeupdate', function(e) {
						$("#currentTime").text(audio.currentTime + ".sec");

						//megadok egy idot, ez alaljan visszad egy indexet ami a taktus szamaval egyezik meg
						//megkeresve az adott taktust kirajzoljuk az adott sorra
						
						///tacts (beats)
						var acutalTact = getTactNumberByTime(tactArray, audio.currentTime, previousTact);
//						console.log("Tact: " + acutalTact.prevTact + " - time: " + audio.currentTime);
						
						previousTact = acutalTact.tact;
						
						//print only the tact is changed
						if (acutalTact.tact != acutalTact.prevTact){
							console.log(acutalTact);
							
							var actualLine = getActualLineByTactNumber(processedData, acutalTact.prevTact);
							/* returned type example 
							 lineInfo: {page: "1" - page in pdf
									  	str: "5"  - line in page
										x: "36.87439815187499" - coordinates of line
										y: "575.2495112"
							}*/
//							console.log(actualLine);
							
							if (prevPage != parseInt(actualLine.lineInfo.page) && actualLine.lineInfo != 0){ // need to change page
								
								clearCanvas();
								loadCanvasPutRectangle(parseInt(actualLine.lineInfo.page), pdfUint, actualLine);
								
								previousLine = actualLine;
								prevPage = parseInt(actualLine.lineInfo.page);
							}

							if (actualLine.lineInfo != 0){ // paint rectangle
								console.log(actualLine);
							
								if (!isFirst){ 
									putRectangleToCanvas(previousLine.lineInfo.x, previousLine.lineInfo.y, previousLine.lineInfo.h,  "#FFFFFF", 0.4);
								}else{
									isFirst = false;
								}
								
								putRectangleToCanvas(actualLine.lineInfo.x, actualLine.lineInfo.y, actualLine.lineInfo.h, "#99ccff	", 0.4);
								previousLine = actualLine;
								
								prevPage = parseInt(actualLine.lineInfo.page);
								actualTactInLine = 1;
							}else {
// 							soronkent is vinni a mutatot								
//								if (previousLine!=1){
//									var x = previousLine.lineInfo.rect[actualTactInLine-1];
//									var width = previousLine.lineInfo.rect[actualTactInLine];
//	
//									var y = previousLine.lineInfo.y;
//									var height = previousLine.lineInfo.h;
//									
//									console.log("x:"+x+" y:"+y+" w:"+width+" h:"+height);
//									putRectangleToLine(x, y, width, height);
//									
//									actualTactInLine++;
//								}
							}
						}
						
					}, false);
					
					
				} else {
					$scope.errorMessage = "Sheetmusic not found!"
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
	
	$scope.previousPageInPdf = function() {
		actualPage--;
		var pdfUint = new Uint8Array(pdfAsArray);
		loadCanvas(actualPage, pdfUint);
	}
	
	$scope.nextPageInPdf = function() {
		actualPage++;
		var pdfUint = new Uint8Array(pdfAsArray);
		loadCanvas(actualPage, pdfUint);
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
		//download only mp3 format
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
                    	//select the bars(beat) marker numbers
                    	//console.log(item);
                    	if (!isNaN(item.str) && item.height > 3.0 && item.height < 9.0){
                        	//console.log(item);
                        	return [pageNumber + 1, item.str, item.transform[4], item.transform[5]+"\n\r"];
                    	}
                    }).join(' ');
                });
            });
        })).then(function(pages) {
        	return pages;
        });
    });
    
}


/**
 * Parsing and filtering the data with page, tact numbers and positions on pdf 
 * 
 * page, tact, coordinates x,y \n\r
 * example: 2, 2, 28.341294251625, 44.9934032000001\n\r
 * 
 * @param resultFromPdf
 * @returns JSON with parsed data
 */
function buildTactNumbersWithPosition(resultFromPdf) {
	var endLine = resultFromPdf.split("\n\r");
	
	var prevNumber = 0;
	var jsonData = [];
	for (var i = 0; i < endLine.length; i++) {
		var splitComma = endLine[i].split(",");
		
		
		if (parseInt(splitComma[1]) > prevNumber) { //the actual number of tact must be grather than previous
			jsonData[i] = {page : splitComma[0], str : splitComma[1], x : splitComma[2], y : splitComma[3], h : "100"};
			tactsNumber = parseInt(splitComma[1]);
		}
		if(splitComma[1] != undefined) { //save the tact number from previous line 
			prevNumber = splitComma[1];
		}
	}
	
	return jsonData;
}

/**
 * The first line is not signed with tact number, therefore need to calculate this poition.
 * 
 * Set line hight by to each line by neighbor (before and acutal) 
 * calculated distances between lines.
 * 
 * @param processedData
 * @returns
 */
function addFirstLineToLines_CalculateHight(processedData){
	var first = processedData[0][0];
	 
	console.log(processedData);
	 var firstLineYPos = "700";
	 if (processedData[0].length == 0){ // only one line exist in a page
	 	 processedData[0][0] = {page:1, str:1, x:processedData[1][0].x, y:"150", h:"100"};
	 } else {
			try {
				 if (processedData[0][0].x != undefined && processedData[0][1].y != undefined){ // exists two line in a page
					 firstLineYPos = parseInt(processedData[0][0].y) + (parseInt(processedData[0][0].y) - parseInt(processedData[0][1].y));
				 }
			} catch (e) {
				 firstLineYPos = parseInt(processedData[0][0].y) + (parseInt(processedData[0][0].y)) - 100;
			}
	
		 for (var j = processedData[0].length; j > 0 ; j--) {
			 processedData[0][j] = processedData[0][j-1];
		 }
		 processedData[0][0] = {page:"1", str:"1", x:processedData[0][0].x, y:""+firstLineYPos+"", h:"100"};
	 }
	 
	 
	 /**
	  * set high to each line
	  * calculated distances between lines
	  */
	 for (var i = 0; i < processedData.length; i++) {
		for (var j = 1; j < processedData[i].length; j++) {
				processedData[i][j-1].h = ""+((processedData[i][j-1].y - processedData[i][j].y)-30)+""; 
		}
		if (processedData[i].length == 1){ // exist only one line in a page
			processedData[i][j-1].h = "400"; 
		}
	}
	 
	 console.log(processedData);
	 return processedData;
}

/**
 * Get actual tact number by time 
 * @param tactArray
 * @param currentTime
 * @param previousLine
 * @returns
 */
function getTactNumberByTime(tactArray, currentTime, previousTact){
	currentTime = currentTime -1; 
	for (var i = 1; i < tactArray.length; i++) {
		if (currentTime >= tactArray[i-1] && currentTime < tactArray[i]){
			return { tact : i+1, prevTact : previousTact };
		}
	}	
	return { tact : 1, prevTact : previousTact };
}


/**
 * 
 * Get actual line informations by tact number
 * @param processedData
 * @param tact
 * @returns
 */
function getActualLineByTactNumber(processedData, tact){
	
	for (var j = 0; j < processedData.length; j++) {
		for (var int = 0; int < processedData[j].length; int++) {
			
//			if (tact > parseInt(processedData[j][int].str) && tact < parseInt(processedData[j][int].str)  ) {
			if (tact == parseInt(processedData[j][int].str)){
				return { lineInfo:processedData[j][int] };
			}
		}
	}
	
	return { lineInfo : 0 };
}

/**
 * Divide the lines by number of tacts to equal parts
 * @param processedData
 * @returns
 */
function divideLines(processedData){
	
	for (var i = 0; i < processedData.length; i++) {
		for (var j = 1; j < processedData[i].length; j++) {
//			console.log(j+": "+parseInt(processedData[i][j].str) +" "+ (j-1)+": "+parseInt(processedData[i][j-1].str));
			
			var numOfTacts = parseInt(processedData[i][j].str) - parseInt(processedData[i][j-1].str)
			var rate = 550 / numOfTacts;
			
//			console.log(rate);
			var dataLines = [];
			dataLines[0] = 0;
			dataLines[1] = rate;
			for (var tact = 2; tact < numOfTacts+1; tact++) {
				dataLines[tact] = (dataLines[tact-1] + rate);
			}
			processedData[i][j-1].rect = dataLines;
		}
	}
//	console.log(processedData);
	return processedData;
}

/**
 * Megjelenites, hol tart a lejatszas
 * Fill a rectangle to show/follow playing music
 * @param page
 * @param pdfAsArray
 */
function putRectangleToCanvas(x, y, h, color, oppacity) {
	var canvas = document.getElementById('pdfCanvas');
	var context = canvas.getContext('2d');
	
	context.fillStyle = color;
	context.globalAlpha = oppacity;

	var rect = canvas.getBoundingClientRect();
//	context.fillRect(x, rect.height-y, rect.width-70, 100);
	
	context.fillRect(x, rect.height-y, rect.width-70, h);
	context.stroke();
}



function putRectangleToLine(x, y, w, h) {
	var canvas = document.getElementById('pdfCanvas');
	var context = canvas.getContext('2d');
	context.fillStyle="#aaff80";
	context.globalAlpha = 0.4;
	
	console.log("width:" + rect.width);
	
	var rect = canvas.getBoundingClientRect();
//	context.fillRect(x, rect.height-y, rect.width-70, 100);
//	console.log("width:" + rect.width);
	context.fillRect(x, rect.height-y, w, h);
	
	context.stroke();
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
 * @param page - number
 * @param pdfAsArray - pdf File
 */

function loadCanvas(page, pdfAsArray) {
	var pdf = PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		maxPageNumber = pdf.numPages;
		if (page <= 0){
			page = 0;
		}
		if (page >= maxPageNumber){
			page = maxPageNumber;
		}
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


function loadCanvasPutRectangle(page, pdfAsArray, actualLine) {
	var pdf = PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		maxPageNumber = pdf.numPages;
		if (page <= 0){
			page = 0;
		}
		if (page >= maxPageNumber){
			page = maxPageNumber;
		}
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
			
			//put rectangle
			putRectangleToCanvas(actualLine.lineInfo.x, actualLine.lineInfo.y, actualLine.lineInfo.h, "#99ccff	", 0.4);
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
 * 
 *  http://stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript
 *
 * @param name
 * @param url
 * @returns Parameter value by name
 */
function getParameterFromURLByName(name) {
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
