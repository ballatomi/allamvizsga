'use strict';

/**
 * Controlling load sheet music to listing
 */

var urlSheetMusic = "http://localhost:8080/BscProject/rest/sheet/";
var pdfAsArray;
var instrumentListId = [];
var instrumentSheetMusicListlength = 0;
var instrumentSheetMusicList;

//var app = angular.module('ngMusicApp.listController', []);
//app.controller('ctrlSheetViewer', function($scope, $http, $location, $window) {
function ctrlSheetViewer($scope, $http, $location, $window) {

	$scope.showLoader = true;
	/**
	 * initialize sheetMusci.html page
	 */
	$scope.init = function() {
		$http.post(urlSheetMusic + "getAllInstrumentSheetmusic").success(function(response) {
			instrumentSheetMusicList = response.instrumentSheetmusic;
			instrumentSheetMusicListlength = response.instrumentSheetmusic.length;
		
			$http.post(urlSheetMusic + "getAllSheetMusic").success(function(response) {
				console.log(response.sheetMusic);
				
				$scope.loadSheetMusic(response);
				
				$scope.showLoader = false;
			});
		});
		
		$http.get(urlSheetMusic + "getAllInstrument").success(function(response) {
				$scope.instrumentsList = response.instrument;
		});
	}
	

	
	/**
	 * Load sheet music to surface
	 * 		- it works only for arrays, with more than 1 element
	 */
	$scope.loadSheetMusic = function(response){
		$scope.Sheetmusic = response.sheetMusic;
		
		//console.log(response.sheetMusic[2].filePdf); - 
		// pdf json-ban byte-onkent
		// Json-bol file-ra alakitas
		var respLength = response.sheetMusic.length;
		
		for (var respInd = 0; respInd < respLength; respInd++) {
			//console.log(response.sheetMusic[respInd].uploadDate);
			var d = new Date(response.sheetMusic[respInd].uploadDate);
			var dformat = [ (d.getMonth()+1).padLeft(), d.getDate().padLeft(),d.getFullYear()].join('-')+
	                    ' ' +
	                  [ d.getHours().padLeft(), d.getMinutes().padLeft(), d.getSeconds().padLeft()].join(':');
			$scope.Sheetmusic[respInd].uploadDate = dformat;
			
			//console.log(response.sheetMusic[respInd].name);
			
			var data = atob(response.sheetMusic[respInd].filePdf);
			var pdfAsArray = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				pdfAsArray[i] = data.charCodeAt(i);
			}
			var pdfUint = new Uint8Array(pdfAsArray);
			var id = response.sheetMusic[respInd].sheetMusicId;
			console.log("ID: " + id);
			
			loadCanvasElements(1, pdfUint, id);
			$scope.Sheetmusic[respInd].length = response.sheetMusic[respInd].length;
			
			//load instruments
			var ins = "";
			for (var int = 0; int < instrumentSheetMusicListlength; int++) {
				if (response.sheetMusic[respInd].sheetMusicId == instrumentSheetMusicList[int].sheetMusic.sheetMusicId){
					ins += instrumentSheetMusicList[int].instrument.name + ", ";
				}
			}
			$scope.Sheetmusic[respInd].instrument = ins;
		}	
	}
	
	$scope.redirectSheetMusicViewer = function(id) {
		console.log("ID:" + id);
		//console.log($location.absUrl());
	}
	
	/**
	 * Search sheet music by pattern
	 */
	$scope.searchByName = function(searchText) {
		$http.get(urlSheetMusic + "getSheetMusicByPattern/" + searchText).success(function(resp) {
				//console.log(response.sheetMusic);

				//if is not an array convert it to array
				//list = response == null ? [] : (response instanceof Array ? response : [response]);
				console.log(resp);
				
				if (resp === null){
					//alert('Not match!');
					$scope.not_found = true;
				} else {
					$scope.not_found = false;
					if (resp.sheetMusic instanceof Array){
						$scope.loadSheetMusic(resp);
					} else {
						
						var list = [resp];
						
						console.log(list);
						
						var d = new Date(list[0].sheetMusic.uploadDate);
						var dformat = [ (d.getMonth()+1).padLeft(), d.getDate().padLeft(),d.getFullYear()].join('-')+
				                    ' ' +
				                  [ d.getHours().padLeft(), d.getMinutes().padLeft(), d.getSeconds().padLeft()].join(':');
						list[0].sheetMusic.uploadDate = dformat;
						
						var data = atob(list[0].sheetMusic.filePdf);
						var pdfAsArray = new Array(data.length);
						for (var i = 0; i < data.length; i++) {
							pdfAsArray[i] = data.charCodeAt(i);
						}
						var pdfUint = new Uint8Array(pdfAsArray);
						var id = list[0].sheetMusic.sheetMusicId;
						
						loadCanvasElements(1, pdfUint, id);
						
						//load instruments
						var ins = "";
						for (var int = 0; int < instrumentSheetMusicListlength; int++) {
							if (list[0].sheetMusic.sheetMusicId == instrumentSheetMusicList[int].sheetMusic.sheetMusicId){
								ins += instrumentSheetMusicList[int].instrument.name + ", ";
							}
						}
						list[0].sheetMusic.instrument = ins;
					
						$scope.Sheetmusic = list[0];
					}
				}
			});
	}
	
	/**
	 * Search sheet music by selected instrument instrument
	 */
	$scope.searchByInstrument = function(id) {
		console.log(id);
		$http.get(urlSheetMusic + "getSheetMusicByInstrument/" + id).success(function(response) {
				console.log(response);

				if (response === null){
					$scope.not_found = true;
				} else {
					$scope.not_found = false;
					if (response.sheetMusic instanceof Array){ //verify the response isArray (contains more than 1 elemnt) - for angularJS
						$scope.loadSheetMusic(response);
					} else {
						
						var list = [response];
						
						console.log(list);
						
						var d = new Date(list[0].sheetMusic.uploadDate);
						dformat = [ (d.getMonth()+1).padLeft(), d.getDate().padLeft(),d.getFullYear()].join('-')+
				                    ' ' +
				                  [ d.getHours().padLeft(), d.getMinutes().padLeft(), d.getSeconds().padLeft()].join(':');
						list[0].sheetMusic.uploadDate = dformat;
						
						var data = atob(list[0].sheetMusic.filePdf);
						var pdfAsArray = new Array(data.length);
						for (var i = 0; i < data.length; i++) {
							pdfAsArray[i] = data.charCodeAt(i);
						}
						var pdfUint = new Uint8Array(pdfAsArray);
						var id = list[0].sheetMusic.sheetMusicId;
						
						loadCanvasElements(1, pdfUint, id);
						
						//load instruments
						var ins = "";
						for (var int = 0; int < instrumentSheetMusicListlength; int++) {
							if (list[0].sheetMusic.sheetMusicId == instrumentSheetMusicList[int].sheetMusic.sheetMusicId){
								ins += instrumentSheetMusicList[int].instrument.name + ", ";
							}
						}
						list[0].sheetMusic.instrument = ins;
					
						$scope.Sheetmusic = list[0];
					}
				}
			});
	}
}




/**
 * convert datetime to simple format
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
function loadCanvasElements(page, pdfAsArray, musicId) {
	PDFJS.getDocument(pdfAsArray).then(function(pdf) {
		pdf.getPage(page).then(function(page) {
			var maxPageNumber = pdf.numPages;
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
