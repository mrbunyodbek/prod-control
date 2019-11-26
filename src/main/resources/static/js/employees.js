var app = angular.module('productionEmpControl', []);

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

    $scope.deleteEmployee = function ($id) {
        $http({
            method: "GET",
            url: "/employees/delete/" + $id
        }).then(function (response) {
            $scope.employees = response.data;
        });
    };

    $scope.printVal = function () {
        $scope.item = $scope.employee.experience;
    };
});