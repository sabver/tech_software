package com.tech.paper.service.impl;

import com.geccocrawler.gecco.GeccoEngine;
import com.tech.paper.app.InitApplication;
import com.tech.paper.crawler.ArxivDetailBean;
import com.tech.paper.domain.ArxivId;
import com.tech.paper.domain.ArxivIdRepository;
import com.tech.paper.domain.Paper;
import com.tech.paper.domain.PaperRepository;
import com.tech.paper.service.InitService;
import com.tech.paper.util.CommonUtil;
import com.tech.paper.util.LuceneUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;

@Service
public class InitServiceImpl implements InitService {

    private static final Logger log = LoggerFactory.getLogger(InitServiceImpl.class);

    @Resource
    private ArxivIdRepository arxivIdRepository;

    @Resource
    private PaperRepository paperRepository;

    @Autowired
    private Environment env;


    @Deprecated
    @Override
    public void init() {
        ArxivId arxivId = queryArxivId();
        log.info(arxivId.toString());
        explore(arxivId);
    }

    @Override
    public ArxivId queryArxivId() {
        ArxivId result = null;
        List<ArxivId> list = arxivIdRepository.findAll();
        if( list.size() == 0 ){
            result = new ArxivId();
            result.setYm("1901");
            result.setNumberStr("00001");
            result = arxivIdRepository.save(result);
        }else{
            result = list.get(0);
        }
        return result;
    }

    @Override
    public void checkErrorRecord() {

    }

    @Deprecated
    @Override
    public void explore(ArxivId cur) {
        log.info(CommonUtil.buildUrl(cur));
				GeccoEngine.create()
						.classpath("com.tech.paper.crawler")
						.start(CommonUtil.buildUrl(cur))
                        .interval(15000)
						.thread(1)
						.mobile(false)
						.start();
    }

    @Deprecated
    @Override
    public Paper fetchPaper(ArxivId target) {
        return null;
    }

    @Override
    public boolean savePaper(Paper paper) {
        try{
            paperRepository.save(paper);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveArxivId(ArxivId arxivId) {
        try{
            arxivIdRepository.save(arxivId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveLucene(Paper paper) throws IOException {
        Directory directory = LuceneUtil.getDirectory(env.getProperty("lucene.filepath"));
        IndexWriter writer = LuceneUtil.getIndexWriter(directory);
        FieldType type = LuceneUtil.getFieldType();
        Document doc = LuceneUtil.getDocument(paper,type);
        writer.addDocument(doc);
        writer.close();
        directory.close();
        return false;
    }

    @Override
    public Paper changeArxivToPaper(ArxivDetailBean bean) {
        String title = bean.getTitle(), abstrust = bean.getAbs();
        title = title.replace("<span class=\"descriptor\">Title:</span>","");
        abstrust = abstrust.replace("<span class=\"descriptor\">Abstract:</span>","");
        Paper paper = new Paper();
        paper.setTitle(title);
        paper.setAbs(abstrust);
        paper.setAuthors(bean.getAuthors().toString());
        paper.setDeadline(bean.getDeadline());
        paper.setTypeName(bean.getTypeName());
        log.info("here?");
        paper.setPdfUrl(CommonUtil.pdfUrl(bean.getParam()));
        paper.setUrl(CommonUtil.buildUrl(bean.getParam()));
        log.info(paper.getTitle());
        log.info(paper.getAbs());
        log.info(paper.getPdfUrl());
        log.info(paper.getUrl());
        return paper;
    }
}
