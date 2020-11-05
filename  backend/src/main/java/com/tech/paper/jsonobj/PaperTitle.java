package com.tech.paper.jsonobj;

import java.io.Serializable;

public class PaperTitle implements Serializable {
    private String title;

    public PaperTitle() {
    }

    public PaperTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
