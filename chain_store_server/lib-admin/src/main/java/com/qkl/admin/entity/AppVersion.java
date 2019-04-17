package com.qkl.admin.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 应用版本表
 * 
 **/
@Entity
@Table(name = "sys_app_version")
public class AppVersion extends UUIDEntity {

	/**应用版本号**/
	@Column(name = "version_code")
	private String versionCode;

	/**应用版本名称**/
	@Column(name = "version_name")
	private String versionName;

	/**应用版本更新说明**/
	@Column(name = "version_desc")
	private String versionDesc;

	/**下载地址**/
	@Column(name = "download_url")
	private String downloadUrl;

	/**应用版本的MD5码**/
	@Column(name = "md5")
	private String md5;

	/**版本状态：上架-0，下架-1**/
	@Column(name = "version_status")
	private Boolean versionStatus;

	/**版本大小，单位Byte**/
	@Column(name = "version_size")
	private String versionSize;

	/**是否强制更新:1=是,0=否**/
	@Column(name = "force_update")
	private Boolean forceUpdate;

	/**客户端类型（10.安卓20.IOS）*/
	@Column(name = "client_type")
	private Integer clientType;


	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
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
