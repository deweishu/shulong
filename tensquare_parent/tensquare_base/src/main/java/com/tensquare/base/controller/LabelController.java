package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * 多条件分页查询
     *
     * @param seachMap 条件对象
     * @param page     查询哪一页
     * @param size     一页显示的数量
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result search(@RequestBody Map seachMap, @PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        Page<Label> pageList = labelService.search(seachMap, page, size);

        //转成我们自己封装了分页pojo
        PageResult<Label> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功",pageResult);
    }


    /**
     * 多条件查询
     *
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Map seachMap) {
        List<Label> list = labelService.search(seachMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据id删除
     *
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "labelId") String labelId) {
        labelService.delete(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改标签
     *
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "labelId") String labelId, @RequestBody Label label) {
        labelService.update(labelId, label);
        return new Result(true, StatusCode.OK, "更新成功");
    }


    /**
     * 根据id查询
     *
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "labelId") String labelId) {
        System.out.println("3号tensquare_base");
        Label label = labelService.findById(labelId);
        return new Result(true, StatusCode.OK, "查询成功", label);
    }

    /**
     * 增加标签
     *
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }


    /**
     * 查询所有的标签列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Label> list = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);

    }


}
