package com.qkl.api.candy.web;

import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.cache.SignDayCache;
import com.qkl.user.dto.CandyLogDto;
import com.qkl.user.service.CandyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

/**
 * 用户糖果相关接口
 * @author dengjihai
 * @create 2018-08-29
 **/
@Api(description = "用户糖果相关接口")
@RestController
@RequestMapping("/candy")
public class CandyController extends BaseController {


    @Autowired
    CandyLogService candyLogService;

    @Autowired
    TokenSessionCache tokenSessionCache;

    @Autowired
    ConfigService configService;

    @Autowired
    SignDayCache signDayCache;

    @ApiOperation(value="查询糖果明细接口", notes="查询糖果明细接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/user/log")
    public JsonResponse candyLog(){
        String userId=tokenSessionCache.getUserId();
        return WebUtil.successJsonResponse("获取糖果明细成功",candyLogService.getCandyLog(userId));
    }



    /**
     * 分享app成功后获取糖果接口
     * @param candyLogDto
     * @param bindingResult
     * @return
     */
    /*@ApiOperation(value="分享app成功后获取糖果接口", notes="分享app成功后获取糖果接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/share/app")
    public JsonResponse shareApp(@Valid @RequestBody CandyLogDto candyLogDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        candyLogDto.setUserId(tokenSessionCache.getUserId());
        candyLogService.save(candyLogDto);
        return WebUtil.successJsonResponse("获取糖果成功");
    }*/






    /**
     * 签到7天。抽奖糖果数接口
     * @return
     */
    @ApiOperation(value="抽奖糖果接口", notes="抽奖糖果接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/cj")
    public JsonResponse cjCandy(){
        String userId=tokenSessionCache.getUserId();
        Integer dayNum =signDayCache.getDayNum(userId);
        if(!dayNum.equals(CodeConstant.DAY_NUM)){
            return WebUtil.errorJsonResponse("亲，要签到7天才可以抽奖哦");
        }
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.CJ_NUM.getCode());
        Integer maxCandy=configDto.getConfigNum();
        Random rand = new Random();
        int num =rand.nextInt(maxCandy) + 1;
        CandyLogDto candyLogDto =new CandyLogDto();
        candyLogDto.setChangeType(CandyLogType.CJ_GET.getCode());
        candyLogDto.setCandyNum(num);
        candyLogDto.setUserId(userId);
        candyLogService.save(candyLogDto);
        return WebUtil.successJsonResponse("恭喜你，中奖"+num+"糖果");
    }

}
