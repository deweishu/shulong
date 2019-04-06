 //控制层 
app.controller('cartController' ,function($scope,$controller   ,cartService){
	
	$controller('baseController',{$scope:$scope});//继承

	//查询购物车列表
	$scope.findCartList=function () {
		cartService.findCartList().success(function (response) {
			$scope.cartList=response;
            sum();
        })
    }

    //添加商品到购物车
	$scope.addItemToCartList=function (itemId,num) {
		cartService.addItemToCartList(itemId,num).success(function (response) {
			if(response.success){
                $scope.findCartList();//添加商品成功后，重新加载购物车列表数据
			}else{
				alert(response.message);
			}
        })
    }

    //统计商品总数量和总金额
	sum=function () {
		//记录总数量和总金额对象
		$scope.count={totalNum:0,totalMoney:0};

		//遍历购物车列表
		for(var i=0;i<$scope.cartList.length;i++){
			//遍历购物车明细列表对象
           var orderItemList = $scope.cartList[i].orderItemList;
           for(var j=0;j<orderItemList.length;j++){
               $scope.count.totalNum+=orderItemList[j].num;//计算总数量
               $scope.count.totalMoney+=orderItemList[j].totalFee;//计算总金额
		   }
		}
    }

});	
