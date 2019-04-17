package com.qkl.manage.admin.web;

import com.qkl.admin.entity.Admin;
import com.qkl.admin.service.AdminService;
import com.qkl.common.bean.BaseStatus;
import com.qkl.common.util.EncryptionUtil;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.AccessUrlCache;
import com.qkl.manage.core.cache.AdminSingleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger illegalLoginLogger = LoggerFactory.getLogger("illegalLoginLogger");

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminSingleSession adminSingleSession;
    @Autowired
    private AccessUrlCache accessUrlCache;

    @GetMapping
    public String loginPage() {
        return "/page/login";
    }

    @PostMapping
    public String login(String username, String password, Model model) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addAttribute("errMsg", "用户名或者密码不能为空");
            logger.error("登录失败，用户名或者密码为空：username={}", username);
            return "/page/login";
        }

        Admin admin = adminService.getByUsername(username);
        if (admin == null || !admin.getPassword().equals(EncryptionUtil.encryptPassword(password))) {
            model.addAttribute("errMsg", "用户名或密码错误");
            illegalLoginLogger.error("登录失败，用户名密码错误username:{}", username);
            return "/page/login";
        }
        if (admin.getStatus() == BaseStatus.DISABLED) {
            model.addAttribute("errMsg", "该用户已禁用，若要继续使用，请联系管理员");
            illegalLoginLogger.error("登录失败，该用户已禁用username:{}", username);
            return "/page/login";
        }
        model.addAttribute("user", admin);
        adminSingleSession.setUser(admin);

        String redirectUrl = WebUtil.getAccessUrl(accessUrlCache, adminSingleSession.getSessionId());
        logger.info("获取-login-redirectUrl=" + redirectUrl);
        if (StringUtil.isNil(redirectUrl)) {
            redirectUrl = WebUtil.getDomainBasePath()+"/";
        } else {
            // 获取值后需清空，防止重复重定向
            accessUrlCache.remove(adminSingleSession.getSessionId());
        }

        return "redirect:" + redirectUrl;
    }

}
