package com.xu.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateIndex {
    public static void main(String[] args) throws IOException {
        // 创建IndexWriter对象
        // 两个参数，第一个参数存储路径
//        Directory directory = FSDirectory.open(Paths.get("E:\\testFile\\Lucene_idx_db"));
//        Directory directory = new NIOFSDirectory(Paths.get("E:\\testFile\\Lucene_idx_db")); // 速度快
        Directory directory = new SimpleFSDirectory(new File("E:\\testFile\\Lucene_idx_db")); // 速度快
        // 第二个参数是配置信息,标准分词器，对单个汉字进行分词
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40,analyzer);
        // 创建模式和追加模式，创建模式是每次删除原有的，重新创建
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory,config);


        int cursor = 1;
        // 获取需要索引的数据
        // 创建文件目录
        File dir = new File("E:\\testFile\\Lucence_content");
        for (File file : dir.listFiles()) {
            // 1.创建Document
            Document document = new Document();
            // 2.文档中添加字段,字段名称，字段值，是否存储
            document.add(new StringField("id",String.valueOf(cursor++), Field.Store.YES));
            document.add(new TextField("fileName",file.getName(), Field.Store.YES));
            document.add(new TextField("filePath",file.getAbsolutePath(), Field.Store.YES));
            document.add(new TextField("fileContent",new InputStreamReader(new FileInputStream(file),"gbk")));

            FieldType type = new FieldType();
            type.setTokenized(true);
            type.setStored(true);
            Field field = new Field("field","ddd",type);

            document.add(field);

            // 把文档添加到索引库
            indexWriter.addDocument(document);
            indexWriter.commit();
        }
        // 关闭IndexWriter
        indexWriter.close();



    }
}
