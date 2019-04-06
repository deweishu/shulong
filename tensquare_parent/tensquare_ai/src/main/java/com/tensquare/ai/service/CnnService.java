package com.tensquare.ai.service;


import com.tensquare.ai.utils.CnnUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CnnService {

    //语料库目录
    @Value("${ai.dataDir}")
    private String dataDir;

    //语料库汇总文本文件
    @Value("${ai.wordLib}")
    private String wordLib;

    //词向量模型文件(名字随便取)
    @Value("${ai.vecModel}")
    private String vecModel;

    @Value("${ai.cnnModel}")
    private String cnnModel;

    /**
     * 词神经网络卷积模型构建
     */
    public void buildCnn() throws Exception {
        CnnUtil.build(vecModel, dataDir, new String[]{"ai", "db", "web"}, cnnModel);
    }

    /**
     * 预测是哪一个类别 {"ai":0.8,"web":0.05,"db":0.15}
     */
    public Map predictions(String content) {
        try {
            Map<String, Double> map = CnnUtil.predictions(vecModel, cnnModel, dataDir, new String[]{"ai", "db", "web"}, content);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
