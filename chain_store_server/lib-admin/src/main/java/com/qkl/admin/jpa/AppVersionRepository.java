package com.qkl.admin.jpa;

import com.qkl.admin.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AppVersionRepository extends JpaRepository<AppVersion, String>, JpaSpecificationExecutor<AppVersion> {



    List<AppVersion> findByClientTypeAndVersionStatusOrderByVersionCodeDesc(Integer clientType, Boolean verionStatus);

}
