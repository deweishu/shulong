package com.qkl.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by niejiuqian on 2017/10/19.
 * 将request中的inputStream取出来copy一份，保证拦截器中能获取到请求参数信息，且不影响整个数据的流向
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {

    private static Logger logger = LoggerFactory.getLogger(MyRequestWrapper.class);
    private byte[] body;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    private void loadBody(HttpServletRequest request) throws IOException{
        body = read(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }

    public static byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public byte[] getBody() {
        return body;
    }

    public String getStringBody(){
        try {
            return new String(getBody(),"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(),ex);
            return null;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }
}
