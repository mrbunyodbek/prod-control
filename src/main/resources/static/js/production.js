var app = angular.module("productionControl", []);

app.controller('RegisterProduction', function ($scope, $window, $http, myService) {

    // Employee list that will be showed in frontend
    $scope.performerList = [];
    $scope.performerListId = 1;

    $scope.collection = [];
    $scope.production = {};

    $http({
        method: "GET",
        url: "/production/collect-data"
    }).then(function (response) {
        $scope.employees = response.data.employees;
        $scope.products = response.data.products;

        // Setting default date
        $scope.date = new Date();

        // Defining cost variable to 0
        $scope.cost = 0;

        $scope.prf = {
            "tableId": $scope.performerListId,
            "employee": null,
            "experience": 0,
            "employeeId": 0,
            "workedHours": 1,
            "salary": 0
        };
        $scope.performerList.push($scope.prf);
    });

    $scope.addNewRow = function ($employee, $wh) {
        $scope.performerList[$scope.performerListId-1].employee = $employee;
        $scope.performerList[$scope.performerListId-1].experience = $employee.experience;
        $scope.performerList[$scope.performerListId-1].employeeId = $employee.id;
        $scope.performerList[$scope.performerListId-1].workedHours = $wh;

        $scope.calculateSalaryForOne();

        ++$scope.performerListId;
        $scope.performer = {
            "tableId": $scope.performerListId,
            "employee": null,
            "experience": 0,
            "employeeId": 0,
            "workedHours": 1,
            "salary": 0
        };

        $scope.performerList.push($scope.performer);
    };

    $scope.calculateSalaryForOne = function () {
        $scope.overallWorkingHoursAndExps = 0;
        $scope.overallSalary = 0;
        $scope.salaryPerHour = 0;

        for (let i = 0; i < $scope.performerList.length; i++) {
            if ($scope.performerList[i].employee === null) break;
            $scope.overallWorkingHoursAndExps = $scope.overallWorkingHoursAndExps + ($scope.performerList[i].experience * $scope.performerList[i].workedHours);
        }

        $scope.salaryPerHour = $scope.cost / $scope.overallWorkingHoursAndExps;

        for (let i = 0; i < $scope.performerList.length; i++) {
            if ($scope.performerList[i].employee === null) break;

            $scope.performerList[i].salary = $scope.salaryForOnePerson(
                $scope.salaryPerHour,
                $scope.performerList[i].experience,
                $scope.performerList[i].workedHours
            );

            $scope.overallSalary += $scope.performerList[i].salary;
        }
    };

    $scope.salaryForOnePerson = function ($salaryPerHour, $experience, $workHour) {
        return Math.round(($salaryPerHour * $experience * $workHour) / 1000) * 1000
    };

    /*****************************************************
    *
    * Work with production cost
    * */
    $scope.calcTotalCost = function () {
        $scope.cost = $scope.amount * $scope.product.rate;
    };

    $scope.setTotalCostToNull = function () {
        $scope.amount = 0;
        $scope.cost = "";
    };
    /*****************************************************/

    $scope.createProductionObject = function () {
        $scope.production = {
            "date": $scope.date,
            "reference": $scope.reference,
            "productId": $scope.product.id,
            "amount": $scope.amount,
            "cost": $scope.cost,
            "workers": {}
        };
    };


    $scope.saveProduction = function () {
        $scope.createProductionObject();
        $scope.performerList.splice(-1,1);

        $scope.collection = {
            "production": $scope.production,
            "performers": $scope.performerList
        };

        $http({
            method: "POST",
            url: "/production/register",
            data: $scope.collection
        }).then(function (response) {
            $window.location.href = '/production';
        });
    };
});

app.controller('ProductionIndexController', function ($scope, $window, $http, myService) {
    //CRUD
    $http({
        method: "GET",
        url: "/production/get"
    }).then(function (response) {
        $scope.production = response.data;
        // $scope.production
    });

    $scope.getProduction = function ($id) {
        $window.location.href = '/production/add-new';
        myService.set($id);

    };

    $scope.deleteProduction = function ($id) {
        $http({
            method: "GET",
            url: "/production/delete/" + $id
        }).then(function (response) {
            $scope.getAll();
        });
    };

    $scope.getAll = function () {
        $http({
            method: "GET",
            url: "/production/get"
        }).then(function (response) {
            $scope.production = response.data;
        });
    }
});

app.factory('myService', function() {
    let savedData;
    function set(data) {
        savedData = data;
    }
    function get() {
        return savedData;
    }

    return {
        set: set,
        get: get
    }

});