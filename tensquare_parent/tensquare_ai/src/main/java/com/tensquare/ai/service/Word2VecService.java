package com.tensquare.ai.service;


import com.tensquare.ai.utils.CnnUtil;
import com.tensquare.ai.utils.VecBuildUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.FileUtil;

import java.io.IOException;
import java.util.List;

@Service
public class Word2VecService {

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
     * 合并分词语料库
     */
    public void merge() throws Exception {
        List<String> list = FileUtil.getFiles(dataDir);
        //合并
        FileUtil.merge(wordLib,list);
    }

    /**
     * 词向量模型构建
     */
    public void buildVec() throws Exception {
        VecBuildUtils.build(wordLib,vecModel);
    }





}
