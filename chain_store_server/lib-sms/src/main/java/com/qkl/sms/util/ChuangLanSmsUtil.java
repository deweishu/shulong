package com.qkl.sms.util;

import com.qkl.common.util.JsonUtil;
import com.qkl.sms.dto.ClSmsSendRequest;
import com.qkl.sms.dto.ClSmsSendResponse;
import com.qkl.sms.service.SmsSendCallback;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 创蓝短信工具类
 * Created by dengjihai on 2018/5/15.
 */
public class ChuangLanSmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChuangLanSmsUtil.class);

    public static final String CHARSET_UTF8 = "UTF-8";

    // 创蓝API地址 普通短信
    private static final String URL = "http://smssh1.253.com/msg/send/json";
    // 创蓝API账号 普通短信
    private static final String ACCOUNT = "N7160410";
    // 创蓝API密码 普通短信
    private static final String PASSWORD = "HX7tVAzT4W62c7";

    // 创蓝API地址 语音短信
    private static final String VOICE_URL = "http://zapi.253.com/msg/";
    // 创蓝API账号 语音短信
    private static final String VOICE_ACCOUNT = "V6254214";
    // 创蓝API密码 语音短信
    private static final String VOICE_PASSWORD = "HjkV9fU8P621a5";

    // 短信签名
    private static final String MSG_PREFIX = "【ChainStore】";


    /**
     * 发送短信
     * @param mobile 手机号码
     * @param msg 短信内容
     * @return
     */
    public static void sendSms(String mobile, String msg, SmsSendCallback callback) {
        msg = setMsgSign(msg);
        ClSmsSendRequest sendRequest = new ClSmsSendRequest(ACCOUNT, PASSWORD, msg, mobile, Boolean.TRUE.toString());
        String requestJson = JsonUtil.beanToJson(sendRequest);
        sendSmsByPost(URL, requestJson, callback);
    }

    /**
     * 发送普通短信，POST方式
     * @param path
     * @param postContent JSON参数
     * @return String
     * @throws
     */
    private static void sendSmsByPost(String path, String postContent, SmsSendCallback callback) {
        boolean resultFlag = false;
        String resultMsg = "发送短信失败";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", CHARSET_UTF8);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(postContent.getBytes(CHARSET_UTF8));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), CHARSET_UTF8));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                logger.info("发送短信结果：{}", sb.toString());
                ClSmsSendResponse sendResponse = JsonUtil.jsonToBean(sb.toString(), ClSmsSendResponse.class);
                if (null!=sendResponse && "0".equals(sendResponse.getCode())) {
                    resultMsg = "发送短信成功";
                    resultFlag = true;
                }
            }

        } catch (Exception e) {
            logger.error("发送短信至创蓝时发生异常：", e);
        } finally {
            if (null != callback) {
                callback.call(resultFlag, resultMsg);
            }
        }
    }


    /**
     * 发送语音短信
     * 支持批量发送，即多个号码使用","分割
     *
     * @param mobile 手机号码
     * @param msg 短信内容
     * @return
     */
    public static void sendVoiceSms(String mobile, String msg, SmsSendCallback callback) {
        msg = setMsgSign(msg);
        sendVoiceSms(VOICE_URL, VOICE_ACCOUNT, VOICE_PASSWORD, mobile, msg, Boolean.TRUE, null, callback);
    }

    /**
     * 发送语音短信
     * 支持批量发送，即多个号码使用","分割
     *
     * @param url        应用地址，类似于http://ip:port/msg/
     * @param account    账号
     * @param pswd       密码
     * @param mobile     手机号码，多个号码使用","分割
     * @param msg        短信内容
     * @param needstatus 是否需要状态报告，需要true，不需要false
     * @return 返回值定义参见HTTP协议文档
     */
    private static void sendVoiceSms(String url, String account, String pswd, String mobile, String msg,
                                   boolean needstatus, String extno, SmsSendCallback callback) {
        boolean resultFlag = false;
        String resultMsg = "发送短信失败";

        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        BufferedReader br = null;
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "HttpBatchSendSM", false));
            method.setQueryString(new NameValuePair[]{
                    new NameValuePair("account", account),
                    new NameValuePair("pswd", pswd),
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("needstatus", String.valueOf(needstatus)),
                    new NameValuePair("msg", msg),
                    new NameValuePair("extno", extno),
            });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                String respContent = URLDecoder.decode(baos.toString(), CHARSET_UTF8);*/

                int loop = 0;
                String str = "";
                String respContent = "";
                br = new BufferedReader(new InputStreamReader(in, CHARSET_UTF8));
                /*返回例子：
                20180515114903,0
                1000515114903625600*/
                while ((str = br.readLine()) != null) {
                    // 读取第一行
                    if (0 == loop) {
                        String[] arr = str.split(",");
                        if (arr.length>1 && "0".equals(arr[1])) {
                            resultMsg = "发送短信成功";
                            resultFlag = true;
                        }
                    }
                    loop++;
                    respContent = respContent + str;
                }
                logger.info("发送语音短信结果：{}", respContent);
            } else {
                logger.error("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } catch (Exception e) {
            logger.error("发送语音短信至创蓝时发生异常：", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("发送语音短信后关闭BufferedReader时异常：", e);
                }
            }

            method.releaseConnection();
            if (null != callback) {
                callback.call(resultFlag, resultMsg);
            }
        }
    }


    /**
     * 设置短信签名
     * @param msg
     * @return
     */
    public static String setMsgSign(String msg) {
        return msg.startsWith(MSG_PREFIX) ? msg : MSG_PREFIX + msg;
    }

}
