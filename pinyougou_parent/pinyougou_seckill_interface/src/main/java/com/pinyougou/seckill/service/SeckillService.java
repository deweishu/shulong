package com.pinyougou.seckill.service;

import com.pinyougou.pojo.TbSeckillGoods;

import java.util.List;

public interface SeckillService {

    /**
     * 从缓存中获取秒杀商品列表展示
     */
    public List<TbSeckillGoods> findSeckillGoodsListFromRedis();

    TbSeckillGoods findOneSeckillGoods(Long seckillGoodsId);

    /**
     * 保存秒杀订单
     */
    public void createSeckillOrder(Long seckillGoodsId,String userId);
}
