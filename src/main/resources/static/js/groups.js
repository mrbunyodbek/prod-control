var app = angular.module('productionGroupControl', []);

app.controller('GroupController', function ($scope, $window, $http, sharedParam, $rootScope) {
    $scope.groups = [];

    // CRUD
    $http({
        method: "GET",
        url: "/groups/get"
    }).then(function (response) {
        $scope.groups = response.data;
    });

    $scope.deleteGroup = function ($id) {
        $http({
            method: "GET",
            url: "/groups/delete/" + $id
        }).then(function (response) {
            $scope.groups = response.data;
        });
    };

    $scope.openModal = function ($id) {
        sharedParam.setId($id);
        $rootScope.$broadcast('siblingAndParent');
    }
});

app.controller('GroupModalController', function ($scope, $window, $http, sharedParam, $rootScope) {

    $rootScope.$on('siblingAndParent', function(event, data) {

        $http({
            method: "GET",
            url: "/groups/get/" + sharedParam.getId()
        }).then(function (response) {
            $scope.group = response.data;
        });
    });

    $scope.editGroup = function () {
        $http({
            method: "POST",
            url: "/groups/edit",
            data: $scope.group
        }).then(function (response) {
            $window.location.reload();
        });
    };

    $scope.saveGroup = function () {
        $http({
            method: "POST",
            url: "/groups/save",
            data: $scope.group
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