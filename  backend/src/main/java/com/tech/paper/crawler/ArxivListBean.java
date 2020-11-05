package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Deprecated
@Gecco(matchUrl="https://arxiv.org/list/{type}/pastweek?skip=0&show=10000", pipelines="arxivListPipeline")
public class ArxivListBean implements HtmlBean{

    private static final long serialVersionUID = -7127412585200687225L;

    @Request
    private HttpRequest request;

    @RequestParameter("type")
    private String type;

    @HtmlField(cssPath=".list-identifier")
    private List<ArxivListHrefBean> listHref;

    public ArxivListBean(HttpRequest request, String type, List<ArxivListHrefBean> listHref) {
        this.request = request;
        this.type = type;
        this.listHref = listHref;
    }

    public ArxivListBean() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ArxivListHrefBean> getListHref() {
        return listHref;
    }

    public void setListHref(List<ArxivListHrefBean> listHref) {
        this.listHref = listHref;
    }

    @Override
    public String toString() {
        return "ArxivListBean{" +
                "request=" + request +
                ", type='" + type + '\'' +
                ", listHref=" + listHref +
                '}';
    }
}
