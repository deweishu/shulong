package com.qkl.common.web;

/**
 * Created by niejiuqian on 2017/9/15.
 * 自定义异常
 */
public class AppException extends RuntimeException {

    private int code;//异常码

    private ResponseHeader responseHeader;

    public AppException(ResponseHeader responseHeader) {
        super(responseHeader.getDescription());
        this.responseHeader = responseHeader;
        this.code = responseHeader.getCode();
    }

    public AppException(String message,ResponseHeader responseHeader) {
        super(message);
        this.responseHeader = responseHeader;
        this.code = responseHeader.getCode();
    }

    public AppException(String msg) {
        super(msg);
        this.code = StandardResponseHeader.ERROR.getCode();
    }

    public AppException(String message, int code) {
        super(message);//异常信息
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }
}
