package com.qkl.apk.es;

import com.qkl.common.constant.EsCodeConstant;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;

/**
 *  活动信息es 实体对象
 * @author dengjihai
 * @create 2018-08-28
 **/
@Document(indexName = EsCodeConstant.ACTIVITY_ES_INDEX, type =EsCodeConstant.ACTIVITY_ES_TYPE)
public class EsActivityInfo implements Serializable {


    @Id
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String id;//id，主键

    /**活动标题*/
    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String title;

    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String mainImg;

    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String logo;

    @Field(type = FieldType.Integer,index = FieldIndex.analyzed)
    private Integer type;

    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String linkUrl;

    /**活动规则**/
    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String rule;

    /**活动简介**/
    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String infomation;

    @Field(type = FieldType.Long,index = FieldIndex.not_analyzed,store = true)
    private Long createDate;//创建时间

    @Field(type = FieldType.Boolean,index = FieldIndex.analyzed)
    private Boolean status;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

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

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }
}
