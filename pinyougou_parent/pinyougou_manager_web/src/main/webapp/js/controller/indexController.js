//初始化控制器
app.controller("indexController",function ($scope,$controller,loginService) {

    //controller继承操作 基于$controller实现angularjs伪继承 {$scope:$scope} 共享$scope变量定义的数据或方法
    $controller("baseController",{$scope:$scope});
    
    $scope.getLoginName=function () {
        loginService.getLoginName().success(function (response) {
            $scope.loginName=response.loginName;
        })
    }

})