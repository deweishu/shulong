package com.qkl.admin.jpa;

import com.qkl.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dengjihai on 2018/3/7.
 */
public interface AdminRepository extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

    Admin findByUsername(String username);
    Admin findByMobile(String mobile);
    Admin findByEmail(String email);
}
