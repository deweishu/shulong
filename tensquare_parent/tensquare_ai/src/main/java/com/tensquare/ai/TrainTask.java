package com.tensquare.ai;

import com.tensquare.ai.service.CnnService;
import com.tensquare.ai.service.Word2VecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TrainTask {

    @Autowired
    private Word2VecService word2VecService;

    @Autowired
    private CnnService cnnService;

    @Scheduled(cron = "25 12 17 * * ?")
    public void train() throws Exception {
        //System.out.println("分词语料库合并开始...");
        //word2VecService.merge();
        //System.out.println("分词语料库合并结束...");

        // System.out.println("词向量转换开始...");
        // word2VecService.buildVec();
        // System.out.println("词向量转换结束...");

        System.out.println("神经网络卷积模型构建开始...");
        cnnService.buildCnn();
        System.out.println("神经网络卷积模型构建结束...");

    }


}
