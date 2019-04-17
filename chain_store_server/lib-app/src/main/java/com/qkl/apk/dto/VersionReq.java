package com.qkl.apk.dto;


import com.qkl.common.dto.Dto;


public class VersionReq  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;

	/**关联的应用id**/
	private String apkId;

	/**版本号**/
	private Integer versionCode;

	/**版本名**/
	private String versionName;

	/**更新内容**/
	private String versionContent;

	/**10.待审核20审核通过-10审核未通过**/
	private Integer status;


	private String queryLike; 
	private Integer pageNumber = PAGE_NUMBER;
	private Integer pageSize = PAGE_SIZE;

	private Boolean audit;


	public Boolean getAudit() {
		return audit==null?false:audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
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

	public void setApkId(String apkId){
		this.apkId = apkId;
	}

	public String getApkId(){
		return this.apkId;
	}

	public void setVersionCode(Integer versionCode){
		this.versionCode = versionCode;
	}

	public Integer getVersionCode(){
		return this.versionCode;
	}

	public void setVersionName(String versionName){
		this.versionName = versionName;
	}

	public String getVersionName(){
		return this.versionName;
	}

	public void setVersionContent(String versionContent){
		this.versionContent = versionContent;
	}

	public String getVersionContent(){
		return this.versionContent;
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
