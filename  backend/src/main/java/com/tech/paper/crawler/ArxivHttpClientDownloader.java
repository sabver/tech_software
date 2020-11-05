package com.tech.paper.crawler;

import com.geccocrawler.gecco.downloader.DownloadException;
import com.geccocrawler.gecco.downloader.HttpClientDownloader;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.tech.paper.domain.ArxivId;
import com.tech.paper.util.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@com.geccocrawler.gecco.annotation.Downloader("arxivHttpClientDownloader")
public class ArxivHttpClientDownloader extends HttpClientDownloader {

    private static Log log = LogFactory.getLog(ArxivHttpClientDownloader.class);

    public HttpResponse download(HttpRequest request, int timeout){
        try {
            HttpResponse response = super.download(request,timeout);
            return response;
        }catch(DownloadException e){
//            log.info(request.getUrl());
            //https://arxiv.org/abs/1911.00001
            /**
             * 这里报错了证明是当前url不存在，有三种情况，单纯的网络错误现在没法处理，现在只处理要跨月跨年的情况
             */
            ArxivId next = CommonUtil.getNextArxivIdYearMonth(request.getUrl());
            String nextUrl = CommonUtil.buildUrl(next);
            SchedulerContext.into(request.subRequest(nextUrl));
            log.info(e.toString());
            ArxivDetailPipeline.isHave404 = true;
        }
        return null;
    }

}
