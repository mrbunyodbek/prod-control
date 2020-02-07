var app = angular.module("dashboardControl", []);

app.controller('ProductionIndexController', function ($scope, $http) {
    //CRUD
    $http({
        method: "GET",
        url: "/dashboard/get"
    }).then(function (response) {

        console.log(response.data);
        $scope.statistics = response.data;
        console.log($scope.statistics.costByProduct.keys());
        console.log($scope.statistics.costByProduct.values());

        areaChartData.labels = $scope.statistics.costByProduct;
        areaChartData.datasets.data = $scope.statistics.costByProduct.values;

    });

});


