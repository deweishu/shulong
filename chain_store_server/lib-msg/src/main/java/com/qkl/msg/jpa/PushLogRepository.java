package com.qkl.msg.jpa;

import com.qkl.msg.entity.PushLog;
import com.qkl.msg.entity.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
public interface PushLogRepository  extends JpaRepository<PushLog, String>, JpaSpecificationExecutor<PushLog> {


}
