package com.qkl.msg.jpa;

import com.qkl.msg.entity.UserMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserMsgRepository extends JpaRepository<UserMsg, String>, JpaSpecificationExecutor<UserMsg> {


    List<UserMsg> findByUser_IdOrderByCreateTimeDesc(String userId);

}
