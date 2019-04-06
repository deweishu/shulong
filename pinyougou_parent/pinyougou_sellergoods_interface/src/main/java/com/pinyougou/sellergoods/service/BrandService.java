package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /**
     * 查询所有品牌列表
     * @return
     */
    public List<TbBrand> findAll();

    PageResult findPage(int pageNum, int pageSize);

    void add(TbBrand brand);

    TbBrand findOne(Long id);

    void update(TbBrand brand);

    void delete(Long[] ids);

    public PageResult search(int pageNum,int pageSize,TbBrand brand);

    /**
     * 查询模板关联的品牌下拉列表
     */
    public List<Map> selectBrandOptions();
}
