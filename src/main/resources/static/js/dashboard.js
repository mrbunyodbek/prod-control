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

var areaChartData = {
    "labels": [],
    "datasets" :
        {
            "label"               : 'Electronics',
            "fillColor"           : 'rgba(210, 214, 222, 1)',
            "strokeColor"         : 'rgba(210, 214, 222, 1)',
            "pointColor"          : 'rgba(210, 214, 222, 1)',
            "pointStrokeColor"    : '#c1c7d1',
            "pointHighlightFill"  : '#fff',
            "pointHighlightStroke": 'rgba(220,220,220,1)',
            "data"                : [65, 59, 80, 81, 56, 55, 40]
        }

};

var lineChartCanvas          = $('#lineChart').get(0).getContext('2d');
var lineChart                = new Chart(lineChartCanvas);
// var lineChartOptions         = areaChartOptions;
// lineChartOptions.datasetFill = false;
lineChart.Line(areaChartData);
