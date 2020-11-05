package com.tech.paper.crawler;
import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.tech.paper.domain.ArxivId;
import com.tech.paper.domain.Paper;
import com.tech.paper.service.InitService;
import com.tech.paper.service.impl.InitServiceImpl;
import com.tech.paper.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ArxivDetailPipeline implements Pipeline<ArxivDetailBean>{

    //标记上次的请求是否发生了404
    public static boolean isHave404 = false;

    private static final Logger log = LoggerFactory.getLogger(ArxivDetailPipeline.class);

    @Autowired
    private InitService initService;

    /**
     * 关于访问地址不正确的问题，这里如果是访问地址不正确的话，arxiv那边是直接返回404的，虽然浏览器上面是可以看到页面的
     * 所以，一旦是进入到这个方法里面的，一律都是已经正确解析地址了的
     * 如果是访问错误的，会直接进入ArxivHttpClientDownloader里面被catch到，到那时再临时修改执行地址
     * 这里发现一个问题，就是gecco执行完新的url之后，因为外面是设置了loop的，所以这里它又会重新执行会以前的url，
     * 所以这里执行的时候一定要指定下一个url
     * @param arxivDetailBean
     */
    @Override
    public void process(ArxivDetailBean arxivDetailBean) {
        log.info("ArxivDetailPipeline process");
        log.info(arxivDetailBean.toString());
        Paper paper = initService.changeArxivToPaper(arxivDetailBean);
        log.info(paper.toString());
        //保存了paper
        initService.savePaper(paper);
        //保存到搜索引擎里面
        try {
            initService.saveLucene(paper);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更新ArxivId
        ArxivId curId = initService.queryArxivId();
        log.info("curId:"+curId.toString());
        //这里有bug，因为这里不知道上次是不是因为404才执行的请求，所以这里的逻辑上没有办法转移到下个ID，所以需要追加table做记录，或者简单点用一个静态变量保存
        ArxivId result = CommonUtil.getNextArxivId(curId);
        if( isHave404 ){
            result = CommonUtil.getNextArxivIdYearMonth(curId);
            isHave404 = false;
        }
        log.info("nextId:"+result.toString());
        result.setId(curId.getId());
        initService.saveArxivId(result);
        //更新当前请求url
        SchedulerContext.into(arxivDetailBean.getRequest().subRequest(CommonUtil.buildUrl(result)));
    }

}
