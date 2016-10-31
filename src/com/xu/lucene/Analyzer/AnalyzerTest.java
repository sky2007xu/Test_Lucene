package com.xu.lucene.Analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sky-fly on 2016/10/23.
 */
public class AnalyzerTest {

    private static String keywords = "毛泽东我只是一个程序员，我要吃饭";

    // 单字分词器
    @Test
    public void test1() throws IOException{

        // 查看默认的停用词
        CharArraySet defaultStopWords = StandardAnalyzer.STOP_WORDS_SET;
        System.out.println(defaultStopWords);

        // 创建自己的停用词
        CharArraySet stopWords = new CharArraySet(Version.LUCENE_40,0,true);
        stopWords.add("个");
        // 加上默认的停用词，不然原有的会被覆盖掉
        stopWords.addAll(defaultStopWords);
        // 创建分词器
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40,stopWords);
        // 进行分词
        TokenStream tokenStream = analyzer.tokenStream(null,new StringReader(keywords));
        // 迭代
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            // 返回一个关键字
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(term.toString());
        }
        analyzer.close(); // 关闭分词器


    }

    // 二分法分词器,太不好了
    @Test
    public void test2() throws IOException{

        // 查看二分法默认的停用词
        CharArraySet defaultStopWords = CJKAnalyzer.getDefaultStopSet();
        System.out.println(defaultStopWords);

        // 创建自己的停用词
        CharArraySet stopWords = new CharArraySet(Version.LUCENE_40,0,true);
        stopWords.add("一个"); // 二分法要加两个汉字
        // 加上默认的停用词，不然原有的会被覆盖掉
        stopWords.addAll(defaultStopWords);


        // 创建二分法分词器
        Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_40,stopWords);
        // 进行分词
        TokenStream tokenStream = analyzer.tokenStream(null,new StringReader(keywords));
        // 迭代
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            // 返回一个关键字
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(term.toString());
        }
        analyzer.close(); // 关闭分词器


    }

    // 词库分词器,需要考入lucene-analyzers-smartcn-6.1.0.jar包
    @Test
    public void test3() throws IOException{

        // 查看停用词
        CharArraySet defaultStopWords = SmartChineseAnalyzer.getDefaultStopSet();
        System.out.println(defaultStopWords);

        // 创建自己的停用词
        CharArraySet stopWords = new CharArraySet(Version.LUCENE_40,0,true);
        stopWords.add("一个");
        // 加上默认的停用词
        stopWords.addAll(defaultStopWords);


        // 创建分词器
        Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_40,stopWords); // 词库分词器
        // 进行分词
        TokenStream tokenStream = analyzer.tokenStream(null,new StringReader(keywords));
        // 迭代
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            // 返回一个关键字
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(term.toString());
        }
        analyzer.close(); // 关闭分词器


    }

}
