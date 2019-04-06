package com.tensquare.base.controller;


import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常处理器
 */

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public  Result error(Exception e){
        //打印信息
        e.printStackTrace();
        return  new Result(false, StatusCode.ERROR,e.getMessage());
    }

}
