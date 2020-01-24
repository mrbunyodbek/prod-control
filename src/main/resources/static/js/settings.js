var app = angular.module("settingsControl", []);
// var products = [];

app.controller('SettingsController', function ($scope, $http) {
    $scope.settings = {
        "startOfDay"    : null,
        "endOfDay"      : null
    };

    //CRUD
    $http({
        method: "GET",
        url: "/settings/get"
    }).then(function (response) {
        $scope.settings = response.data;
    });

    $scope.saveSettings = function () {
        $http({
            method: "POST",
            url: "/settings/save",
            data: $scope.settings
        }).then(function (response) {
            alert(response.data.message);
            $scope.settings = response.data.settings;
        });
    };
});