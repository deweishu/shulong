package com.qkl.apk.dto;


import com.qkl.common.dto.Dto;


public class ActivityReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**活动logo的url**/
	private String logo;

	/**活动标题**/
	private String title;

	/**活动链接**/
	private String linkUrl;

	/**活动类型(10.项目活动20.糖果盒子30.赏金计划)**/
	private Integer type;

	/**活动简要**/
	private String desc;

	/**活动规则**/
	private String rule;

	/**活动简介**/
	private String infomation;


	private String queryLike; 
	private Integer pageNumber = PAGE_NUMBER;
	private Integer pageSize = PAGE_SIZE; 



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

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return this.desc;
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

	public String getQueryLike() {
		return queryLike;
	}

	public void setQueryLike(String queryLike) {
		this.queryLike = queryLike;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		 this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		 this.pageSize = pageSize;
	}

}
