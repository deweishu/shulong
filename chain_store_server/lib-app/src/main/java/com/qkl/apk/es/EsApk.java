package com.qkl.apk.es;

import com.qkl.common.constant.EsCodeConstant;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * apk es 信息实体类
 */
@Document(indexName = EsCodeConstant.APK_ES_INDEX, type =EsCodeConstant.APK_ES_TYPE)
public class EsApk  implements Serializable {

    @Id
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String id;//id，主键

    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String name;//应用名称

    @Field(type = FieldType.String,index = FieldIndex.analyzed)
    private String namePinyin;//名称的拼音

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String logo;//应用logo

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String mainImg;//应用主图


    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String appDesc;//应用描述

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String appSpecial;//软件特色

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer status;//应用状态

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String categoryId;//分类id

    @Field(type = FieldType.Long,index = FieldIndex.not_analyzed,store = true)
    private Long createTime;//创建时间

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String createDate;//创建日期

    @Field(type = FieldType.Long,index = FieldIndex.not_analyzed,store = true)
    private Long updateTime;//更新时间

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer downNum;//下载次数

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer candyNum;//奖励糖果的个数

    @Field(type = FieldType.Boolean,index = FieldIndex.not_analyzed)
    private Boolean haveAd; //是否有广告

    @Field(type = FieldType.Boolean,index = FieldIndex.not_analyzed)
    private Boolean havePlugin; //是否有插件

    @Field(type = FieldType.Boolean,index = FieldIndex.not_analyzed)
    private Boolean havePeople; //是否人工亲测

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer clientType;//适配客户端类型

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String scoreNum;//评分数

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String packageName;//安卓包名

    @Field(type = FieldType.Boolean,index = FieldIndex.not_analyzed)
    private Boolean appSort;//是否加入应用榜

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer appSortNum;//应用榜的排序值

    @Field(type = FieldType.Boolean,index = FieldIndex.not_analyzed)
    private Boolean gameSort;//是否加入游戏榜

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer gameSortNum;//游戏榜的排序值

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String buildId;// ios 的build id

    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String plistFile;

    @Field(type = FieldType.Integer,index = FieldIndex.not_analyzed)
    private Integer commentCandy;//评论给予的糖果数

    /**开发者名称*/
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String devName;

    /**开发者官网url*/
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String devUrl;

    /**适用年龄*/
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String applyAge;

    /**适用语言*/
    @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
    private String laguage;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevUrl() {
        return devUrl;
    }

    public void setDevUrl(String devUrl) {
        this.devUrl = devUrl;
    }

    public String getApplyAge() {
        return applyAge;
    }

    public void setApplyAge(String applyAge) {
        this.applyAge = applyAge;
    }

    public String getLaguage() {
        return laguage;
    }

    public void setLaguage(String laguage) {
        this.laguage = laguage;
    }

    public Integer getCommentCandy() {
        return commentCandy;
    }

    public void setCommentCandy(Integer commentCandy) {
        this.commentCandy = commentCandy;
    }

    public String getPlistFile() {
        return plistFile;
    }

    public void setPlistFile(String plistFile) {
        this.plistFile = plistFile;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public Integer getAppSortNum() {
        return appSortNum==null?1:appSortNum;
    }

    public void setAppSortNum(Integer appSortNum) {
        this.appSortNum = appSortNum;
    }

    public Integer getGameSortNum() {
        return gameSortNum==null?1:gameSortNum;
    }

    public void setGameSortNum(Integer gameSortNum) {
        this.gameSortNum = gameSortNum;
    }

    public Boolean getAppSort() {
        return appSort==null?false:appSort;
    }

    public void setAppSort(Boolean appSort) {
        this.appSort = appSort;
    }

    public Boolean getGameSort() {
        return gameSort==null?false:gameSort;
    }

    public void setGameSort(Boolean gameSort) {
        this.gameSort = gameSort;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public Boolean getHaveAd() {
        return haveAd;
    }

    public void setHaveAd(Boolean haveAd) {
        this.haveAd = haveAd;
    }

    public Boolean getHavePlugin() {
        return havePlugin;
    }

    public void setHavePlugin(Boolean havePlugin) {
        this.havePlugin = havePlugin;
    }

    public Boolean getHavePeople() {
        return havePeople;
    }

    public void setHavePeople(Boolean havePeople) {
        this.havePeople = havePeople;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getCandyNum() {
        return candyNum;
    }

    public void setCandyNum(Integer candyNum) {
        this.candyNum = candyNum;
    }

    public Integer getDownNum() {
        return downNum;
    }

    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppSpecial() {
        return appSpecial;
    }

    public void setAppSpecial(String appSpecial) {
        this.appSpecial = appSpecial;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
