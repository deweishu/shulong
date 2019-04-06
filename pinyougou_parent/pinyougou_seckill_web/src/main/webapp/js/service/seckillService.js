//服务层
app.service('seckillService',function($http){
	    	
	//获取秒杀商品列表展示
    this.findSeckillGoodsList=function () {
        return $http.get("seckill/findSeckillGoodsList.do");
    }
    //查询秒杀商品详情信息
    this.findOneSeckillGoods=function (seckillGoodsId) {
        return $http.get("seckill/findOneSeckillGoods.do?seckillGoodsId="+seckillGoodsId);
    }

    //秒杀下单
    this.createSeckillOrder=function (seckillGoodsId) {
        return $http.get("seckill/createSeckillOrder.do?seckillGoodsId="+seckillGoodsId);
    }

});
