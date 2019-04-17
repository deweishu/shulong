package com.qkl.common.jpa;

import com.qkl.common.bean.UUIDEntity;
import com.qkl.common.dto.ILoad;
import com.qkl.common.page.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

/**
 * 公共Repository
 */
public interface CommonRepository extends JpaRepository<UUIDEntity, String>, JpaSpecificationExecutor<UUIDEntity> {

    <T extends UUIDEntity> T findOne(String id, Class<T> clazz);

    <T extends UUIDEntity> List<T> findAll(Class<T> clazz);

    List<Object> callFunction(String sql, Map<String, Object> params);

    <T extends ILoad> List<T> findNativeSQL(String sql, Map<String, Object> params, Class<T> dtoClazz);

    /** 分页查询，如果page为空，则不进行分页<br>
     *  如果params为空，则试图去page中查找查询条件<br>
     */
    <T extends ILoad> Page<T> findNativeSQL(Page page, String sql, Map<String, Object> params, Class<T> dtoClazz);

    int executeUpdate(String sql, Map<String, Object> params);

}
