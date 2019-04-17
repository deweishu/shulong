package com.qkl.admin.dto;


import com.qkl.common.dto.Dto;


public class ConfigReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date updateTime;

	/**配置code**/
	private String configCode;

	/**配置描述**/
	private String configDesc;

	/**配置内容**/
	private String configContent;

	/**配置数值**/
	private Integer configNum;


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

	public void setConfigCode(String configCode){
		this.configCode = configCode;
	}

	public String getConfigCode(){
		return this.configCode;
	}

	public void setConfigDesc(String configDesc){
		this.configDesc = configDesc;
	}

	public String getConfigDesc(){
		return this.configDesc;
	}

	public void setConfigContent(String configContent){
		this.configContent = configContent;
	}

	public String getConfigContent(){
		return this.configContent;
	}

	public void setConfigNum(Integer configNum){
		this.configNum = configNum;
	}

	public Integer getConfigNum(){
		return this.configNum;
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
