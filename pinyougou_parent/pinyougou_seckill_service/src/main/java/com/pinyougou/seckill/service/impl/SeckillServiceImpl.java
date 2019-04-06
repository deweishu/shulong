package com.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private CreateOrder createOrder;

    @Override
    public List<TbSeckillGoods> findSeckillGoodsListFromRedis() {
        List<TbSeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("SECKILL_GOODS").values();
        return seckillGoodsList;
    }

    @Override
    public TbSeckillGoods findOneSeckillGoods(Long seckillGoodsId) {
        return (TbSeckillGoods) redisTemplate.boundHashOps("SECKILL_GOODS").get(seckillGoodsId);
    }

    @Override
    public void createSeckillOrder(Long seckillGoodsId, String userId) {
         /*`id` bigint(20) NOT NULL COMMENT '主键',
          `seckill_id` bigint(20) DEFAULT NULL COMMENT '秒杀商品ID',
          `money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
          `user_id` varchar(50) DEFAULT NULL COMMENT '用户',
          `seller_id` varchar(50) DEFAULT NULL COMMENT '商家',
          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
          `status` varchar(1) DEFAULT NULL COMMENT '状态',*/
         //解决同一个人重复购买此商品的问题
        Boolean member = redisTemplate.boundSetOps("SECKILL_GOODS" + seckillGoodsId).isMember(userId);
        if (member) {
            throw new RuntimeException("请先去支付，不允许重复抢购");
        }

        TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps("SECKILL_GOODS").get(seckillGoodsId);

       if(seckillGoods==null || seckillGoods.getStockCount()<=0){
           throw new RuntimeException("商品售罄");
       }

       //解决超卖问题
        Object obj = redisTemplate.boundListOps("SECKILL_GOODS_QUEUE" + seckillGoodsId).leftPop();
       if(obj==null){
           throw new RuntimeException("商品售罄");
       }

       //如果排队人数超过剩余库存量+10个人时，提示排队人数过多

        Long num = redisTemplate.boundValueOps("SECKILL_PERSON_QUEUE" + seckillGoodsId).size();
        if((seckillGoods.getStockCount()+10)<num){
            throw new RuntimeException("排队人员过多");
        }

        //将多线程操作mysql任务放入redis中
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("seckillGoodsId",seckillGoodsId);
        paramMap.put("userId",userId);
        redisTemplate.boundListOps("SECKILL_ORDER_QUEUE").leftPush(paramMap);

        //基于redis解决人员过多排队问题 排队人数加1操作
        redisTemplate.boundValueOps("SECKILL_PERSON_QUEUE"+seckillGoodsId).increment(1);

        //执行多线程操作mysql
        executor.execute(createOrder);
    }
}
