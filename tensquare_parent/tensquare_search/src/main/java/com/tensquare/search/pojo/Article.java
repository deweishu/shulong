package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Document(indexName = "tensquare",type = "article")
public class Article implements Serializable {

    @Id
    private String id;

    //是否索引: 决定了能否被搜索到
    //是否分词: 决定了搜索的时候是整合匹配还是单词匹配, 分词的目的是为了索引
    //是否存储: 决定了搜索出来是否需要在搜索页面展示
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store = true)
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",store =true)
    private String content;

    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
