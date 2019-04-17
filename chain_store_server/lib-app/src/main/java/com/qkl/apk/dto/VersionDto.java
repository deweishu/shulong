package com.qkl.apk.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.apk.bean.LinkType;
import com.qkl.common.dto.Dto;

import javax.persistence.Column;


public class VersionDto  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/**更新时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**关联的应用id**/
	private String apkId;

	/**关联的应用名称*/
	private String apkName;

	/**版本号**/
	private Integer versionCode;

	/**版本名**/
	private String versionName;

	/**更新内容**/
	private String versionContent;

	/**10.待审核20审核通过-10审核未通过**/
	private Integer status;

	/**下载的url**/
	private String downUrl;

	/**链接类型(10.app_store链接20.第三方分发平台链接30.自有下载包地址)*/
	private Integer linkType;

	private String linkTypeDesc;

	/**apk类型，10安卓，20ios*/
	private Integer apkType;


	/**apk大小(例如:9.87M)**/
	private String size;

	/**审核备注**/
	private String statusReason;

	private String createDate;

	private String packageName;//apk的包名

	private String appLogo;//app的logo

	private String pListFile;

	public String getpListFile() {
		return pListFile;
	}

	public void setpListFile(String pListFile) {
		this.pListFile = pListFile;
	}

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public Integer getApkType() {
		return apkType;
	}

	public void setApkType(Integer apkType) {
		this.apkType = apkType;
	}

	public String getLinkTypeDesc() {
		return linkTypeDesc;
	}

	public void setLinkTypeDesc(String linkTypeDesc) {
		this.linkTypeDesc = linkTypeDesc;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
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

}
