var app = angular.module("productionControl", []);

app.controller('EmployeeController', function ($scope, $http) {
    // CRUD
    $http({
        method: "GET",
        url: "/employees/get"
    }).then(function (response) {
        $scope.employees = response.data;
    });

    $scope.getEmployee = function ($id) {
        $http({
            method: "POST",
            url: "/employees/get/" + $id
        }).then(function (response) {
            $scope.employee = response.data;
        });
    };

    $scope.saveEmployee = function () {
        $http({
            method: "POST",
            url: "/employees/save",
            data: $scope.employee
        }).then(function (response) {
            $scope.employees = response.data;
        });
    };

    $scope.deleteEmployee = function ($res) {
        $http({
            method: "POST",
            url: "/employees/delete",
            data: $res
        }).then(function (response) {
            $scope.employees = response.data;
        });
    };
});