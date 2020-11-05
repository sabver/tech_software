package com.tech.paper.service.impl;

import com.tech.paper.domain.ArxivId;
import com.tech.paper.domain.ArxivIdRepository;
import com.tech.paper.domain.PaperRepository;
import com.tech.paper.service.ResetService;
import org.apache.lucene.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ResetServiceImpl implements ResetService {
    private static final Logger log = LoggerFactory.getLogger(ResetServiceImpl.class);

    @Resource
    private PaperRepository paperRepository;

    @Resource
    private ArxivIdRepository arxivIdRepository;

    @Autowired
    private Environment env;

    @Override
    public void reset() {
        log.info("reset");
        try {
            Path indexPath = Paths.get(env.getProperty("lucene.filepath"));
            IOUtils.rm(indexPath);
            paperRepository.deleteAll();
            ArxivId result = new ArxivId();
            List<ArxivId> list = arxivIdRepository.findAll();
            if( list.size() != 0 ){
                result = list.get(0);
            }
            result.setYm("1901");
            result.setNumberStr("00001");
            arxivIdRepository.save(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
