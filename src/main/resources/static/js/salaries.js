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

            for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].details.length > 0) {
                    $scope.collection.push(response.data[i]);
                    console.log(response.data[i]);
                }
            }

        });
    };

    $scope.saveToFile = function () {

        $scope.filter.start = $scope.start;
        $scope.filter.end = $scope.end;
        $http({
            method: "POST",
            url: "/salaries/save-to-file",
            data: $scope.filter
        }).then(function (response) {

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