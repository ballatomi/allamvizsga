'use strict';

/* Services */

var services = angular.module('ngMusicApp.services', [ 'ngResource' ]);

services.factory('UserFactory', function($resource) {

	return $resource('/BscProject/rest/login/log', {}, {
		query : {
			method : 'GET',
			params : {}
		}
	})

});
