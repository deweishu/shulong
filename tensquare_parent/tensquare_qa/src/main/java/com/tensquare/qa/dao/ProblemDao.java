package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    // SELECT * FROM tb_problem WHERE id in(SELECT problemid from tb_pl WHERE labelid = '1') ORDER BY  createtime desc

    @Query(value = "SELECT * FROM tb_problem WHERE id in(SELECT problemid from tb_pl WHERE labelid = ?1) ORDER BY  createtime desc",nativeQuery = true)
    Page<Problem> findNewlist(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id in(SELECT problemid from tb_pl WHERE labelid = ?1) ORDER BY  reply desc",nativeQuery = true)
    Page<Problem> findHotList(String labelid,Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id in(SELECT problemid from tb_pl WHERE labelid = ?1) AND reply=0 ORDER BY createtime desc",nativeQuery = true)
    Page<Problem> findWaitlist(String labelid, Pageable pageable);
}
