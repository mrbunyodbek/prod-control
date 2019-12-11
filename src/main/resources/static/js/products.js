var app = angular.module("productionProdControl", []);
// var products = [];

app.controller('ProductController', function ($scope, $window, $http, sharedParam, $rootScope) {
    $scope.products = [];

    //CRUD
    $http({
        method: "GET",
        url: "/products/get"
    }).then(function (response) {
        $scope.products = response.data;
        $scope.indexer = 0;
    });

    $scope.deleteProduct = function ($id) {
        $http({
            method: "GET",
            url: "/products/delete/" + $id
        }).then(function (response) {
            $scope.products = response.data;
        });
    };

    $scope.openModal = function ($id) {
        sharedParam.setId($id);
        $rootScope.$broadcast('siblingAndParent');
    }
});

app.controller('ProductModalController', function ($scope, $window, $http, sharedParam, $rootScope) {

    $rootScope.$on('siblingAndParent', function(event, data) {

        $http({
            method: "GET",
            url: "/products/get/" + sharedParam.getId()
        }).then(function (response) {
            $scope.product = response.data;
        });
    });

    $scope.editProduct = function () {
        $http({
            method: "POST",
            url: "/products/edit",
            data: $scope.product
        }).then(function (response) {
            $window.location.reload();
        });
    };

    $scope.saveProduct = function () {
        $http({
            method: "POST",
            url: "/products/save",
            data: $scope.product
        }).then(function (response) {
            $window.location.reload();
        });
    };
});

app.service('sharedParam', function () {
    var id = 0;

    var setId = function (param) {
        id = param;
    };

    var getId = function () {
        return id;
    };

    return {
        setId : setId,
        getId : getId
    }
});