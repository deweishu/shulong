package com.qkl.api.app.web;

import com.qkl.api.app.dto.AppCheckUpdate;
import com.qkl.api.app.dto.AppInfoReq;
import com.qkl.api.app.dto.AppInfoResp;
import com.qkl.api.app.dto.AppListResp;
import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.apk.bean.AppType;
import com.qkl.apk.cache.AppIndexCache;
import com.qkl.apk.cache.TodayAppCache;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.ApkSearchReq;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.es.EsApkOperate;
import com.qkl.apk.service.ApkService;
import com.qkl.apk.service.ImgService;
import com.qkl.apk.service.VersionService;
import com.qkl.common.bean.JpaProperty;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.HalfOpen;
import com.qkl.common.web.JsonResponse;
import com.qkl.user.bean.CandyLogType;
import com.qkl.user.dto.CandyLogDto;
import com.qkl.user.entity.CandyLog;
import com.qkl.user.service.CandyLogService;
import com.qkl.user.service.CommentLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * app 查询
 */
@Api(description = "根据条件查询APPlist")
@RestController
@RequestMapping("/app")
public class AppController extends BaseController {


    @Autowired
    ApkService apkService;

    @Autowired
    EsApkOperate esApkOperate;

    @Autowired
    CommentLogService commentLogService;

    @Autowired
    VersionService versionService;

    @Autowired
    CandyLogService candyLogService;

    @Autowired
    TokenSessionCache tokenSessionCache;

    @Autowired
    ImgService imgService;

    @Autowired
    TodayAppCache todayAppCache;

    @Autowired
    AppIndexCache appIndexCache;

    @ApiOperation(value="根据分类ID查询APP,查询APP排行榜接口", notes="根据分类ID查询APP,查询APP排行榜接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/list")
    public JsonResponse getAppList(@Valid @RequestBody ApkSearchReq apkSearchReq, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(apkSearchReq.getSortType()==null && StringUtil.isNil(apkSearchReq.getCategoryId()) && StringUtil.isNil(apkSearchReq.getAppName())){
            return WebUtil.errorJsonResponse("查询条件不能全部为空");
        }
        return WebUtil.successJsonResponse("获取APP信息成功",esApkOperate.getApkList(apkSearchReq));
    }


    @ApiOperation(value="根据APK的id查询apk的信息，dto和列表一致", notes="根据APK的id查询apk的信息，dto和列表一致", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/self/apk/{id}")
    @HalfOpen
    public JsonResponse selfApkInfo(@PathVariable String id) throws ExecutionException, InterruptedException {
        return WebUtil.successJsonResponse("获取APP明细成功",esApkOperate.getApkById(id));
    }


    @ApiOperation(value="根据App应用的id查询出app的版本详情以及下载地址，", notes="根据App应用的id查询出app的版本详情以及下载地址", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/info")
    public JsonResponse appInfo(@Valid @RequestBody AppInfoReq appInfoReq,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        AppInfoResp appInfoResp = new AppInfoResp();
        AppType appType= JpaProperty.getProperty(AppType.class,appInfoReq.getClientType());
        VersionDto versionDto =versionService.getLastVersion(appInfoReq.getAppId(),appType);
        appInfoResp.setVersionDto(versionDto);
        appInfoResp.setImgList(imgService.getAllImgByApkId(appInfoReq.getAppId()));
        return WebUtil.successJsonResponse("获取APP明细成功",appInfoResp);
    }


    @ApiOperation(value="首页根据日期来查询app，只会返回3条数据，加上4条主题数据", notes="首页根据日期来查询app，只会返回3条数据，加上4条主题数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/list/{date}")
    @HalfOpen
    public JsonResponse indexAppList(@PathVariable String date){
        AppListResp appListResp = new AppListResp();
        appListResp.setAppList(appIndexCache.getApkList(date));
        appListResp.setTodayList(todayAppCache.getApkList(date));
        return WebUtil.successJsonResponse("首页获取APP成功",appListResp);
    }

    @ApiOperation(value="安装/下载完成APP，获取糖果接口", notes="安装/下载完成APP，获取糖果接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/candy/change")
    public JsonResponse appCandy(@Valid @RequestBody CandyLogDto candyLogDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if(StringUtil.isNil(candyLogDto.getChangeId())){
            return WebUtil.errorJsonResponse("应用ID不能为空");
        }
        ApkDto apkDto=apkService.findOne(candyLogDto.getChangeId());
        Assert.notNull(apkDto,"不存在该APP");
        //如果是0或者为空，就直接返回信息给前端
        if(apkDto.getCandyNum()==null||apkDto.getCandyNum()<=0){
            return WebUtil.successJsonResponse("获取糖果成功");
        }
        Assert.notNull(apkDto,"不存在该应用信息");
        candyLogDto.setUserId(tokenSessionCache.getUserId());
        candyLogDto.setCandyNum(apkDto.getCandyNum());
        candyLogService.save(candyLogDto);
        return WebUtil.successJsonResponse("操作成功");
    }


    @ApiOperation(value="判断当前用户是否可以评论该app", notes="判断当前用户是否可以评论该app", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/comment/valid/{apkId}")
    public JsonResponse validateComment(@PathVariable String apkId){
        String userId= tokenSessionCache.getUserId();
        return WebUtil.successJsonResponse("获取成功",commentLogService.canCommentApk(userId,apkId));
    }

    @ApiOperation(value="增加APP下载次数的接口（在用户点击下载之后，调用此接口）", notes="增加APP下载次数的接口（在用户点击下载之后，调用此接口）", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/down/num/{appId}")
    public JsonResponse addAppDown(@PathVariable String appId){
        apkService.addDownNum(appId);
        return WebUtil.successJsonResponse("操作成功");
    }


    @ApiOperation(value="安卓客户端检查更新接口（通过包名查询）", notes="安卓客户端检查更新接口（通过包名查询）", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/check/update")
    public JsonResponse checkUpdate(@Valid @RequestBody AppCheckUpdate appCheckUpdate, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return WebUtil.errorJsonResponse(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        List<VersionDto> versionDtoList =new ArrayList<>();
        if(appCheckUpdate.getPackageName().indexOf(",")>-1){
            String [] names=appCheckUpdate.getPackageName().split(",");
            for(int i =0;i<names.length;i++){
                ApkDto apkDto =apkService.findByPackageName(names[i]);
                if(apkDto!=null){
                    VersionDto versionDto =versionService.getLastVersion(apkDto.getId(),AppType.ANDROID_APP);
                    if(versionDto!=null){
                        versionDto.setPackageName(apkDto.getPackageName());
                        versionDto.setAppLogo(apkDto.getLogo());
                        versionDtoList.add(versionDto);
                    }
                }
            }
        }else{
            ApkDto apkDto =apkService.findByPackageName(appCheckUpdate.getPackageName());
            if(apkDto!=null){
                VersionDto versionDto =versionService.getLastVersion(apkDto.getId(),AppType.ANDROID_APP);
                if(versionDto!=null){
                    versionDto.setPackageName(apkDto.getPackageName());
                    versionDto.setAppLogo(apkDto.getLogo());
                    versionDtoList.add(versionDto);
                }
            }
        }
        return WebUtil.successJsonResponse("获取版本信息成功",versionDtoList);
    }

}
