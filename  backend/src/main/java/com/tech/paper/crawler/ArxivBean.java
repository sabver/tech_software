package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

@Deprecated
@Gecco(matchUrl="https://arxiv.org", pipelines="arxivPipeline")
public class ArxivBean implements HtmlBean {

    private static final long serialVersionUID = -7127412585200687226L;

    @Request
    private HttpRequest request;

    @HtmlField(cssPath="#content > form > select option")
    @Attr("data-url")
    private List<String> types;

    public ArxivBean(HttpRequest request, List<String> types) {
        this.request = request;
        this.types = types;
    }

    public ArxivBean() {
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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
