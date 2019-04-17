package com.qkl.user.jpa;

import com.qkl.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {


    User findByMobile(String mobile);


    User findByMobileAndPassWord(String mobile,String password);

    User findByInvetCode(String invetCode);

    User findByThirdId(String thirdId);


    List<User> findByInviteUserOrderByCreateTimeDesc(User user);


    List<User> findByOrderByCreateTimeDesc();
}
