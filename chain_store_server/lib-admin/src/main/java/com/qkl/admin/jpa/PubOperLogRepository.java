package com.qkl.admin.jpa;


import com.qkl.admin.entity.PubOperLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Benson on 2018/3/13.
 */
public interface PubOperLogRepository extends JpaRepository<PubOperLog, String>, JpaSpecificationExecutor<PubOperLog> {
}
