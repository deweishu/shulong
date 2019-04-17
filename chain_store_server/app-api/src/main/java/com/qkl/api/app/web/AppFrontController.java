package com.qkl.api.app.web;

import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.AppVersionService;
import com.qkl.admin.service.ConfigService;
import com.qkl.api.base.web.BaseController;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.dto.VersionDto;
import com.qkl.apk.service.ApkService;
import com.qkl.apk.service.ImgService;
import com.qkl.apk.service.VersionService;
import com.qkl.common.util.TimeUtil;
import com.qkl.common.web.HalfOpen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author dengjihai
 * @create 2018-09-10
 **/
@Controller
@RequestMapping("/h5/app")
public class AppFrontController extends BaseController {

    @Autowired
    ApkService apkService;

    @Autowired
    ImgService imgService;

    @Autowired
    VersionService versionService;


    @Autowired
    ConfigService configService;


    @Autowired
    AppVersionService appVersionService;

    /**
     * H5 前端进入app详情页面
     * @param appId
     * @return
     */
    @GetMapping("/info/{appId}")
    @HalfOpen
    public String appInfo(@PathVariable String appId, Model model){
        ApkDto apkDto=apkService.findOne(appId);
        Assert.notNull(apkDto,"不存在该应用");
        List<ImgDto> imgDtoList=imgService.getAllImgByApkId(appId);
        VersionDto versionDto =versionService.getLastVersion(appId,null);
        if(versionDto!=null){
            versionDto.setCreateDate(TimeUtil.dateFormat(versionDto.getCreateTime()));
        }
        model.addAttribute("appInfo",apkDto);
        model.addAttribute("imgList",imgDtoList);
        model.addAttribute("version",versionDto);
        model.addAttribute("android",appVersionService.getLastUpdate(10));
        return "app/app_info";
    }

    @GetMapping("/down")
    @HalfOpen
    public String down(Model model){
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.REG_CANDY.getCode());

        ConfigDto configDto1=configService.getConfigByKey(SysConfigKey.INVITE_FRIEND.getCode());
        model.addAttribute("regCandyNum",configDto.getConfigNum());
        model.addAttribute("invetNum",configDto1.getConfigNum());
        model.addAttribute("android",appVersionService.getLastUpdate(10));
        model.addAttribute("ios",appVersionService.getLastUpdate(20));
        return "app/down";
    }
}
