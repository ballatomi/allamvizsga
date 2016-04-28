/**
 * 
 */

function UserPersonalController($scope, $http, $location, $window) {
	
	$scope.init = function() {
		
		$http.get(urlSheetMusicView + "favorite/getUsersFavorite").success(function(response) {
			resp = response;
			
			if (resp === null){
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

//			getDataFromPDF(pdfUint);
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


/**
 * PDF feldolgozasa
 * @param data
 * @returns
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
                        return item.str;
                    }).join(' ');
                });
            });
        })).then(function(pages) {
        	console.log(pages);
        	return pages.join("\r\n");
        });
    });
}