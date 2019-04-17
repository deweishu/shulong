package com.qkl.admin.jpa;

import com.qkl.admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dengjihai on 2018/3/7.
 */
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    Role findById(String id);

    @Query("select r from Role r where r.status > 0")
    List<Role> findAvailable();

    @Query("select r from Role r where r.id in ?1")
    List<Role> findByIds(List<String> ids);
}
