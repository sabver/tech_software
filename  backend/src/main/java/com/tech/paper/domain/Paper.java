package com.tech.paper.domain;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Paper extends Auditable<String>{

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    private String authors;
    private String deadline;
    //abstract
    @Column(nullable = false)
    private String abs;
    // resource url
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String pdfUrl;
    // the type of paper,such as cs,math
    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Paper() {
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", deadline='" + deadline + '\'' +
                ", abs='" + abs + '\'' +
                ", url='" + url + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
