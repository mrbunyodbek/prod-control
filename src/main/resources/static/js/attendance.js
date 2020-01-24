var app = angular.module("productionAttendanceControl", []);

app.controller('AttendanceController', function ($scope, $http) {
    $scope.collection = [];

    $http({
        method: "GET",
        url: "/attendance/get",
        data: $scope.filter
    }).then(function (response) {
        $scope.collection = response.data;
        console.log($scope.collection);
    });

    // $scope.sendQueryForFilter = function () {
    //
    //     $scope.filter.start = $scope.start;
    //     $scope.filter.end = $scope.end;
    //
    //     $http({
    //         method: "GET",
    //         url: "/attendance/get",
    //         data: $scope.filter
    //     }).then(function (response) {
    //         $scope.collection = response.data;
    //         console.log($scope.collection);
    //     });
    // };

    $scope.getDate = function () {
        console.log($scope.date);
    }
});

app.controller('RowController', function ($scope, $http) {
    $scope.toggleRow = function () {
        $scope.selected = !$scope.selected;
    };

    $scope.isSelected = function (i) {
        return $scope.selected;
    };
});