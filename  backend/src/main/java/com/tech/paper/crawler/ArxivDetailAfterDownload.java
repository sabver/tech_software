package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.GeccoClass;
import com.geccocrawler.gecco.downloader.AfterDownload;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GeccoClass(ArxivDetailBean.class)
public class ArxivDetailAfterDownload implements AfterDownload {

    private static final Logger log = LoggerFactory.getLogger(ArxivDetailAfterDownload.class);

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        log.info("ArxivDetailAfterDownLoad");
        log.info(String.valueOf(httpResponse.getStatus()));
    }
}
