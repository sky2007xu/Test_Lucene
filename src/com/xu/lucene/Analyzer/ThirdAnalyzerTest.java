package com.xu.lucene.Analyzer;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sky-fly on 2016/10/23.
 */
public class ThirdAnalyzerTest {

    private static String keywords = "毛泽东,徐玉东都是一个伟大的程序员，我要吃饭";
    // IK词库分词器,需要拷入jar包
    @Test
    public void test1() throws IOException {
        // 创建IK分词器
        Analyzer analyzer = new IKAnalyzer(); // 词库分词器
        // 进行分词
        TokenStream tokenStream = analyzer.tokenStream(null,new StringReader(keywords));
        // 迭代
//        tokenStream.reset();
        while (tokenStream.incrementToken()){
            // 返回一个关键字
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(term.toString());
        }
        analyzer.close(); // 关闭分词器
    }

    // mmseg词库分词器,需要拷入jar包
    @Test
    public void test2() throws IOException {
        // 创建分词器
        Analyzer analyzer = new ComplexAnalyzer(); // 词库分词器
        // 进行分词
        TokenStream tokenStream = analyzer.tokenStream(null,new StringReader(keywords));
        // 迭代
//        tokenStream.reset();
        while (tokenStream.incrementToken()){
            // 返回一个关键字
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(term.toString());
        }
        analyzer.close(); // 关闭分词器
    }


}


