package com.qkl.common.web;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResponseHeader {

    private Integer code;

    private String description;

    public static ResponseHeader newInstance(Integer code, String description) {
        ResponseHeader responseCode = new ResponseHeader();
        responseCode.code = code;
        responseCode.description = description;

        return responseCode;
    }

    /**
     * 构建excel响应消息头部
     * @param response
     * @param excelFileName
     * @throws UnsupportedEncodingException
     */
    public static void buildResponseExcelHeader(HttpServletResponse response, String excelFileName) throws UnsupportedEncodingException {
        response.setContentType("application/binary; charset = UTF-8");
        excelFileName = URLEncoder.encode(excelFileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + excelFileName + ".xlsx");
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
