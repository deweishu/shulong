package com.tensquare.spit.controller;


import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 发布吐槽
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功!");
    }

    /**
     * 吐槽点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable(value = "spitId") String spitId) {
        //后面会换成真实的用户id
        String userId = "123";

        //判断是否点赞过
        if(redisTemplate.opsForValue().get("spit_"+userId+"_"+spitId) != null){
            //点赞过
            return new Result(false, StatusCode.REPERROR, "不能重复点赞!");
        }


        spitService.thumbup(spitId);

        //记录点赞过
        redisTemplate.opsForValue().set("spit_"+userId+"_"+spitId,"1");

        return new Result(true, StatusCode.OK, "点赞成功!");
    }

    /**
     * 根据parentid 获得当前吐槽的评论列表(分页)
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findComment(@PathVariable(value = "parentid") String parentid, @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        Page<Spit> pageList = spitService.findComment(parentid, page, size);
        //封装成我们自定义的分页数据
        PageResult<Spit> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功!",pageResult);
    }

    /**
     * 删除吐槽
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "spitId") String spitId) {
        spitService.delete(spitId);
        return new Result(true, StatusCode.OK, "删除成功!");
    }


    /**
     * 修改吐槽
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "spitId") String spitId, @RequestBody Spit spit) {
        spitService.update(spitId, spit);
        return new Result(true, StatusCode.OK, "更新成功!");
    }


    /**
     * 根据ID查询吐槽
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "spitId") String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "查询成功!", spit);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功!", list);
    }





}
