package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ArticleDao extends ElasticsearchCrudRepository<Article,String> {

    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);

}
