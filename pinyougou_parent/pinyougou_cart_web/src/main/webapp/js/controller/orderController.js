 //控制层 
app.controller('orderController' ,function($scope,$controller   ,addressService,cartService,orderService){
	
	$controller('baseController',{$scope:$scope});//继承

	//记录选择的收件人地址对象
	$scope.address=null;

    //查询登陆人关联的收件人地址列表
	$scope.findAddressListByUserId=function () {
		addressService.findAddressListByUserId().success(function (response) {
			$scope.addressList=response;

			//设置默认地址功能
			for(var i=0;i<$scope.addressList.length;i++){
				if($scope.addressList[i].isDefault=='1'){
                    $scope.address=$scope.addressList[i];
                    break;
				}
			}
			//如果用户设置收件人地址信息时没有设置默认地址，将地址列表中的第一个数据作为默认地址
			if($scope.address==null){
                $scope.address=$scope.addressList[0];
			}

        })
    }
    
    //设置地址勾选状态的方法
	$scope.isSelected=function (addr) {
		if($scope.address==addr){
			return true;
		}else {
			return false;
		}
    }

    //切换收件人地址
	$scope.updateAddress=function (addr) {
        $scope.address=addr;
    }


	//查询购物车列表
	$scope.findCartList=function () {
		cartService.findCartList().success(function (response) {
			$scope.cartList=response;
            sum();
        })
    }

    //为订单中的支付属性赋值
	$scope.entity={paymentType:'1'};

    //切换支付方式
	$scope.updatePaymentType=function (type) {
        $scope.entity.paymentType=type;
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

    //提交订单
    $scope.submitOrder=function(){
        /*页面传递参数
            `payment_type` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型，1、在线支付，2、货到付款',
            `receiver_area_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人地区名称(省，市，县)街道',
            `receiver_mobile` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机',
            `receiver` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',*/
        $scope.entity.receiverAreaName=$scope.address.address;
        $scope.entity.receiverMobile=$scope.address.mobile;
        $scope.entity.receiver=$scope.address.contact;
        orderService.add( $scope.entity  ).success(
            function(response){
                if(response.success){
                    //跳转支付页面
					location.href="pay.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }

});	
