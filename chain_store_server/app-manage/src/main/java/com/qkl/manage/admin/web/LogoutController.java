package com.qkl.manage.admin.web;

import com.qkl.common.helper.CookieHelper;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.RequestContext;
import com.qkl.common.web.session.SingleSession;
import com.qkl.manage.core.cache.AdminSingleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private AdminSingleSession adminSingleSession;

    @GetMapping
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        adminSingleSession.setToUnavaliable(adminSingleSession.getSessionId());
        adminSingleSession.cleanThreadSession();
        Cookie cookie = new Cookie(SingleSession.COOKIE_SESSION_ID, "");
        cookie.setMaxAge(0);
        CookieHelper.addCookie(response, cookie);

        String redirectUrl = WebUtil.getDomainBasePath() +"/"+ RequestContext.getModuleProperties().loginUri;
        return "redirect:" + redirectUrl;
    }

}
