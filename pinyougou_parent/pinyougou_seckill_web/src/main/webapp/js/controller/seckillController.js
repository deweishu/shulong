 //控制层 
app.controller('seckillController' ,function($scope,$controller ,$location,$interval,seckillService){
	
	$controller('baseController',{$scope:$scope});//继承

    //查询秒杀商品列表
    $scope.findSeckillGoodsList=function () {
        seckillService.findSeckillGoodsList().success(function (response) {
            $scope.seckillGoodsList=response;
        })
    }

    //根据秒杀商品id查询商品详情
    $scope.findOneSeckillGoods=function () {
        //获取秒杀列表页面传递的秒杀商品id
        $scope.seckillGoodsId = $location.search()["seckillGoodsId"];
        seckillService.findOneSeckillGoods($scope.seckillGoodsId).success(function (response) {
            $scope.entity=response;
           /* $scope.time=10;
            //测试  参数一：定时器做的事  参数二：每个多长时间执行一次  参数三：执行次数
            $interval(function () {
                $scope.time--;
            }, 1000,10);*/

            //当前时间距离秒杀结束时间倒计时显示
            var endTime=new Date($scope.entity.endTime).getTime();
            var nowTime=new Date().getTime();
            /**
             *  days= Math.floor(seconds/60/60/24)   3.15
             *  hours = Math.floor((seconds-days*24*60*60)/60/60)
             *  minutes = Math.floor((seconds-days*24*60*60-hours*60*60)/60)
             *  seconds = seconds-days*24*60*60-hours*60*60-minutes*60
             */
            //剩余时间
            $scope.secondes =Math.floor( (endTime-nowTime)/1000 );

            var time =$interval(function () {
                if($scope.secondes>0){
                    //时间递减
                    $scope.secondes--;
                    //时间格式化
                    $scope.timeString=$scope.convertTimeString($scope.secondes);
                }else{
                    //结束时间递减
                    $interval.cancel(time);
                }
            },1000);
        })
    }

    $scope.convertTimeString=function (allseconds) {
        //计算天数
        var days = Math.floor(allseconds/(60*60*24));

        //小时
        var hours =Math.floor( (allseconds-(days*60*60*24))/(60*60) );

        //分钟
        var minutes = Math.floor( (allseconds-(days*60*60*24)-(hours*60*60))/60 );

        //秒
        var seconds = allseconds-(days*60*60*24)-(hours*60*60)-(minutes*60);

        //拼接时间
        var timString="";
        if(days>0){
            timString=days+"天:";
        }

        if(hours<10){
            hours="0"+hours;
        }
        if(minutes<10){
            minutes="0"+minutes;
        }
        if(seconds<10){
            seconds="0"+seconds;
        }
        return timString+=hours+":"+minutes+":"+seconds;
    }

    //秒杀下单
    $scope.createSeckillOrder=function () {
        seckillService.createSeckillOrder($scope.seckillGoodsId).success(function (response) {
            if(response.success){
                location.href="pay.html";
            }else {
                alert(response.message);
            }
        })
    }
    
});
