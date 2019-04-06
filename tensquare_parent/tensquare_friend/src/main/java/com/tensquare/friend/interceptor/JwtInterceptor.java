package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1. 创建一个类实现HandlerInterceptor
 * 2. 配置拦截器
 */

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    //返回true 就是放行(就是在控制器的方法调用之前执行)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("JwtInterceptor preHandle()");
        //进行验证, 验证通过了, 存到request对象里面
        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            //截取字符串 获得token
            String token = authorization.substring(7);
            //解析
            Claims claims = jwtUtil.parseJWT(token);
            if(claims != null){
                String roles = (String) claims.get("roles");

                //判断是管理员
                if("admin".equals(roles)){
                    request.setAttribute("role_admin",claims);
                }
                //判断是普通用户
                if("user".equals(roles)){
                    request.setAttribute("role_user",claims);
                }
            }
        }
        return true;
    }
}
