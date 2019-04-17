package com.qkl.apk.jpa;

import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ApkRepository extends JpaRepository<Apk, String>, JpaSpecificationExecutor<Apk> {

    List<Apk> findByCategory_Id(String categoryId);

    Integer countByCustomerId(String customerId);

    List<Apk> findByIdIn(List<String> ids);

    Apk findByPackageNameAndStatus(String packageName,ApkStatus status);


    List<Apk> findByStatus(ApkStatus apkStatus);


}
