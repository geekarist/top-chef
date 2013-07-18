'use strict';

/* Controllers */

//angular.module('myApp.controllers', []).
//  controller('MyCtrl1', [function() {
//
//
//  }])
//  .controller('MyCtrl2', [function() {
//
//  }]);

function MyCtrl1($scope, $http) {
    $scope.showData = false;
    $scope.gridOptions = { data: 'games' };
    $scope.loadGames = function() {
        console.log($scope.gameConsole);
        var url = "/game?gameConsole=" + $scope.gameConsole
        $http.get(url).success(function(data) {
            $scope.games = data;
        })
    }
}