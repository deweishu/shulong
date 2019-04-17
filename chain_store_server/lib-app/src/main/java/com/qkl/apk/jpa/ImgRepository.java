package com.qkl.apk.jpa;

import com.qkl.apk.entity.Apk;
import com.qkl.apk.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ImgRepository extends JpaRepository<Img, String>, JpaSpecificationExecutor<Img> {

    List<Img> findByApkOrderByImgSortAsc(Apk apk);

    List<Img> findByIdIn(List<String> ids);

    Integer countByApk(Apk apk);
}
