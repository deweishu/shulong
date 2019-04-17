/**
 *
 */
package com.qkl.manage.core.web;

import com.qkl.common.web.ModuleProperties;
import com.qkl.manage.core.cache.AdminSingleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求拦截器，设置当前请求类型、来源、权限等信息
 *
 * @author Benson
 */
@Component
public class ConsoleLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    AdminSingleSession adminSingleSession;

    /*@Autowired
    PubOperLogService operateLogService;*/

    @Autowired
    ModuleProperties moduleProperties;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*if (adminSingleSession.getUser() != null) {
            PubOperLogDto operateLog = new PubOperLogDto();
            operateLog.setModule(moduleProperties.module);
            operateLog.setOperatorId(adminSingleSession.getUser().getId());
            operateLog.setMethod(RequestContext.getRequestInfo().getRequestHttpMethod().name());
            operateLog.setUri(request.getRequestURI());
            operateLog.setParameter(RequestContext.getRequestInfo().getRequestHttpMethod().equals(HttpMethod.POST) ? getPostRequestParameter(request) : request.getQueryString());
            operateLogService.save(operateLog);
        }*/
    }

    public String getPostRequestParameter(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            if (key.toLowerCase().equals("passwd") || key.toLowerCase().equals("password")) {
                continue;
            }
            String[] values = params.get(key);
            for (String value : values) {
                sb.append(key + "=" + value + "&");
            }
        }

        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
    }
}
