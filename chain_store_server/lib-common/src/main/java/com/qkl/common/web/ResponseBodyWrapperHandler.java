package com.qkl.common.web;

import com.qkl.common.util.KeepJsonUtil;
import com.qkl.common.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by niejiuqian on 2017/9/13.
 * json格式响应参数统一封装处理
 * <p>
 *     如果不需要使用此封装，则在Controller的方法入口上增加ReturnTypeNotResolved即可
 *     还有返回格式为JsonResponse也不处理
 * </p>
 */
@Component
public class ResponseBodyWrapperHandler extends RequestResponseBodyMethodProcessor {

    static Logger logger = LoggerFactory.getLogger(ResponseBodyWrapperHandler.class);
    public ResponseBodyWrapperHandler() {
        super(new ArrayList<>(Arrays.asList(new MappingJackson2HttpMessageConverter())));
    }

    public ResponseBodyWrapperHandler(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public ResponseBodyWrapperHandler(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager) {
        super(converters, manager);
    }

    public ResponseBodyWrapperHandler(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
        super(converters, requestResponseBodyAdvice);
    }

    public ResponseBodyWrapperHandler(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        /**
         * 将标注了ReturnTypeNotResolved注解的返回值、返回方法过滤掉
         * 如果返回false，将不会执行handleReturnValue函数
         */
        ReturnTypeNotResolved typeNotResolved = returnType.getMethodAnnotation(ReturnTypeNotResolved.class);
        if (null != typeNotResolved) {
            return false;
        }
        typeNotResolved = returnType.getParameterAnnotation(ReturnTypeNotResolved.class);
        if (null != typeNotResolved){
            return false;
        }
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        /**
         * 对Controller返回业务数据进行统一封装处理。
         * 能返回到这里的，说明业务处理没有问题，所以，返回code跟msg为成功
         * 将业务数据returnVlaue赋值给data
         */
        //响应数据统一处理
        JsonResponse jsonResponse = null;
        if(returnValue != null && returnValue instanceof  JsonResponse){
            jsonResponse = (JsonResponse) returnValue;
        } else {
            jsonResponse = WebUtil.successJsonResponse("操作成功",returnValue);
        }
        String resultJson = KeepJsonUtil.beanToJson(jsonResponse);
        logger.info(" \n返回客户端json :\n "+ resultJson);
        ResponseHelper.write(((HttpServletResponse)webRequest.getNativeResponse()),resultJson);
    }
}
