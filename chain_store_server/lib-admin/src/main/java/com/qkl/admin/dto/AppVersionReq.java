package com.qkl.admin.dto;


import com.qkl.common.dto.Dto;


public class AppVersionReq  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

	/**应用版本号**/
	private String versionCode;

	/**应用版本名称**/
	private String versionName;

	/**应用版本更新说明**/
	private String versionDesc;

	/**下载地址**/
	private String downloadUrl;

	/**应用版本的MD5码**/
	private String md5;

	/**版本状态：上架-0，下架-1**/
	private Integer versionStatus;

	/**版本大小，单位Byte**/
	private Integer versionSize;

	/**是否强制更新:1=是,0=否**/
	private Integer isForceUpdate;


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

	public void setVersionCode(String versionCode){
		this.versionCode = versionCode;
	}

	public String getVersionCode(){
		return this.versionCode;
	}

	public void setVersionName(String versionName){
		this.versionName = versionName;
	}

	public String getVersionName(){
		return this.versionName;
	}

	public void setVersionDesc(String versionDesc){
		this.versionDesc = versionDesc;
	}

	public String getVersionDesc(){
		return this.versionDesc;
	}

	public void setDownloadUrl(String downloadUrl){
		this.downloadUrl = downloadUrl;
	}

	public String getDownloadUrl(){
		return this.downloadUrl;
	}

	public void setMd5(String md5){
		this.md5 = md5;
	}

	public String getMd5(){
		return this.md5;
	}

	public void setVersionStatus(Integer versionStatus){
		this.versionStatus = versionStatus;
	}

	public Integer getVersionStatus(){
		return this.versionStatus;
	}

	public void setVersionSize(Integer versionSize){
		this.versionSize = versionSize;
	}

	public Integer getVersionSize(){
		return this.versionSize;
	}

	public void setIsForceUpdate(Integer isForceUpdate){
		this.isForceUpdate = isForceUpdate;
	}

	public Integer getIsForceUpdate(){
		return this.isForceUpdate;
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
