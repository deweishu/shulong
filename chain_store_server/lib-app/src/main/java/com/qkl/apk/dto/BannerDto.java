package com.qkl.apk.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;


public class BannerDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**bannerlogo的url**/
	private String logo;

	/**连接url**/
	private String url;

	/**连接类型(10.连接到app20连接到网页)**/
	private Integer linkType;

	/**应用id**/
	private String apkId;

	private String apkName;

	/**是否可用**/
	private Boolean status;

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return this.logo;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return this.url;
	}

	public void setLinkType(Integer linkType){
		this.linkType = linkType;
	}

	public Integer getLinkType(){
		return this.linkType;
	}

	public void setApkId(String apkId){
		this.apkId = apkId;
	}

	public String getApkId(){
		return this.apkId;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean getStatus(){
		return this.status;
	}

}
