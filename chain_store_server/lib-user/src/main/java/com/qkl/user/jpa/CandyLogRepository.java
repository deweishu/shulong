package com.qkl.user.jpa;

import com.qkl.user.bean.CandyLogType;
import com.qkl.user.entity.CandyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface CandyLogRepository extends JpaRepository<CandyLog, String>, JpaSpecificationExecutor<CandyLog> {


    List<CandyLog> findByUser_IdOrderByCreateTimeDesc(String userId);

    CandyLog findByUser_IdAndChangeId(String userId,String changeId);

    List<CandyLog> findByUser_IdAndChangeType(String userId, CandyLogType candyLogType);


    List<CandyLog> findByUser_IdAndChangeTypeAndChangeId(String userId, CandyLogType candyLogType,String changeId);


    List<CandyLog> findByUser_IdAndChangeTypeAndChangeIdAndChangeDate(String userId, CandyLogType candyLogType,String changeId,String changeDate);
}
