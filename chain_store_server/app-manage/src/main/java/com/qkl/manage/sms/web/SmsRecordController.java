package com.qkl.manage.sms.web;

import com.qkl.common.permission.RequestPermission;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import com.qkl.msg.dto.SmsRecordDto;
import com.qkl.msg.dto.SmsRecordReq;
import com.qkl.msg.service.SmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dengjihai on 2018/8/10.
 */
@Controller
@RequestMapping(value = "/sms/record")
public class SmsRecordController extends BaseController {

    @Autowired
    private SmsRecordService smsRecordService;


    @RequestPermission(value = AdminPermission.SMS_RECORD)
    @MenuMapping(value = "短信记录", menu = BoardMenu.ADMIN, weight = 10)
    @GetMapping
    public String toSmsRecordPage() {
        return "sms/list";
    }


    @RequestPermission(value = AdminPermission.SMS_RECORD)
    @PostMapping("/data")
    @ResponseBody
    public Page<SmsRecordDto> data(SmsRecordReq smsRecordReq) {
        smsRecordReq.setPageSize(getPageRequest().getPageSize());
        smsRecordReq.setPageNumber(getPageRequest().getPageNumber());
        return smsRecordService.findPage(smsRecordReq);
    }

}
