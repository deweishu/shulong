package util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * 分词工具类
 */
public class IKUtil {

    /**
     * 根据文本返回分词后的文本
     * @param content  需要分词的内容
     * @param splitChar 分词后的分隔符
     * @return
     * @throws IOException
     */
    public  static String split(String content,String splitChar) throws IOException {
        StringReader sr=new StringReader(content);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        StringBuilder sb=new StringBuilder("");
        while((lex=ik.next())!=null){
            sb.append(lex.getLexemeText()+splitChar);//拼接
        }
        return sb.toString();
    }


}
