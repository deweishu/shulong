package com.tensquare.spit.service;


import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;


    public void add(Spit spit) {

        //封装请求参数里面没有的数据
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态


        //判断是评论(parenid!=null)还是直接发布的吐槽
        if(spit.getParentid() != null && !"".equals(spit.getParentid())){
            //这个吐槽就是评论, 根据Parentid获得原始的吐槽, 给评论数+1
            //封装条件
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            //封装数据
            Update update = new Update();
            update.inc("comment",1);

            mongoTemplate.updateFirst(query,update,"spit");
        }

        spitDao.save(spit);
    }

    public List<Spit> findAll() {
        return  spitDao.findAll();
    }

    public Spit findById(String spitId) {
        return  spitDao.findById(spitId).get();
    }

    public void update(String spitId, Spit spit) {
        //方式一先查询, 再更新
        // 判断传过来的spit里面是否有数据, 有数据赋值
        //spit = spitDao.findById(spitId).get();
        spit.set_id(spitId);
        spitDao.save(spit);

        //方式二: 不要SpringData里面的save()方法进行更新, 自己定义方法
    }

    public void delete(String spitId) {
        spitDao.deleteById(spitId);
    }

    public Page<Spit> findComment(String parentid, int page, int size) {
       Pageable pageable = PageRequest.of(page-1,size);
       return spitDao.findByParentid(parentid,pageable);
    }



    public void thumbup(String spitId) {
        //方式一: 先查询+1, 再更新
        //Spit spit = spitDao.findById(spitId).get();
        //spit.setThumbup(spit.getThumbup()+1);
        //spitDao.save(spit);

        //方式二: 使用mongoTemplate
        //封装条件   _id=1
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        //封装数据
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
