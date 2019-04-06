package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据id查询
     * @param labelId
     * @return
     */
    public Label findById(String labelId) {
        return  labelDao.findById(labelId).get();
    }

    /**
     * 查询所有的标签列表
     * @return
     */
    public List<Label> findAll() {
        int i = 1/0;
       return labelDao.findAll();
    }

    /**
     * 新增标签
     * @param label
     */
    public void add(Label label) {
        //给label设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }


    /**
     * 修改标签
     * @param labelId
     * @param label
     */
    public void update(String labelId, Label label) {
        label.setId(labelId); //设置id , id是需要修改的label的id, 从前端获得的请求参数
        labelDao.save(label);
    }

    /**
     * 根据id删除
     * @param labelId
     */
    public void delete(String labelId) {
        labelDao.deleteById(labelId);
    }




    /**
     * 多条件查询
     * @return
     */
    public List<Label> search(Map seachMap) {
        //根据seachMap构建Specification
        Specification<Label> specification = createSpecification(seachMap);
       return labelDao.findAll(specification);
    }

    /**
     * 多条件分页查询
     * @return
     */
    public Page<Label> search(Map seachMap, int page, int size) {
        Specification<Label> specification = createSpecification(seachMap);
        //封装分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return  labelDao.findAll(specification,pageable);
    }

    /**
     *  根据seachMap构建Specification对象
     * @param seachMap
     * @return
     */
    private Specification<Label> createSpecification(Map seachMap) {
        return  new Specification<Label>() {
            @Nullable
            @Override
            //参数1root: 根对象, 获得实体类里面的属性
            //参数2query: 上层查询对象, 定义查询(一般不用)
            //参数3cb: 构建条件对象, 封装了构建条件的方法
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //根据seachMap里面的条件动态添加Predicate
                List<Predicate> predicateList = new ArrayList<Predicate>();

                //判断seachMap里面是否有条件数据
                if(seachMap.get("id") != null && !"".equals(seachMap.get("id"))){
                    //添加id条件, =
                    predicateList.add(cb.equal(root.get("id"), seachMap.get("id")));
                }

                if(seachMap.get("labelname") != null && !"".equals(seachMap.get("labelname"))){
                    //添加labelname条件, 模糊
                    predicateList.add(cb.like(root.get("labelname").as(String.class),"%"+seachMap.get("labelname")+"%"));
                }

                if(seachMap.get("state") != null && !"".equals(seachMap.get("state"))){
                    //添加state状态, =
                    predicateList.add(cb.equal(root.get("state"),seachMap.get("state")));
                }

                if(seachMap.get("recommend") != null && !"".equals(seachMap.get("recommend"))){
                    //添加recommend 是否是推荐标签  =
                    predicateList.add( cb.equal(root.get("recommend"),seachMap.get("recommend")));
                }

                //and 多条件一个满足
                Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
                return cb.and(predicates);
            }
        };
    }


}
