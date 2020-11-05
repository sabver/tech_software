package com.tech.paper.crawler;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

public class ConsolePipeline implements Pipeline<SpiderBean> {

    @Override
    public void process(SpiderBean bean) {
        System.out.println(JSON.toJSONString(bean));
    }

}