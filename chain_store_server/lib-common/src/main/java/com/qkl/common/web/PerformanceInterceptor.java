package com.qkl.common.web;

import com.google.common.collect.Maps;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class PerformanceInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.nanoTime());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long timeTaken = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime.get());
        String uri = String.format("%s(%s)", request.getRequestURI(), request.getMethod());
        Map<String, Object> map = Maps.newHashMap();
        map.put("URI", uri);
        map.put("TIME_TOKEN", timeTaken);
        logger.info(Markers.appendEntries(map), "{} taken {}ms", uri, timeTaken);

        startTime.remove();
    }
}
