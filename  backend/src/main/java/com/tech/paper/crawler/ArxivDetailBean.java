package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import javax.persistence.Column;
import java.sql.Date;
import java.util.List;

@Gecco(matchUrl="https://arxiv.org/abs/{param}", pipelines="arxivDetailPipeline",downloader = "arxivHttpClientDownloader")
public class ArxivDetailBean implements HtmlBean {
    private static final long serialVersionUID = -7127412585200687225L;

    @Request
    private HttpRequest request;

    @RequestParameter("param")
    private String param;

    @HtmlField(cssPath=".title.mathjax")
    private String title;

    @HtmlField(cssPath="#abs > div.authors a")
    private List<String> authors;

    @HtmlField(cssPath="#abs > div.dateline")
    private String deadline;

    //abstract
    @HtmlField(cssPath="#abs > blockquote")
    private String abs;

    // the type of paper,such as cs,math
    @HtmlField(cssPath="#header > div.header-breadcrumbs > a:nth-child(2)")
    private String typeName;

    //Article identifier '{yearMonth}.{number}' not recognized
    //这个字段暂时用不上了
    @Deprecated
    @HtmlField(cssPath="#content > h1")
    private String notFindContent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNotFindContent() {
        return notFindContent;
    }

    public void setNotFindContent(String notFindContent) {
        this.notFindContent = notFindContent;
    }

    public ArxivDetailBean() {
    }

    public ArxivDetailBean(HttpRequest request, String param, String title, List<String> authors, String deadline, String abs, String typeName, String notFindContent) {
        this.request = request;
        this.param = param;
        this.title = title;
        this.authors = authors;
        this.deadline = deadline;
        this.abs = abs;
        this.typeName = typeName;
        this.notFindContent = notFindContent;
    }

    @Override
    public String toString() {
        return "ArxivDetailBean{" +
                "request=" + request +
                ", param='" + param + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", deadline='" + deadline + '\'' +
                ", abs='" + abs + '\'' +
                ", typeName='" + typeName + '\'' +
                ", notFindContent='" + notFindContent + '\'' +
                '}';
    }
}
