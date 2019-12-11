var app = angular.module("productionSalaryControl", []);

app.controller('SalaryController', function ($scope, $http) {
    $scope.collection = [];
    $scope.filter = {
        "start": null,
        "end": null
    };

    $scope.sendQueryForFilter = function () {

        $scope.filter.start = $scope.start;
        $scope.filter.end = $scope.end;

        $http({
            method: "POST",
            url: "/salaries/filter",
            data: $scope.filter
        }).then(function (response) {
            $scope.collection = response.data;
        });
    };

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