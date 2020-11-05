package com.tech.paper.crawler;

import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Deprecated
public class ArxivListHrefBean implements HtmlBean {

    private static final long serialVersionUID = 3018760488621382659L;

    @Href(click=false)
    @HtmlField(cssPath="a[title=Abstract]")
    private String url;

    public ArxivListHrefBean(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArxivListHrefBean() {
    }

    @Override
    public String toString() {
        return "ArxivListHrefBean{" +
                "url='" + url + '\'' +
                '}';
    }
}
