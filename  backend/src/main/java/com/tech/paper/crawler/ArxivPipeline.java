package com.tech.paper.crawler;
import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.SpiderBean;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Deprecated
public class ArxivPipeline implements Pipeline<ArxivBean>{
    private static Log log = LogFactory.getLog(ArxivPipeline.class);
    @Override
    public void process(ArxivBean arxivBean) {
        log.info(arxivBean.getTypes());
        List<String> types = arxivBean.getTypes();
        String[] result = null;
        String typeName = null;
        String href = null;
        for(String type:types){
            result =  type.split("https://arxiv.org/search/");
            typeName = result[1];
            log.info(typeName);
            /**
             * 获取了typeName(cs,q-bio...)之后，要做防止重复查询的操作，
             * 查询ArxivCrawlerLastData表，如果是空的，那就是新建然后设定已查数量为0，否则就从表里面的total
             * 字段里面推算出url的参数
             * https://arxiv.org/list/{type}/pastweek?skip=0&show=10000
             */

        }
    }
}
