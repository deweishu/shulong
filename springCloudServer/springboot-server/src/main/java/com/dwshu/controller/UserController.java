package com.dwshu.controller;

import com.dwshu.pojo.User;
import com.dwshu.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dwshu
 * @create 2019/11/27
 */

@RestController
public class UserController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserServer userServer;


    @GetMapping("/all")
    public List<User> getAll() {
        List<User> users = userServer.findAllByOrderById();
        for (User user : users) {

            //将查出的用户信息，已Id为键，用户为值的方式存入redis中
            redisTemplate.opsForValue().set(String.valueOf(user.getId()), user);
        }
        return users;
    }

}
