var app = angular.module("productionAttendanceControl", []);

app.controller('AttendanceController', function ($scope, $http) {
    $scope.collection = [];

    const date = new Date();
    const month = date.toLocaleString('default', { month: 'long' });

    $http({
        method: "GET",
        url: "/attendance/get/" + month
    }).then(function (response) {
        $scope.collection = [];
        $scope.createDaysForTable();
        $scope.collection = response.data;
    });

    $scope.getDate = function (months) {
        console.log(months);
        $http({
            method: "GET",
            url: "/attendance/get/" + months
        }).then(function (response) {
            $scope.collection = [];
            $scope.createDaysForTable();
            $scope.collection = response.data;
        });
    };

    $scope.createDaysForTable = function () {
        for (let i = 1; i < 32; i++) {
            $scope.days.push(i);
        }
    };
});