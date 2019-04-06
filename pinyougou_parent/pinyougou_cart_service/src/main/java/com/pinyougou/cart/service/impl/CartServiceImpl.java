package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import groupEntity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num) {

        //1.根据商品SKU ID查询SKU商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        if (item==null) {
            throw new RuntimeException("商品不存在");
        }

        //2.获取商家ID
        String sellerId = item.getSellerId();
        //3.根据商家ID判断购物车列表中是否存在该商家的购物车
        Cart cart = searchCartBySellerId(cartList,sellerId);
        if (cart==null) {
            //4.如果购物车列表中不存在该商家的购物车
            //4.1 新建购物车对象
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());
            //4.2 将新建的购物车对象添加到购物车列表
            List<TbOrderItem> orderItemList = new ArrayList<>();
            //4.3创建购物车明细对象
           TbOrderItem orderItem = createOrderItem(item,num);

           orderItemList.add(orderItem);
           cart.setOrderItemList(orderItemList);
           cartList.add(cart);

        }else {
            //5.如果购物车列表中存在该商家的购物车
            // 查询购物车明细列表中是否存在该商品
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            TbOrderItem orderItem = searchOrderItemByItemId(orderItemList,itemId);
            if (orderItem==null) {
                //5.1. 如果没有，新增购物车明细对象
                orderItem = createOrderItem(item,num);
                orderItemList.add(orderItem);
            }else {
                //5.2. 如果有，在原购物车明细上添加数量，更改金额
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));

                //删除某个商品
                if (orderItem.getNum()<=0) {
                    orderItemList.remove(orderItem);
                }
                //删除所有购物明细对象
                if (orderItemList.size()==0) {
                    cartList.remove(cart);
                }
            }

        }
        return cartList;
    }

    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {

        for (TbOrderItem orderItem : orderItemList) {
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }

    private TbOrderItem createOrderItem(TbItem item, Integer num) {
        if(num<=0){
            throw new RuntimeException("添加商品数量不能小于1");
        }

        TbOrderItem orderItem = new TbOrderItem();
         /*`id` bigint(20) NOT NULL,
          `item_id` bigint(20) NOT NULL COMMENT '商品id',
          `goods_id` bigint(20) DEFAULT NULL COMMENT 'SPU_ID',
          `order_id` bigint(20) NOT NULL COMMENT '订单id',
          `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品标题',
          `price` decimal(20,2) DEFAULT NULL COMMENT '商品单价',
          `num` int(10) DEFAULT NULL COMMENT '商品购买数量',
          `total_fee` decimal(20,2) DEFAULT NULL COMMENT '商品总金额',
          `pic_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品图片地址',
          `seller_id` varchar(100) COLLATE utf8_bin DEFAULT NULL,*/
        orderItem.setItemId(item.getId());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        orderItem.setPicPath(item.getImage());
        orderItem.setSellerId(item.getSellerId());
        return orderItem;

    }

    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Cart> findCartListFromRedis(String sessionId) {
        //[]
        String cartListStr = (String) redisTemplate.boundValueOps(sessionId).get();

        if(cartListStr==null || "".equals(cartListStr)){
            cartListStr="[]";
        }

        List<Cart> cartList = JSON.parseArray(cartListStr, Cart.class);
        return cartList;
    }

    @Override
    public void addCartListToRedis(String sessionId, List<Cart> cartList) {
        redisTemplate.boundValueOps(sessionId).set(JSON.toJSONString(cartList),7L, TimeUnit.DAYS);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList_sessionId, List<Cart> cartList_username) {

        for(Cart cart:cartList_sessionId){
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            for (TbOrderItem orderItem : orderItemList) {
                cartList_username= addItemToCartList(cartList_username,orderItem.getItemId(),orderItem.getNum());
            }
        }

        return cartList_username;
    }

    @Override
    public void deleteCartList(String sessionId) {
        redisTemplate.delete(sessionId);
    }


}
