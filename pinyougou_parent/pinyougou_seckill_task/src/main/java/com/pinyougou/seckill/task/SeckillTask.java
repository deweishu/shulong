package com.pinyougou.seckill.task;

import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SeckillTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    //定期执行该方法完成秒杀商品数据库数据同步redis操作
    @Scheduled(cron = "0/5 * * * * ?")  //每隔5秒执行一次
    public void syncSeckillGoodsToRedis(){


      /*  审核通过
           有库存
        当前时间大于开始时间,并小于秒杀结束时间*/
        TbSeckillGoodsExample example = new TbSeckillGoodsExample();
        TbSeckillGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1").
                andStockCountGreaterThan(0).
                andStartTimeLessThanOrEqualTo(new Date()).
                andEndTimeGreaterThanOrEqualTo(new Date());
        List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);

        //将满足条件的秒杀商品同步redis
        for (TbSeckillGoods tbSeckillGoods : seckillGoodsList) {
            redisTemplate.boundHashOps("SECKILL_GOODS").put(tbSeckillGoods.getId(),tbSeckillGoods);

            //将剩余库存商品id存入redis队列中  例如库存还剩两个秒杀商品， 1 1
            for(int i=0;i<tbSeckillGoods.getStockCount();i++){
                redisTemplate.boundListOps("SECKILL_GOODS_QUEUE"+tbSeckillGoods.getId()).leftPush(tbSeckillGoods.getId());
            }

        }

        System.out.println("syncSeckillGoodsToRedis finish !!!");
    }

}
