package com.qkl.apk.jpa;

import com.qkl.apk.bean.ApkType;
import com.qkl.apk.bean.ApkVersionStatus;
import com.qkl.apk.entity.Apk;
import com.qkl.apk.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VersionRepository extends JpaRepository<Version, String>, JpaSpecificationExecutor<Version> {


    /*通过apkid，查询出最新版本的信息*/
    List<Version> findByApk_IdAndApkTypeAndStatusOrderByVersionCodeDesc(String apkId,ApkType apkType, ApkVersionStatus apkVersionStatus);


    List<Version> findByApk_IdAndStatusOrderByVersionCodeDesc(String apkId, ApkVersionStatus apkVersionStatus);


    List<Version> findByApk(Apk apk);

}
