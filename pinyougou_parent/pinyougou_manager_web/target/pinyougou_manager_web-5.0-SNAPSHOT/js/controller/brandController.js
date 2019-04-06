//初始化控制器
app.controller("brandController",function ($scope,$controller,brandService) {

    //controller继承操作 基于$controller实现angularjs伪继承 {$scope:$scope} 共享$scope变量定义的数据或方法
    $controller("baseController",{$scope:$scope});


    //查询所有品牌数据
    $scope.findAll=function () {
        //$http.请求方式.success() 响应成功后回调函数，处理响应结果
        brandService.findAll().success(function (response) {
            $scope.list=response;
        })
    }

    //分页查询方法  CRUD
    $scope.findPage=function (pageNum,pageSize) {
        //alert(123);
        brandService.findPage(pageNum,pageSize).success(function (response) {
            // response = {total:100,rows:[{id:1,name:华为,firstChar:H}]}
            $scope.paginationConf.totalItems=response.total;//总记录数
            $scope.list=response.rows;//当前页集合数据
        });
    }

    //保存品牌
    $scope.save=function () {

        var method=null;

        //判断是修改操作还是添加，基于entity的id判断
        if($scope.entity.id!=null){
            //修改
            method=brandService.update($scope.entity);
        }else{
            //添加
            method=brandService.add($scope.entity);
        }

        //entity={name:华为,firstChar:H}
        method.success(function (response) {
            if(response.success){
                $scope.reloadList();//添加成功，重新加载数据
            }else{
                alert(response.message);
            }
        })
    }

    //根据id查询品牌数据
    $scope.findOne=function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity=response;
        })
    }

    //删除方法
    $scope.dele=function () {
        brandService.dele($scope.selectIds).success(function (response) {
            if(response.success){
                $scope.reloadList();//添加成功，重新加载数据
            }else{
                alert(response.message);
            }
        })
    }

    $scope.searchEntity={};  //解决初始请求参数为空的问题
    $scope.search = function(pageNum,pageSize){
        brandService.search(pageNum,pageSize,$scope.searchEntity).success(
            function(response){
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            }
        )
    }

});