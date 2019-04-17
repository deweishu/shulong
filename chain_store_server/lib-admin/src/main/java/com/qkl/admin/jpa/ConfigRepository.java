package com.qkl.admin.jpa;

import com.qkl.admin.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ConfigRepository extends JpaRepository<Config, String>, JpaSpecificationExecutor<Config> {


    Config findByConfigCode(String configKey);
}
