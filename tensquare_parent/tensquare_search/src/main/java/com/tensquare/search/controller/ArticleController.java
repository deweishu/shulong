package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 根据关键词搜索
     *
     * @param keywords 用户输入的搜索的关键字
     * @param page     查询哪一页
     * @param size     一页查询的数量
     * @return
     */
    @RequestMapping(value = "/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result search(@PathVariable(value = "keywords") String keywords, @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        Page<Article> pageList = articleService.search(keywords, page, size);
        PageResult<Article> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "搜索成功",pageResult);
    }

    /**
     * 保存文章(就是向索引库添加)
     *
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }


}
