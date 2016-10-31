package com.xu.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by sky-fly on 2016/10/29.
 */
public class ManagerIndex {
    public static void main(String[] args) throws Exception{
        select();
    }

    public static void save() throws IOException{
        // 创建索引写入器
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(directory,writerConfig);

        // 写入数据
        Document document = new Document();
        document.add(new StringField("id",String.valueOf(9), Field.Store.YES));
        document.add(new TextField("name","杨颖", Field.Store.YES));
        document.add(new IntField("age",27, Field.Store.YES));
        writer.addDocument(document);

        // 写入数据
        Document document2 = new Document();
        document2.add(new StringField("id",String.valueOf(10), Field.Store.YES));
        document2.add(new TextField("name","晓明", Field.Store.YES));
        document2.add(new IntField("age",40, Field.Store.YES));
        writer.addDocument(document2);

        // 提交数据后关闭IO流
        writer.commit();
        writer.close();
    }

    // 更新
    public static void update() throws IOException{
        // 创建索引写入器
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(directory,writerConfig);

        // 写入数据
        Document document = new Document();
        document.add(new StringField("id",String.valueOf(9), Field.Store.YES));
        document.add(new TextField("name","baby", Field.Store.YES));
        document.add(new IntField("age",27, Field.Store.YES));
        writer.updateDocument(new Term("id","9"),document); // 记住，需要传一个Term吧

        // 提交数据后关闭IO流
        writer.commit();
        writer.close();
    }

    // 删除
    public static void delete() throws IOException{
        // 创建索引写入器
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(directory,writerConfig);

//        writer.deleteAll();
        writer.deleteDocuments(new Term("id","9"));

        // 提交数据后关闭IO流
        writer.commit();
        writer.close();
    }

    // 查询
    public static void select() throws IOException{
        // 创建IndexReader
        Directory directory = FSDirectory.open(new File("E:\\testFile\\Lucene_idx_db"));
        IndexReader reader = DirectoryReader.open(directory); // 通过reader可以知道获取索引库的总体数据。

        System.out.println("文档的总数量:" + reader.numDocs());
        for (int i = 0; i < reader.numDocs(); i++) { // 这个读取所以的文档，不能带查询条件
            Document document = reader.document(i);
            System.out.println(document.get("id") + document.get("name") + document.get("age"));
        }

        reader.close(); // 为什么要进行close呢，因为读的时候要对文件进行加锁的，所以用完之后要关掉。
    }


}
