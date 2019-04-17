package com.qkl.apk.entity;


import com.qkl.apk.bean.ApkResource;
import com.qkl.apk.bean.ApkStatus;
import com.qkl.apk.bean.AppType;
import com.qkl.apk.bean.LinkType;
import com.qkl.common.bean.UUIDEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * apk应用信息表
 * 
 **/
@Entity
@Table(name = "app_apk")
public class Apk extends UUIDEntity {

	/**app名字**/
	@Column(name = "name")
	private String name;


	/**app的logo**/
	@Column(name = "logo")
	private String logo;

	/**应用主图，首页的横幅图片*/
	@Column(name = "main_img")
	private String mainImg;

	/**app类型(10.安卓20.ios)**/
	@Column(name = "client_type")
	private AppType clientType;

	/**下载次数**/
	@Column(name = "down_num")
	private Integer downNum;

	/**应用描述**/
	@Column(name = "describe_info")
	private String describe;

	/**是否有广告**/
	@Column(name = "have_ad")
	private Boolean haveAd;

	/**是否有插件**/
	@Column(name = "have_plugin")
	private Boolean havePlugin;

	/**是否人工亲测**/
	@Column(name = "have_people")
	private Boolean havePeople;

	/**软件特色**/
	@Column(name = "special")
	private String special;

	/**分类id**/
	@JoinColumn(name = "category_id")
	@ManyToOne
	private Category category;

	/**客户id**/
	@Column(name = "customer_id")
	private String customerId;

	/**应用来源(10.系统添加20.客户添加)**/
	@Column(name = "apk_resource")
	private ApkResource apkResource;

	/**应用状态(10.上架中-10已下架20待审核30审核通过40审核未通过)**/
	@Column(name = "status")
	private ApkStatus status;

	/**审核备注**/
	@Column(name = "status_reason")
	private String statusReason;

	/**奖励糖果个数*/
	@Column(name = "candy_num")
	private Integer candyNum;

	/**分享apk，奖励糖果个数*/
	@Column(name = "share_candy")
	private Integer shareCandy;

	/**每天最多分享次数*/
	@Column(name = "share_max_num")
	private Integer shareMaxNum;

	/**评分数*/
	@Column(name = "score_num")
	private String scoreNum;

	/**安卓包名*/
	@Column(name = "package_name")
	private String packageName;

	/**ios的buildid*/
	@Column(name = "build_id")
	private String buildId;

	/**ios的plist文件路径*/
	@Column(name = "plist_file")
	private String plistFile;

	/**评论该应用，奖励的糖果数*/
	@Column(name = "comment_candy")
	private Integer commentCandy;

	/**开发者名称*/
	@Column(name = "dev_name")
	private String devName;

	/**开发者官网url*/
	@Column(name = "dev_url")
	private String devUrl;

	/**适用年龄*/
	@Column(name = "age")
	private String applyAge;

	/**适用语言*/
	@Column(name = "laguage")
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
		return commentCandy==null?0:commentCandy;
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

	public Integer getShareCandy() {
		return shareCandy;
	}

	public void setShareCandy(Integer shareCandy) {
		this.shareCandy = shareCandy;
	}

	public Integer getShareMaxNum() {
		return shareMaxNum;
	}

	public void setShareMaxNum(Integer shareMaxNum) {
		this.shareMaxNum = shareMaxNum;
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
		return scoreNum==null?"5":scoreNum;
	}

	public void setScoreNum(String scoreNum) {
		this.scoreNum = scoreNum;
	}

	public Integer getCandyNum() {
		return candyNum==null?0:candyNum;
	}

	public void setCandyNum(Integer candyNum) {
		this.candyNum = candyNum;
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

	public AppType getClientType() {
		return clientType;
	}

	public void setClientType(AppType clientType) {
		this.clientType = clientType;
	}


	public Integer getDownNum() {
		return downNum==null?1:downNum;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public ApkResource getApkResource() {
		return apkResource;
	}

	public void setApkResource(ApkResource apkResource) {
		this.apkResource = apkResource;
	}

	public ApkStatus getStatus() {
		return status;
	}

	public void setStatus(ApkStatus status) {
		this.status = status;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

}
