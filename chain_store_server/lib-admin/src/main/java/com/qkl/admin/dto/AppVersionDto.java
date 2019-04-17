package com.qkl.admin.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;

import java.util.Date;


public class AppVersionDto  extends Dto {

	/**主键**/
	private String id;

	/**创建时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/**更新时间**/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
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

	/**版本状态*/
	private Boolean versionStatus;

	/**版本大小**/
	private String versionSize;

	/**是否强制更新**/
	private Boolean forceUpdate;

	private Integer clientType;

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Boolean getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(Boolean versionStatus) {
		this.versionStatus = versionStatus;
	}

	public String getVersionSize() {
		return versionSize;
	}

	public void setVersionSize(String versionSize) {
		this.versionSize = versionSize;
	}


	public Boolean getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(Boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
}
