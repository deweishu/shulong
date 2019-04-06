 //控制层 
app.controller('payController' ,function($scope,$controller ,$location,payService){
	
	$controller('baseController',{$scope:$scope});//继承

	//生成二维码
    $scope.createNative=function () {
        payService.createNative().success(function (response) {
            //订单号和支付金额
            $scope.out_trade_no=response.out_trade_no;
            $scope.total_fee=(response.total_fee/100).toFixed(2);

            //生成二维码
            var qr = new QRious({
                element: document.getElementById('qrious'),
                size: 300,
                value: response.code_url,
                level:"H"
            });
            //调用支付状态查询的方法，因为应用程序无法获知用户操作行为，所以需要持续调用一段时间
          //  alert(333);
            $scope.queryPayStatus();
        })
    }

    //查找支付状态
    $scope.queryPayStatus=function () {
        //alert(123)
        payService.queryPayStatus($scope.out_trade_no).success(function (response) {
            if(response.success){
                location.href="paysuccess.html#?money="+$scope.total_fee;
            }else {
                //连接超时，重新生成二维码
                if(response.message=="timeout"){
                    $scope.createNative();
                }


                location.href="payfail.html";
            }
        })
    }

    //支付成功页面，展示支付金额
    $scope.getMoney=function () {
        $scope.money=$location.search()['money'];
    }
});	
