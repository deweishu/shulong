package com.qkl.apk.jpa;

import com.qkl.apk.bean.CategoryType;
import com.qkl.apk.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

    List<Category> findByTypeOrderByCateSortAsc(CategoryType categoryType);

}
