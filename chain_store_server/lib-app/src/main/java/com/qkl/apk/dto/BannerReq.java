package com.qkl.apk.dto;


import com.qkl.common.dto.Dto;


public class BannerReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**bannerlogo的url**/
	private String logo;

	/**连接url**/
	private String url;

	/**连接类型(10.连接到app20连接到网页)**/
	private Integer linkType;

	/**应用id**/
	private String apkId;

	/**是否可用**/
	private Integer status;


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

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
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
