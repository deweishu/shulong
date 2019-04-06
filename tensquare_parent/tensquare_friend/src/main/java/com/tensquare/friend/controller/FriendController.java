package com.tensquare.friend.controller;


import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;



    /**
     * 删除好友(之前点击了爱心之后的, 再能删除)
     * @param friendid
     * @param request
     * @return
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable(value = "friendid") String friendid, HttpServletRequest request){
        //1. 判断是否登录过了
       /* Claims claims = (Claims) request.getAttribute("role_user");
        if(claims == null){
            return  new Result(false, StatusCode.ACCESSERROR,"请先登录");
        }

        //2.获得当前用户的id
        String userId = claims.getId();*/

        String userId = "1";
        //3.调用业务
        friendService.deleteFriend(userId,friendid);

        return  new Result(true, StatusCode.OK,"删除成功");
    }


    /**
     * 添加好友和非好友
     * @param friendid  添加的好友/非好友id
     * @param type  1喜欢  (点击爱心,添加好友) 2不喜欢(点击错×,添加非好友)
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable(value = "friendid") String friendid, @PathVariable(value = "type") String type, HttpServletRequest request){
        //1. 判断是否登录过了
       /* Claims claims = (Claims) request.getAttribute("role_user");
        if(claims == null){
            return  new Result(false, StatusCode.ACCESSERROR,"请先登录");
        }

        //2.获得当前用户的id
        String userId = claims.getId();*/

        String userId = "1";

        //3.判断是否是喜欢还是不喜欢
        if("1".equals(type)){
            //喜欢
            int count =  friendService.addFriend(userId,friendid);
            if(count == 0){
                //添加过的
                return  new Result(false, StatusCode.REPERROR,"你已经添加过了该好友");
            }
        }else{
            //不喜欢
            friendService.addNoFriend(userId,friendid);
        }

        return  new Result(true, StatusCode.OK,"添加成功");
    }

}
