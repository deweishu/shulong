package com.qkl.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author dengjihai
 * @create 2018-10-09
 **/
@Component
public class PlistFileUtil {

    private static Logger logger= LoggerFactory.getLogger(PlistFileUtil.class);


    @Value(value = "${spring.profiles.active}")
     String springProfilesActive;


    public  File createPlist(String downUrl,String fileName,String versionName,String appName) throws IOException{
        logger.info("==========开始创建plist文件");
        //这个地址应该是创建的服务器地址，在这里用生成到本地磁盘地址
        String path = "C:/plists/";
        //如果是linux环境，那就是这个路径
        if(springProfilesActive.equals("pre")){
            path="/home/plist";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String plistFile = fileName;
        final String PLIST_PATH = path + plistFile;
        file = new File(PLIST_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                "<plist version=\"1.0\">\n" +
                "<dict>\n" +
                "\t<key>items</key>\n" +
                "\t<array>\n" +
                "\t\t<dict>\n" +
                "\t\t\t<key>assets</key>\n" +
                "\t\t\t<array>\n" +
                "\t\t\t\t<dict>\n" +
                "\t\t\t\t\t<key>kind</key>\n" +
                "\t\t\t\t\t<string>software-package</string>\n" +
                "\t\t\t\t\t<key>url</key>\n" +
                "\t\t\t\t\t<string>"+downUrl+"</string>\n" +
                "\t\t\t\t</dict>\n" +
                "\t\t\t\t<dict>\n" +
                "\t\t\t\t\t<key>kind</key>\n" +
                "\t\t\t\t\t<string>display-image</string>\n" +
                "\t\t\t\t\t<key>needs-shine</key>\n" +
                "\t\t\t\t\t<false/>\n" +
                "\t\t\t\t\t<key>url</key>\n" +
                "\t\t\t\t\t<string>https://upgradeapp.oss-cn-hangzhou.aliyuncs.com/upgradeapp/OKEx-128.png</string>\n" +
                "\t\t\t\t</dict>\n" +
                "\t\t\t\t<dict>\n" +
                "\t\t\t\t\t<key>kind</key>\n" +
                "\t\t\t\t\t<string>full-size-image</string>\n" +
                "\t\t\t\t\t<key>needs-shine</key>\n" +
                "\t\t\t\t\t<false/>\n" +
                "\t\t\t\t\t<key>url</key>\n" +
                "\t\t\t\t\t<string>https://upgradeapp.oss-cn-hangzhou.aliyuncs.com/upgradeapp/OKEx-128.png</string>\n" +
                "\t\t\t\t</dict>\n" +
                "\t\t\t</array>\n" +
                "\t\t\t<key>metadata</key>\n" +
                "\t\t\t<dict>\n" +
                "\t\t\t\t<key>bundle-identifier</key>\n" +
                "\t\t\t\t<string>com.lyc.lyc</string>\n" +
                "\t\t\t\t<key>bundle-version</key>\n" +
                "\t\t\t\t<string>"+versionName+"</string>\n" +
                "\t\t\t\t<key>kind</key>\n" +
                "\t\t\t\t<string>software</string>\n" +
                "\t\t\t\t<key>subtitle</key>\n" +
                "\t\t\t\t<string>"+appName+"</string>\n" +
                "\t\t\t\t<key>title</key>\n" +
                "\t\t\t\t<string>"+appName+"</string>\n" +
                "\t\t\t</dict>\n" +
                "\t\t</dict>\n" +
                "\t</array>\n" +
                "</dict>\n" +
                "</plist>\n";
        try {
            FileOutputStream output = new FileOutputStream(file);
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(output, "UTF-8");
            writer.write(plist);
            writer.close();
            output.close();
        } catch (Exception e) {
            System.err.println("==========创建plist文件异常：" + e.getMessage());
        }
        logger.info("==========成功创建plist文件");
        return file;
    }
//
//    public static void main(String[] args) throws IOException {
//        String plistPath = createPlist("http://127.0.0.1/project/upload/files/20160504201048174_7836_19.ipa");
//    }
//


}
