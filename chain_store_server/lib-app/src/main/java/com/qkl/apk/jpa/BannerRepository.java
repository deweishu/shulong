package com.qkl.apk.jpa;

import com.qkl.apk.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface BannerRepository extends JpaRepository<Banner, String>, JpaSpecificationExecutor<Banner> {


    List<Banner> findByStatus(Boolean status);

    List<Banner> findByStatusOrderByCreateTimeDesc(Boolean status);
}
