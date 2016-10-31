package com.xu.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;

public class SearchIndex {
    public static void main(String[] args) throws Exception{
        // 创建IndexSearcher，需要指定到哪个索引库进行检索

        // 1.创建IndexReader
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        IndexReader reader = DirectoryReader.open(directory);
        // 2.创建IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        // 进行检索
        // 两个参数，第一个是查询对象，下面使用的是关键字查询
        Query query = new TermQuery(new Term("fileContent","花")); // 选择关键字查询
        // 第二个是返回的记录数
        TopDocs topDocs = indexSearcher.search(query,8);

        System.out.println("最好的分数:" + topDocs.getMaxScore());
        System.out.println("命中的记录数:" + topDocs.totalHits);
        // 获取分数文档数组，因为进行了打分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("文档的id:" + scoreDoc.doc); // 是Lucene自动生成的那个id，查询出来的结果只是一个id记录
            System.out.println("文档的分数:" + scoreDoc.score); // 文档的分数
            // 调用IndexSearcher来获取记录文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            // 获取字段,必须是存储过的字段
            System.out.println(doc.get("id") + "\t" + doc.get("fileName") + "\t" + doc.get("filePath"));
        }
        reader.close();
    }
}
