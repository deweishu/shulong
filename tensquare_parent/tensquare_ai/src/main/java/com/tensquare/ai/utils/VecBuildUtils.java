package com.tensquare.ai.utils;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;

import java.io.File;
import java.io.IOException;

/**
 * 词向量工具类
 */
public class VecBuildUtils {

    /**
     * 构建词向量
     * @param wordLib  语料库文本文件
     * @param vecModel 词向量文件
     */
    public static void build(String wordLib,String vecModel){
        try {
            //加载数爬虫分词数据集
            SentenceIterator iter = new LineSentenceIterator(new File(wordLib));
            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(10) //词最少出现的次数, 只有出现这个次数以上才会学习
                    .iterations(1)  //学习迭代次数
                    .layerSize(100) //次向量维度
                    .seed(42) //随机种子
                    .windowSize(5) //关联词之间的距离
                    .iterate(iter) //指定训练的数据集
                    .build();
            vec.fit();
            //保存模型之前先删除
            new File(vecModel).delete();//删除
            WordVectorSerializer.writeWordVectors(vec, vecModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
