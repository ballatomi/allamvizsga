'use strict';

/* Filters */

angular.module('ngMusicApp.filters', []).filter('interpolate',
		[ 'version', function(version) {
			return function(text) {
				return String(text).replace(/\%VERSION\%/mg, version);
			}
		} ]).

filter('datetime', function($filter) {
	return function(input) {
		if (input == null) {
			return "";
		}
		var _date = $filter('date')(new Date(input), 'MM dd yyyy - HH:mm:ss');
		return _date;

	};
}).

filter('iif', function () {
   return function(input, trueValue, falseValue) {
        return input ? falseValue : trueValue ;
   };
});
