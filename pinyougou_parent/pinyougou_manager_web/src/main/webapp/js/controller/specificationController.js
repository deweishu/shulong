//初始化控制器
app.controller("specificationController",function ($scope,$controller,specificationService) {

    //controller继承操作 基于$controller实现angularjs伪继承 {$scope:$scope} 共享$scope变量定义的数据或方法
    $controller("baseController",{$scope:$scope});

    //保存
    $scope.save=function () {

        var method=null;

        //判断是修改操作还是添加，基于entity的id判断
        if($scope.entity.specification.id!=null){
            //修改
            method=specificationService.update($scope.entity);
        }else{
            //添加
            method=specificationService.add($scope.entity);
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
        specificationService.findOne(id).success(function (response) {
            $scope.entity=response;
        })
    }

    //删除方法
    $scope.dele=function () {
        specificationService.dele($scope.selectIds).success(function (response) {
            if(response.success){
                $scope.reloadList();//添加成功，重新加载数据
            }else{
                alert(response.message);
            }
        })
    }

    $scope.searchEntity={};  //解决初始请求参数为空的问题
    $scope.search = function(pageNum,pageSize){
        specificationService.search(pageNum,pageSize,$scope.searchEntity).success(
            function(response){
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            }
        )
    }

    //初始化实体对象
    $scope.entity={specification:{},specificationOptions:[]};

    //新增规格选项行
    $scope.addRow=function () {
        $scope.entity.specificationOptions.push({});
    }

    //删除规格选项行
    $scope.deleRow=function (index) {
        $scope.entity.specificationOptions.splice(index,1);
    }

});