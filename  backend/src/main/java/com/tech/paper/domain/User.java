package com.tech.paper.domain;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String>{

    @Id
    @GeneratedValue
    private Long id;
    //登录用户的名称
    @Column(nullable = false,unique = true)
    private String account;
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String researchTheme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResearchTheme() {
        return researchTheme;
    }

    public void setResearchTheme(String researchTheme) {
        this.researchTheme = researchTheme;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", researchTheme='" + researchTheme + '\'' +
                '}';
    }
}
