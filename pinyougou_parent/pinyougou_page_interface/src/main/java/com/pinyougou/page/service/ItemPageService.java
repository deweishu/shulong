package com.pinyougou.page.service;

import groupEntity.Goods;

public interface ItemPageService {

    /**
     * 查询组装静态页面需要的数据
     */
    public Goods findOne(Long goodsId);

}
