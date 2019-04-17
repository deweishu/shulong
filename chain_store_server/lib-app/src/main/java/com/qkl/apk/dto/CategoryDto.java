package com.qkl.apk.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.qkl.common.dto.Dto;


public class CategoryDto  extends Dto {

	/**主键**/
	private String id;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;

	/****/
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;

	/**分类名称**/
	private String name;

	/**排序**/
	private Integer cateSort;

	/**10.分类20.专题**/
	private Integer type;



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

	public void setCateSort(Integer cateSort){
		this.cateSort = cateSort;
	}

	public Integer getCateSort(){
		return this.cateSort;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

}
