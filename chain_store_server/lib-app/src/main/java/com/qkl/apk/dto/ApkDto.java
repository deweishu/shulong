package com.qkl.apk.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;


public class ApkDto  extends Dto  implements Comparable<ApkDto>{

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**app名字**/
	private String name;

	/**版本号**/
	private Integer versionCode;

	/**版本名**/
	private String versionName;

	/**app的logo**/
	private String logo;

	/**app类型(10.安卓20.ios)**/
	private Integer clientType;

	/**下载的url**/
	private String downUrl;

	/**apk大小(例如:9.87M)**/
	private String size;

	/**下载次数**/
	private Integer downNum;

	/**应用描述**/
	private String describe;

	/**是否有广告**/
	private Boolean haveAd;

	/**是否有插件**/
	private Boolean havePlugin;

	/**是否人工亲测**/
	private Boolean havePeople;

	/**软件特色**/
	private String special;

	/**分类id**/
	private String categoryId;


	private String cateName;

	/**客户id**/
	private String customerId;

	/**应用来源(10.系统添加20.客户添加)**/
	private Integer apkResource;

	private String apkResourceTxt;

	/**应用状态(10.上架中-10已下架20待审核30审核通过40审核未通过)**/
	private Integer status;

	private String statusTxt;

	/**审核备注**/
	private String statusReason;

	/**奖励糖果个数*/
	private Integer candyNum;

	/**评分数*/
	private String scoreNum;

	private String mainImg;

	private String packageName;

	/**ios的buildid*/
	private String buildId;

	/**每天最多分享次数*/
	private Integer shareMaxNum;

	/**分享apk，奖励糖果个数*/
	private Integer shareCandy;

	private String plistFile;

	/**评论该应用，奖励的糖果数*/
	private Integer commentCandy;

	/**开发者名称*/
	private String devName;

	/**开发者官网url*/
	private String devUrl;

	/**适用年龄*/
	private String applyAge;

	/**适用语言*/
	private String laguage;

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

	public Integer getShareMaxNum() {
		return shareMaxNum;
	}

	public void setShareMaxNum(Integer shareMaxNum) {
		this.shareMaxNum = shareMaxNum;
	}

	public Integer getShareCandy() {
		return shareCandy;
	}

	public void setShareCandy(Integer shareCandy) {
		this.shareCandy = shareCandy;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
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

	public Integer getCandyNum() {
		return candyNum;
	}

	public void setCandyNum(Integer candyNum) {
		this.candyNum = candyNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getDownNum() {
		return downNum;
	}

	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getApkResource() {
		return apkResource;
	}

	public void setApkResource(Integer apkResource) {
		this.apkResource = apkResource;
	}

	public String getApkResourceTxt() {
		return apkResourceTxt;
	}

	public void setApkResourceTxt(String apkResourceTxt) {
		this.apkResourceTxt = apkResourceTxt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusTxt() {
		return statusTxt;
	}

	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	@Override
	public int compareTo(ApkDto o) {
		return o.getUpdateTime().compareTo(this.getUpdateTime());
	}
}
