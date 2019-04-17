package com.qkl.msg.jpa;

import com.qkl.msg.entity.SmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by Benson on 2018/8/10.
 */
public interface SmsRecordRepository extends JpaRepository<SmsRecord, String>, JpaSpecificationExecutor<SmsRecord> {

    List<SmsRecord> findByMobileOrderByCreateTimeDesc(String mobile);

    List<SmsRecord> findByMobileAndCreateTimeBetween(String mobile, Date starte, Date end);
}
