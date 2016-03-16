var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";
var pdfAsArray;
var instrumentListId = [];
var instrumentSheetMusicListlength = 0;

/**
 * Controlling load sheet music to listing
 */
var App = angular.module('controllSheetMusic', []);
App.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {

	$scope.showLoader = true;
	$scope.init = function() {
		
		$http.post(urlSheetMusic + "getAllInstrumentSheetmusic").success(function(response) {
			instrumentSheetMusicList = response.instrumentSheetmusic;
			instrumentSheetMusicListlength = response.instrumentSheetmusic.length;
		
			$http.post(urlSheetMusic + "getAll").success(function(response) {
				console.log(response.sheetMusic);
	
				$scope.Sheetmusic = response.sheetMusic;
				
				//console.log(response.sheetMusic[2].filePdf); - pdf json-ban
				// byte-onkent
				// Json-bol file-ra alakitas
				var respLength = response.sheetMusic.length;
				
				for (var respInd = 0; respInd < respLength; respInd++) {
					//console.log(response.sheetMusic[respInd].uploadDate);
					var d = new Date(response.sheetMusic[respInd].uploadDate);
					dformat = [ (d.getMonth()+1).padLeft(), d.getDate().padLeft(),d.getFullYear()].join('-')+
			                    ' ' +
			                  [ d.getHours().padLeft(), d.getMinutes().padLeft(), d.getSeconds().padLeft()].join(':');
					//console.log(dformat);
					$scope.Sheetmusic[respInd].uploadDate = dformat;
					
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
					
					//load instruments
					var ins = "";
					for (var int = 0; int < instrumentSheetMusicListlength; int++) {
						if (response.sheetMusic[respInd].sheetMusicId == instrumentSheetMusicList[int].sheetMusic.sheetMusicId){
							ins += instrumentSheetMusicList[int].instrument.name + ", ";
						}
					}
					$scope.Sheetmusic[respInd].instrument = ins;
				}
				$scope.showLoader = false;
			});
		});
	}	
	
	$scope.redirectSheetMusicViewer = function(id) {
		console.log("ID:" + id);
		//console.log($location.absUrl());
	}
	
});

/**
 * Controlling logout user
 */
App.controller('logoutController', function($scope, $http, $location, $window) {
	$scope.logout = function(user) {
		$http.get(urlLogin + "logout").success(function(response) {
			console.log(response);
			$window.location = '../';
		});
	};
});


/**
 * convert datetime
 */
Number.prototype.padLeft = function(base,chr){
   var  len = (String(base || 10).length - String(this).length)+1;
   return len > 0? new Array(len).join(chr || '0')+this : this;
}

/**
 *  load canvas a Picture
 * @param page
 * @param pdfAsArray
 * @param musicId
 */
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
