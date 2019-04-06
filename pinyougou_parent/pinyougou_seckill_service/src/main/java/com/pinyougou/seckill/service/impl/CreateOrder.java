package com.pinyougou.seckill.service.impl;

import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.mapper.TbSeckillOrderMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import util.IdWorker;

import java.util.Date;
import java.util.Map;

@Component
public class CreateOrder implements Runnable{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillOrderMapper seckillOrderMapper;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;
    @Override
    public void run() {
        //获取redis中存入的操作mysql线程任务
        Map<String,Object> paramMap = (Map<String, Object>) redisTemplate.boundListOps("SECKILL_ORDER_QUEUE").leftPop();
        Long seckillGoodsId = (Long) paramMap.get("seckillGoodsId");
        String userId = (String) paramMap.get("userId");

        //获取下单商品信息
        TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps("SECKILL_GOODS").get(seckillGoodsId);

        TbSeckillOrder order = new TbSeckillOrder();
        order.setId(idWorker.nextId());
        order.setSeckillId(seckillGoodsId);
        order.setUserId(userId);
        order.setMoney(seckillGoods.getCostPrice());
        order.setCreateTime(new Date());
        order.setSellerId(seckillGoods.getSellerId());
        order.setStatus("1");//未支付
        seckillOrderMapper.insert(order);

        //秒杀下单成功后，系统记录秒杀成功用户
        redisTemplate.boundSetOps("SECKILL_GOODS"+seckillGoodsId).add(userId);

        //秒杀商品下单后，库存减一
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);

        if(seckillGoods.getStockCount()<=0){//商品售罄
            //同步更新秒杀商品数据库库存
            seckillGoods.setStockCount(0);
            seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
            //清空售罄的秒杀商品
            redisTemplate.boundHashOps("SECKILL_GOODS").delete(seckillGoodsId);
        }else {
            redisTemplate.boundHashOps("SECKILL_GOODS").put(seckillGoodsId,seckillGoods);
        }

        //用户下单后，排队人数需要减一
        redisTemplate.boundValueOps("SECKILL_PERSON_QUEUE"+seckillGoodsId).increment(-1);
    }
}
