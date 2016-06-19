/**
 * Controlling user profile page
 */

function UserPersonalController($scope, $http, $location, $window) {
	
	$scope.init = function() {
		
		$http.get(urlSheetMusicView + "favorite/getUsersFavorite").success(function(response) {
			resp = response;
			
			if (resp == "null"){
				$scope.not_found = true;
			} else {
				$scope.not_found = false;
				if (resp.sheetmusicFavorite instanceof Array){
					$scope.loadSheetMusic(resp.sheetmusicFavorite);
				} else {
					
					var list = [resp];
					console.log(list);
					
					var data = atob(list[0].sheetmusicFavorite.sheetMusic.filePdf);
					var pdfAsArray = new Array(data.length);
					for (var i = 0; i < data.length; i++) {
						pdfAsArray[i] = data.charCodeAt(i);
					}
					var pdfUint = new Uint8Array(pdfAsArray);
					var id = list[0].sheetmusicFavorite.sheetMusic.sheetMusicId;
				
					
					loadCanvasElements(1, pdfUint, id);
				
					$scope.Sheetmusic = list[0];
					console.log($scope.Sheetmusic);
					
				}
			}
		});
		
		$http.get(urlLogin + "loggedIn").success(function(response) {
			console.log(response);
			$scope.activeUser = response;
		});
		
		$scope.loadSheetMusicByUser();
		
	}
	
	/**
	 * Load sheet music uploaded by logged in user
	 */
	$scope.loadSheetMusicByUser = function(){
		$http.get(urlSheetMusic + "getSheetMusicByUser/").success(function(resp) {

			console.log(resp);
			if (resp == "null") {
				$scope.notExitst = true;
			} else {
				$scope.sheetMusicAddedByUser = resp.sheetMusic;
			}
		});
	}

	/**
	 * Load sheet music to surface
	 * 		- it works only for arrays, with more than 1 element
	 */
	$scope.loadSheetMusic = function(response){
		$scope.Sheetmusic = response;
		console.log($scope.Sheetmusic)

		var respLength = response.length;
		
		for (var respInd = 0; respInd < respLength; respInd++) {

			var data = atob(response[respInd].sheetMusic.filePdf);
			var pdfAsArray = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				pdfAsArray[i] = data.charCodeAt(i);
			}
			var pdfUint = new Uint8Array(pdfAsArray);
			var id = response[respInd].sheetMusic.sheetMusicId;
			
			loadCanvasElements(1, pdfUint, id);
		}	
	}

}

/**
 *  load canvas a Picture
 * @param page
 * @param pdfAsArray
 * @param musicId
 */
function loadCanvasElementsFavorites(page, pdfAsArray, musicId) {
	
	PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		pdf.getPage(page).then(function(page) {
			var maxPageNumber = pdf.numPages;
			PDFDocumentProxy_getData();

			console.log(pdf.fontCache);
			PDFPageProxy_getTextContent();
			
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
