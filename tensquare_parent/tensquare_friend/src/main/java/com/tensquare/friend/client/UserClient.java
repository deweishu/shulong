package com.tensquare.friend.client;


import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-user")
public interface UserClient {
    /**
     * 变更关注数
     * @param userId
     * @param count
     * @return
     */
    @RequestMapping(value = "/user/incFollow/{userId}/{count}",method= RequestMethod.POST)
     Result incFollow(@PathVariable(value = "userId") String userId, @PathVariable(value = "count")int count);


    /**
     * 变更粉丝
     * @param userId
     * @param count
     * @return
     */
    @RequestMapping(value = "/user/incFans/{userId}/{count}",method= RequestMethod.POST)
     Result incFans(@PathVariable(value = "userId") String userId, @PathVariable(value = "count")int count);


}
