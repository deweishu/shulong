package com.dwshu.service;


import com.dwshu.pojo.User;
import com.dwshu.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dwshu
 * @create 2019/11/27
 */

@Service
public class UserServer {

    @Autowired
    private UserRepository userRepository;

    //查询所有用户信息
    public List<User> findAllByOrderById(){
        List<User> users = userRepository.findAllByOrderById();
        return  users;
    }
}
