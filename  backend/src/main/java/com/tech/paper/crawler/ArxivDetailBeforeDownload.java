package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.GeccoClass;
import com.geccocrawler.gecco.downloader.BeforeDownload;
import com.geccocrawler.gecco.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GeccoClass(ArxivDetailBean.class)
public class ArxivDetailBeforeDownload implements BeforeDownload {

    private static final Logger log = LoggerFactory.getLogger(ArxivDetailBeforeDownload.class);

    @Override
    public void process(HttpRequest httpRequest) {
        log.info("ArxivDetailBeforeDownload");

    }
}
