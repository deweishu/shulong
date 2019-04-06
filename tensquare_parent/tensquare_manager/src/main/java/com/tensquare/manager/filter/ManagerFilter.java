package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 0;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Autowired
    private JwtUtil jwtUtil;
    @Override
    //进行鉴权 不是管理员角色, 统一的拦截(权限不足,跨域不拦[客户端刚开始的请求就跨域的],   登录不拦)
    public Object run() throws ZuulException {
        //1.获得request
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //2.判断是否是跨域
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return  null;
        }

        //3.判断是否是登录
        if(request.getRequestURI().contains("/admin/login")){
            System.out.println("是登录, 不拦...");
            return  null;
        }

        //4, 登录了, 并且角色是管理员
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer")){
            //把authorization再添加
            requestContext.addZuulRequestHeader("Authorization",authorization);

            String token = authorization.substring(7);
            //5.解析token , 获得角色, 判断是否是管理员
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null && claims.get("roles").equals("admin")){
                return  null;
            }
        }

        //6.拦截, 不让访问
        requestContext.setSendZuulResponse(false); //阻止访问
        requestContext.setResponseStatusCode(401); //设置状态码
        requestContext.setResponseBody("No Privilege"); //设置响应的内容
        requestContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}
