package com.qkl.manage.core.web;

import com.qkl.common.util.JsonUtil;
import com.qkl.manage.core.cache.AdminSingleSession;
import com.qkl.menu.MenuUtil;
import com.qkl.menu.UserMenu;
import com.qkl.menu.UserMenuListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求拦截器，设置当前请求类型、来源、权限等信息
 *
 * Created by dengjihai on 2018/3/7.
 */
@Component
public class ConsoleMenuInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleMenuInterceptor.class);

    public static final String MENU_REQUEST_ATTRIBUTE_KEY = "menu";

    private static final String MENU_SESSION_KEY = "menu";

    @Autowired
    private AdminSingleSession adminSingleSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserMenuListWrapper userMenuListWrapper = adminSingleSession.get(MENU_SESSION_KEY, UserMenuListWrapper.class);
        if (userMenuListWrapper == null) {
            List<UserMenu> userMenus = MenuUtil.getUserMenu(adminSingleSession.getUser());
            userMenuListWrapper = new UserMenuListWrapper(userMenus);
            adminSingleSession.put(MENU_SESSION_KEY, userMenuListWrapper);
            logger.info("\n菜单数据：" + JsonUtil.beanToJson(userMenuListWrapper));
        }

        request.setAttribute(MENU_REQUEST_ATTRIBUTE_KEY, JsonUtil.beanToJson(userMenuListWrapper.getUserMenus()));
        return true;
    }
}
