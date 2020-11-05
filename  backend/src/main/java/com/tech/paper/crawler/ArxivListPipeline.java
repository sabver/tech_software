package com.tech.paper.crawler;
import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.SpiderBean;

import java.util.List;

@Deprecated
public class ArxivListPipeline implements Pipeline<ArxivListBean>{
    @Override
    public void process(ArxivListBean spiderBean) {
        List<ArxivListHrefBean> hrefs = spiderBean.getListHref();
        System.out.println(hrefs);
        for(ArxivListHrefBean href : hrefs) {
            HttpRequest currRequest = spiderBean.getRequest();
            SchedulerContext.into(currRequest.subRequest(href.getUrl()));
        }
    }
}
