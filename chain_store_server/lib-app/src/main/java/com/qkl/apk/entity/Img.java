package com.qkl.apk.entity;


import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;


/**
 * 
 * app的演示图表
 * 
 **/
@Entity
@Table(name = "app_img")
public class Img extends UUIDEntity {

	/**图片url**/
	@Column(name = "img_url")
	private String imgUrl;

	/**apk主键的id**/
	@JoinColumn(name = "apk_id")
	@ManyToOne
	private Apk apk;

	/**排序*/
	@Column(name = "img_sort")
	private Integer imgSort;


	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Apk getApk() {
		return apk;
	}

	public void setApk(Apk apk) {
		this.apk = apk;
	}

	public Integer getImgSort() {
		return imgSort;
	}

	public void setImgSort(Integer imgSort) {
		this.imgSort = imgSort;
	}
}
