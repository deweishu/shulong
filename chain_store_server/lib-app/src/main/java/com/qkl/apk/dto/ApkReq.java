package com.qkl.apk.dto;


import com.qkl.common.dto.Dto;


public class ApkReq  extends Dto {

	/**主键**/
	private String id;

	/****/
	private java.util.Date createTime;

	/****/
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
	private Integer haveAd;

	/**是否有插件**/
	private Integer havePlugin;

	/**是否人工亲测**/
	private Integer havePeople;

	/**软件特色**/
	private String special;

	/**分类id**/
	private String categoryId;

	/**客户id**/
	private String customerId;

	/**应用来源(10.系统添加20.客户添加)**/
	private Integer apkResource;

	/**应用状态(10.上架中-10已下架20待审核30审核通过40审核未通过)**/
	private Integer status;

	/**审核备注**/
	private String statusReason;


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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
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

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return this.logo;
	}

	public void setClientType(Integer clientType){
		this.clientType = clientType;
	}

	public Integer getClientType(){
		return this.clientType;
	}

	public void setDownUrl(String downUrl){
		this.downUrl = downUrl;
	}

	public String getDownUrl(){
		return this.downUrl;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return this.size;
	}

	public void setDownNum(Integer downNum){
		this.downNum = downNum;
	}

	public Integer getDownNum(){
		return this.downNum;
	}

	public void setDescribe(String describe){
		this.describe = describe;
	}

	public String getDescribe(){
		return this.describe;
	}

	public void setHaveAd(Integer haveAd){
		this.haveAd = haveAd;
	}

	public Integer getHaveAd(){
		return this.haveAd;
	}

	public void setHavePlugin(Integer havePlugin){
		this.havePlugin = havePlugin;
	}

	public Integer getHavePlugin(){
		return this.havePlugin;
	}

	public void setHavePeople(Integer havePeople){
		this.havePeople = havePeople;
	}

	public Integer getHavePeople(){
		return this.havePeople;
	}

	public void setSpecial(String special){
		this.special = special;
	}

	public String getSpecial(){
		return this.special;
	}


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return this.customerId;
	}

	public void setApkResource(Integer apkResource){
		this.apkResource = apkResource;
	}

	public Integer getApkResource(){
		return this.apkResource;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setStatusReason(String statusReason){
		this.statusReason = statusReason;
	}

	public String getStatusReason(){
		return this.statusReason;
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
