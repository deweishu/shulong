package com.qkl.apk.assembler;


import com.qkl.apk.bean.CategoryType;
import com.qkl.apk.dto.CategoryDto;
import com.qkl.apk.entity.Category;
import com.qkl.common.bean.JpaProperty;

import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class CategoryAssembler {

	public static Category convertToEntity(CategoryDto categoryDto, Category category){
		if(category==null){
			category = new Category();
		}
		category.setName(categoryDto.getName());
		category.setCateSort(categoryDto.getCateSort());
		category.setType(categoryDto.getType()==null? CategoryType.FENLEI: JpaProperty.getProperty(CategoryType.class,categoryDto.getType()));
		return category;
	}


	public static CategoryDto convertToDto(Category category){
		if(category==null){
			return null;
		}
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setCateSort(category.getCateSort());
		categoryDto.setCreateTime(category.getCreateTime());
		categoryDto.setUpdateTime(category.getUpdateTime());
		categoryDto.setType(category.getType()==null?null:category.getType().getCode());
		return categoryDto;
	}


	public static List<CategoryDto> convertToDtoList(List<Category> categoryList){
		List<CategoryDto> categoryDtoList= new ArrayList<>();
		categoryList.forEach(category -> categoryDtoList.add(convertToDto(category)));
		return categoryDtoList;
	}
}
