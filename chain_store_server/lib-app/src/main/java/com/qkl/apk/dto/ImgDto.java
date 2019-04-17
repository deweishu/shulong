package com.qkl.apk.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;


public class ImgDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**图片url**/
	private String imgUrl;

	/**apk主键的id**/
	private String apkId;

	private Integer imgSort;//排序


	public Integer getImgSort() {
		return imgSort==null?1:imgSort;
	}

	public void setImgSort(Integer imgSort) {
		this.imgSort = imgSort;
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

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return this.imgUrl;
	}

	public void setApkId(String apkId){
		this.apkId = apkId;
	}

	public String getApkId(){
		return this.apkId;
	}

}
