package com.dwshu.dao;

import com.dwshu.pojo.EsUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author dwshu
 * @create 2019/11/27
 */
public interface EsUserRepository extends ElasticsearchRepository<EsUser,Integer> {
}
