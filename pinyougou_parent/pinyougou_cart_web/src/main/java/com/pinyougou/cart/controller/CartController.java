package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.cart.service.CartService;
import entity.Result;
import groupEntity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    /**
     * 这是session的有效时长，不仅仅是一次会话
     * @return
     */
    private String getSessionId(){
        String sessionId = session.getId();
        if (sessionId==null) {
            sessionId=CookieUtil.getCookieValue(request,"cartList","utf-8");
        }
        CookieUtil.setCookie(request,response,"cartList",sessionId,3600*24*7,"utf-8");
        return sessionId;
    }

    /**
     * 查询购物车列表数据
     */
    @RequestMapping("/findCartList")
    public List<Cart> findCartList(){
        String sessionId = getSessionId();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //未登录
        List<Cart> cartList_sessionId = cartService.findCartListFromRedis(sessionId);
        if ("anonymousUser".equals(username)) {//未登录
            System.out.println("search item to sesssionId redis ....");
            return cartList_sessionId;
        }else{

            System.out.println("search item to username redis ....");
            //登录
            List<Cart> cartList_username = cartService.findCartListFromRedis(username);
            if (cartList_sessionId!=null && cartList_sessionId.size()>0) {//登录前，添加了商品到购物车列表

                //合并登录前的购物车列表数据到登录后的购物车列表
                cartList_username= cartService.mergeCartList(cartList_sessionId,cartList_username);

                //清除合并前的购物车列表
                cartService.deleteCartList(sessionId);

                //将合并后的购物车列表再存入redis中
                cartService.addCartListToRedis(username,cartList_username);

            }
            return cartList_username;
        }

    }


    /**
     * 添加商家到购物车列表
     */
    @RequestMapping("/addItemToCartList")
    @CrossOrigin(origins = "http://item.pinyougou.com",allowCredentials = "true")
    public Result addItemToCartList(Long itemId,Integer num){

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println(username);
            List<Cart> cartList = findCartList();
            //添加商品到购物车列表
            cartList = cartService.addItemToCartList(cartList, itemId, num);
            if("anonymousUser".equals(username)){//未登录
                System.out.println("add item to sesssionId redis ....");
                String sessionId = session.getId();
                //将添加商品后的购物车列表存入redis
                cartService.addCartListToRedis(sessionId,cartList);
            }else {
                System.out.println("add item to username redis ....");
                //将添加商品后的购物车列表存入redis
                cartService.addCartListToRedis(username,cartList);
            }
            return new Result(true,"添加购物车成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return  new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,"添加购物车失败");
        }
    }



}
