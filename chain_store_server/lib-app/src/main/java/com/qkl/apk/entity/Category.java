package com.qkl.apk.entity;


import com.qkl.apk.bean.CategoryType;
import com.qkl.common.bean.UUIDEntity;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * app分类信息表
 * 
 **/
@Entity
@Table(name = "app_category")
public class Category extends UUIDEntity {

	/**分类名称**/
	@Column(name = "name")
	private String name;

	/**排序**/
	@Column(name = "cate_sort")
	private Integer cateSort;

	/**10.分类20.专题**/
	@Column(name = "type")
	private CategoryType type;

	/**
	 * apk-对应的主题
	 */
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "cate_apk",
			joinColumns = {@JoinColumn(name = "cate_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "apk_id", referencedColumnName ="id")})
	private Set<Apk> apkSet = new HashSet<>();

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

	public void setType(CategoryType type){
		this.type = type;
	}

	public CategoryType getType(){
		return this.type;
	}


	public Set<Apk> getApkSet() {
		return apkSet;
	}

	public void setApkSet(Set<Apk> apkSet) {
		this.apkSet = apkSet;
	}
}
