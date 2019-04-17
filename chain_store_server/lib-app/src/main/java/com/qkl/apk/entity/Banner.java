package com.qkl.apk.entity;


import com.qkl.apk.bean.BannerLinkType;
import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * 首页banner
 * 
 **/
@Entity
@Table(name = "app_banner")
public class Banner extends UUIDEntity {

	/**banner标题*/
	@Column(name = "banner_title")
	private String bannerTitle;

	/**bannerlogo的url**/
	@Column(name = "logo")
	private String logo;

	/**连接url**/
	@Column(name = "url")
	private String url;

	/**连接类型(10.网页20.应用)*/
	@Column(name = "link_type")
	private BannerLinkType linkType;

	/**应用id**/
	@JoinColumn(name = "apk_id")
	@ManyToOne
	private Apk apk;

	/**是否可用**/
	@Column(name = "status")
	private Boolean status;


	public String getBannerTitle() {
		return bannerTitle;
	}

	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
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

	public void setLinkType(BannerLinkType linkType){
		this.linkType = linkType;
	}

	public BannerLinkType getLinkType(){
		return this.linkType;
	}

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
