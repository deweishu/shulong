package com.qkl.manage.push.web;

import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.apk.dto.ApkDto;
import com.qkl.apk.dto.ApkSortDto;
import com.qkl.apk.service.ApkService;
import com.qkl.common.mq.UserPushSender;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import com.qkl.msg.bean.PushType;
import com.qkl.msg.dto.PushLogDto;
import com.qkl.msg.dto.PushLogReq;
import com.qkl.msg.dto.SendPushDto;
import com.qkl.msg.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengjihai
 * @create 2018-10-19
 **/
@Controller
@RequestMapping("/push")
public class PushController extends BaseController {


    @Autowired
    PushService pushService;

    @Autowired
    AdminSingleSession adminSingleSession;

    @Autowired
    ApkService apkService;


    @GetMapping("/index")
    @RequestPermission(value = AdminPermission.PUSH_MSG_LOG)
    @MenuMapping(value = "APP推送管理", menu = BoardMenu.ADMIN, weight = 21)
    public String pushLog(){
        return "push/push_log_list";
    }


    @RequestPermission(value = AdminPermission.SYS_LOG_LIST)
    @PostMapping("/data")
    @ResponseBody
    public Page<PushLogDto> pushData(@RequestParam(value = "searchText", required = false) String searchText) {
        PushLogReq pushLogReq = new PushLogReq();
        pushLogReq.setQueryLike(searchText);
        pushLogReq.setPageSize(getPageRequest().getPageSize());
        pushLogReq.setPageNumber(getPageRequest().getPageNumber());
        Page<PushLogDto> pageData = pushService.findPage(pushLogReq);
        return pageData;
    }


    @GetMapping("/send")
    @RequestPermission(value = AdminPermission.SEND_MSG)
    public String sendPush(){
        return "push/send_push";
    }


    @RequestPermission(value = AdminPermission.SEND_MSG)
    @PostMapping("/msg/send")
    @ResponseBody
    @SystemLogAnnotation(desc = "发送了一则消息通知")
    public JsonResponse pushMsg(SendPushDto sendPushDto){
        sendPushDto.setOperateId(adminSingleSession.getUserId());
        sendPushDto.setOperateName(adminSingleSession.getUser().getRealname());
        sendPushDto.setPushType(PushType.GROUP.getCode());
        String apkId=sendPushDto.getApkId();
        if(StringUtil.isNotNil(apkId)){
            logger.info("\n ## 推送--选择了apkid,{}",apkId);
            ApkDto apkDto =apkService.findOne(apkId);
            sendPushDto.setContent(JsonUtil.beanToJson(apkDto));
        }else{
            logger.info("\n ### 推送--未选择APK");
        }
        String id=pushService.sendPush(sendPushDto);
        UserPushSender.send(id);
        return WebUtil.successJsonResponse("发送成功");
    }

}
