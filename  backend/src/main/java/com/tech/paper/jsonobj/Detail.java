package com.tech.paper.jsonobj;

import com.tech.paper.domain.Paper;

public class Detail {
    private Paper paper;
    //boolean 该用户已收藏本论文
    private boolean isCollect;

    public Detail(Paper paper, boolean isCollect) {
        this.paper = paper;
        this.isCollect = isCollect;
    }

    public Detail() {
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }
}
