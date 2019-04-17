package com.qkl.apk.entity;


import com.qkl.apk.bean.AcitivityType;
import com.qkl.apk.bean.BannerLinkType;
import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 活动信息表
 * 
 **/
@Entity
@Table(name = "app_activity")
public class Activity extends UUIDEntity {

	/**
	 * 活动主图
	 */
	@Column(name = "main_img")
	private String mainImg;

	/**活动logo的url**/
	@Column(name = "logo")
	private String logo;

	/**活动标题**/
	@Column(name = "title")
	private String title;

	/**活动链接**/
	@Column(name = "link_url")
	private String linkUrl;

	/**活动类型(10.项目活动20.糖果盒子30.赏金计划)**/
	@Column(name = "type")
	private AcitivityType type;

	/**活动规则**/
	@Column(name = "rule")
	private String rule;

	/**活动简介**/
	@Column(name = "infomation")
	private String infomation;

	@Column(name = "status")
	private Boolean status;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}


	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return this.logo;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl(){
		return this.linkUrl;
	}

	public void setType(AcitivityType type){
		this.type = type;
	}

	public AcitivityType getType(){
		return this.type;
	}


	public void setRule(String rule){
		this.rule = rule;
	}

	public String getRule(){
		return this.rule;
	}

	public void setInfomation(String infomation){
		this.infomation = infomation;
	}

	public String getInfomation(){
		return this.infomation;
	}

}
