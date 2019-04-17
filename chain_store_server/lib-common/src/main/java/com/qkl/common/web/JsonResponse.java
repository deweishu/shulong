package com.qkl.common.web;

/**
 * JSON响应对象
 */
public class JsonResponse {

    private ResponseHeader header;

    private String msg;

    private Object data;

    public JsonResponse(){}
    private JsonResponse(ResponseHeader header, String msg, Object data) {
        this.header = header;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResponse newInstance(ResponseHeader header, String msg, Object data) {
        return new JsonResponse(header, msg, data);
    }

    public static JsonResponse newInstance(ResponseHeader header, Object data) {
        return new JsonResponse(header, null, data);
    }

    public Integer getCode() {
        return header.getCode();
    }

    public String getMsg() {
        return msg != null ? msg : header.getDescription();
    }

    public Object getData() {
        return data;
    }
}
