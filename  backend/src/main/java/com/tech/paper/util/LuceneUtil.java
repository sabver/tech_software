package com.tech.paper.util;

import com.tech.paper.domain.Paper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneUtil {
    public static Analyzer getAnalyzer(){
        return new StandardAnalyzer();
    }

    public static IndexWriter getIndexWriter(Directory directory) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
        return new IndexWriter(directory, config);
    }

    public static Directory getDirectory(String path) throws IOException {
        return FSDirectory.open(Paths.get(path));
    }

    public static Document getDocument(Paper paper,FieldType type){
        Document doc = new Document();
        doc.add(new Field("id", String.valueOf(paper.getId()), TextField.TYPE_STORED));
        doc.add(new Field("title", paper.getTitle(), type));
        doc.add(new Field("authors", paper.getAuthors(), type));
        doc.add(new Field("deadline", paper.getDeadline(), TextField.TYPE_STORED));
        doc.add(new Field("abs", paper.getAbs(), type));
        doc.add(new Field("url", paper.getUrl(), TextField.TYPE_STORED));
        doc.add(new Field("pdfUrl", paper.getPdfUrl(), TextField.TYPE_STORED));
        doc.add(new Field("typeName", paper.getTypeName(), TextField.TYPE_STORED));
        return doc;
    }

    public static FieldType getFieldType(){
        // 新建FieldType,用于指定字段索引时的信息
        FieldType type = new FieldType();
        // 索引时保存文档、词项频率、位置信息、偏移信息
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setStored(true);// 原始字符串全部被保存在索引中
        type.setStoreTermVectors(true);// 存储词项量
        type.setTokenized(true);// 词条化
        return type;
    }

    public static Paper getPaper(Document doc){
        Paper paper = new Paper();
        paper.setId(Long.valueOf(doc.get("id")));
        paper.setTitle(doc.get("title"));
        paper.setAuthors(doc.get("authors"));
        paper.setDeadline(doc.get("deadline"));
        paper.setAbs(doc.get("abs"));
        paper.setUrl(doc.get("url"));
        paper.setPdfUrl(doc.get("pdfUrl"));
        paper.setTypeName(doc.get("typeName"));
        return paper;
    }
}
