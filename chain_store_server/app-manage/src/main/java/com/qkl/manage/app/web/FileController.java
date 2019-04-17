package com.qkl.manage.app.web;


import com.qkl.admin.bean.SysConfigKey;
import com.qkl.admin.dto.ConfigDto;
import com.qkl.admin.service.ConfigService;
import com.qkl.apk.dto.ImgDto;
import com.qkl.apk.service.ImgService;
import com.qkl.common.bean.oss.OSSManager;
import com.qkl.common.enums.OssDirType;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/file")
public class FileController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImgService imgService;

    @Autowired
    OSSManager ossManager;

    @Autowired
    ConfigService configService;

    @RequestPermission(value = AdminPermission.MY_APP_LIST)
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map upload(HttpServletRequest request) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String infoId=request.getParameter("infoId");
        Integer imgNum=imgService.getImgNumByApk(infoId);
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.APP_IMG_MAX_NUM.getCode());
        Assert.isTrue(imgNum<configDto.getConfigNum(),"应用演示图最多只能上传"+configDto.getConfigNum()+"张图");
        logger.info("\n #### infoid:{}",infoId);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile mainImgFile =multipartRequest.getFile(itr.next());
        InputStream is = mainImgFile.getInputStream();
        String fileName = mainImgFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String tempFileName = StringUtil.getUUID() +"."+ suffix;
        // 上传到OSS上
        String ossUrl = ossManager.upload(OssDirType.IMG_PATH, Boolean.TRUE, is, tempFileName);
        map.put("error",0);
        map.put("url",ossUrl);
        if(StringUtil.isNotNil(infoId)){
            ImgDto imgDto = new ImgDto();
            imgDto.setImgUrl(ossUrl);
            imgDto.setApkId(infoId);
            imgService.save(imgDto);
        }
        return map;
    }


    /**
     * 进入图片列表页面
     * @return
     */
    @GetMapping("/list/{objectId}")
    public String imgList(@PathVariable String objectId, ModelMap modelMap) throws ExecutionException, InterruptedException {

        List<ImgDto> imgDtos=imgService.getAllImgByApkId(objectId);
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.APP_IMG_MAX_NUM.getCode());
        modelMap.addAttribute("num",configDto.getConfigNum());
        modelMap.addAttribute("imgList",imgDtos);
        modelMap.addAttribute("objectId",objectId);
        return "/apk/image/list";
    }

    /**
     * 校验应用演示图的张数
     * @param objectId
     * @return
     */
    @ResponseBody
    @GetMapping("/valid/add/{objectId}")
    public JsonResponse validateUpload(@PathVariable String objectId){
        return WebUtil.successJsonResponse("获取校验结果成功",imgService.validateAddImg(objectId));
    }


    /**
     * 进入图片上传页面
     * @return
     */
    @RequestPermission(value = AdminPermission.MY_APP_LIST)
    @GetMapping("/webupload/{objectId}")
    public String webUpload(@PathVariable String objectId, ModelMap modelMap) throws ExecutionException, InterruptedException {
        Integer imgNum=imgService.getImgNumByApk(objectId);
        ConfigDto configDto=configService.getConfigByKey(SysConfigKey.APP_IMG_MAX_NUM.getCode());
        modelMap.addAttribute("imgNum",configDto.getConfigNum()-imgNum);
        modelMap.addAttribute("objectId",objectId);
        return "/apk/image/upload";
    }

}
