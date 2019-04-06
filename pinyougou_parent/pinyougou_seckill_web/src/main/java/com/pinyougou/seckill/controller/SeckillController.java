package com.pinyougou.seckill.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.seckill.service.SeckillService;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;

    /**
     * 获取秒杀商品列表展示
     */
    @RequestMapping("/findSeckillGoodsList")
    public List<TbSeckillGoods> findSeckillGoodsList(){
        return seckillService.findSeckillGoodsListFromRedis();
    }

    /**
     * 根据秒杀商品id查询商品详情
     */
    @RequestMapping("/findOneSeckillGoods")
    public TbSeckillGoods findOneSeckillGoods(Long seckillGoodsId){
        return seckillService.findOneSeckillGoods(seckillGoodsId);
    }

    /**
     * 秒杀下单功能
     */
    @RequestMapping("/createSeckillOrder")
    public Result createSeckillOrder(Long seckillGoodsId){
        try {
            //获取登录人信息
            String userId =SecurityContextHolder.getContext().getAuthentication().getName();

            if("anonymousUser".equals(userId)){
                return new Result(false,"请先登录");
            }
            seckillService.createSeckillOrder(seckillGoodsId,userId);

            return new Result(true,"抢单成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"秒杀失败");
        }
    }
}
