package com.qkl.user.jpa;

import com.qkl.user.entity.SignLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface SignLogRepository extends JpaRepository<SignLog, String>, JpaSpecificationExecutor<SignLog> {

}
