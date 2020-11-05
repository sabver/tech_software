package com.tech.paper.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserPaper implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "id")
    private Paper paper;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public UserPaper(Long id, User user, Paper paper) {
        this.id = id;
        this.user = user;
        this.paper = paper;
    }

    public UserPaper() {
    }
}
