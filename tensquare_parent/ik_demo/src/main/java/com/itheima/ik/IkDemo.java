package com.itheima.ik;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class IkDemo {

    public static void main(String[] args) throws IOException {
        String str = "我是程序员";
        StringReader reader = new StringReader(str);

        //1.创建分词(段)器  参数2: true 最大切分; false: 最细切分
        IKSegmenter ikSegmenter = new IKSegmenter(reader,false);

        //2.分词 Lexeme对象代表分词后的词语
        Lexeme lexeme = null;
        while ((lexeme = ikSegmenter.next()) != null){
            System.out.println(lexeme.getLexemeText());
        }

        //3.释放
        reader.close();

    }

}
