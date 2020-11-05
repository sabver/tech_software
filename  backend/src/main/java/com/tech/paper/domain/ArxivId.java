package com.tech.paper.domain;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class ArxivId implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    //year and month
    @Column(nullable = false)
    private String ym;

    @Column(nullable = false)
    private String numberStr;

    public ArxivId() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(String numberStr) {
        this.numberStr = numberStr;
    }

    @Override
    public String toString() {
        return "ArxivId{" +
                "id=" + id +
                ", ym='" + ym + '\'' +
                ", numberStr='" + numberStr + '\'' +
                '}';
    }
}
