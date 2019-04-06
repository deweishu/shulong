package com.tensquare.foreground.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站前端网关的过滤器
 */

@Component
public class WebFilter extends ZuulFilter {


    @Override
    //过滤的类型
    //pre ：可以在请求被路由之前调用
    //route ：在路由请求时候被调用
    //post ：在route和error过滤器之后被调用
    //error ：处理请求时发生错误时被调用
    public String filterType() {
        return "pre";
    }


    @Override
    //设置过滤的优先级. 数字越小, 优先级越高
    public int filterOrder() {
        return 0;
    }


    @Override
    //是否需要过滤, true 需要过滤, false: 不过滤
    public boolean shouldFilter() {
        return true;
    }


    @Override
    //过滤的逻辑代码编写的位置
    public Object run() throws ZuulException {
        System.out.println("WebFilter 执行了...");
        //通过网关来调用微服务, 请求头会丢失, 需要我们自己来获得再添加进去
        //1.获得requestContext
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获得request
        HttpServletRequest request = requestContext.getRequest();
        //3.获得Authorization认证头
        String authorization = request.getHeader("Authorization");
        //4. 非空判断
        if (authorization != null) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
