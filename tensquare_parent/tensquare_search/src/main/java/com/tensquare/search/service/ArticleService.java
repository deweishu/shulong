package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文章
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId()+"");
        //调用Dao
        articleDao.save(article);
    }

    public Page<Article> search(String keywords, int page, int size) {
        //调用Dao
        Pageable pageable =  PageRequest.of(page-1,size);
        return  articleDao.findByTitleLikeOrContentLike(keywords,keywords,pageable);

    }
}
