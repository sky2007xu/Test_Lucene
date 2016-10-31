package com.xu.lucene.query;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

/**
 * Created by sky-fly on 2016/10/29.
 */
public class Query子类 {
    public static void main(String[] args) throws Exception{
        // 创建IndexSearcher
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

//        TermQuery：关键字查询
        Query query1 = new TermQuery(new Term("name","杨"));
//        PrefixQuery：前缀查询
        Query query2 = new PrefixQuery(new Term("name","晓"));
//        WildcardQuery：通配符查询
        Query query3 = new WildcardQuery(new Term("name","*"));
//        FuzzyQuery：模糊查询
        Query query4 = new FuzzyQuery(new Term("name","%杨"));
//        RegexpQuery：正则表达式查询
        Query query5 = new RegexpQuery(new Term("name","杨|明")); // |表示或的关系

//        BooleanQuery：布尔查询，用来组合其他查询
        BooleanQuery bool = new BooleanQuery();
        bool.add(query1, BooleanClause.Occur.MUST); // query1必须要有
        bool.add(query2, BooleanClause.Occur.MUST_NOT); // query2必须没有
        bool.add(query3, BooleanClause.Occur.SHOULD); // query3可能要有

//        PhraseQuery：短语查询
//        MultiPhraseQuery：多短语查询
//        TermRangeQuery：关键字范围查询
//        NumericRangeQuery：数字范围查询

        TopDocs topDocs = searcher.search(bool,8);

        System.out.println("最好的分数:" + topDocs.getMaxScore());
        System.out.println("命中的记录数:" + topDocs.totalHits);
        // 获取分数文档数组，因为进行了打分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("文档的id:" + scoreDoc.doc); // 是Lucene自动生成的那个id，查询出来的结果只是一个id记录
            System.out.println("文档的分数:" + scoreDoc.score); // 文档的分数
            // 调用IndexSearcher来获取记录文档
            Document doc = searcher.doc(scoreDoc.doc);
            // 获取字段,必须是存储过的字段
            System.out.println(doc.get("id") + "\t" + doc.get("name") + "\t" + doc.get("age"));
        }
        reader.close();
    }
}
