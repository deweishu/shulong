package com.qkl.apk.entity;


import com.qkl.apk.bean.ApkType;
import com.qkl.apk.bean.ApkVersionStatus;
import com.qkl.apk.bean.LinkType;
import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * apk版本记录
 * 
 **/
@Entity
@Table(name = "apk_version")
public class Version extends UUIDEntity {

	/**关联的应用id**/
	@JoinColumn(name = "apk_id")
	@ManyToOne
	private Apk apk;

	/**版本号**/
	@Column(name = "version_code")
	private Integer versionCode;

	/**版本名**/
	@Column(name = "version_name")
	private String versionName;

	/**更新内容**/
	@Column(name = "version_content")
	private String versionContent;

	/**10.待审核20审核通过-10审核未通过**/
	@Column(name = "status")
	private ApkVersionStatus status;


	/**下载的url**/
	@Column(name = "down_url")
	private String downUrl;

	/**链接类型(10.app_store链接20.第三方分发平台链接30.自有下载包地址)*/
	@Column(name = "link_type")
	private LinkType linkType;

	/*apk类型，10.安卓20.IOS*/
	@Column(name = "apk_type")
	private ApkType apkType;

	/**apk大小(例如:9.87M)**/
	@Column(name = "size")
	private String size;

	/**审核备注**/
	@Column(name = "status_reason")
	private String statusReason;

	public ApkType getApkType() {
		return apkType;
	}

	public void setApkType(ApkType apkType) {
		this.apkType = apkType;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
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

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
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

	public void setStatus(ApkVersionStatus status){
		this.status = status;
	}

	public ApkVersionStatus getStatus(){
		return this.status;
	}

}
