package com.tech.paper.util;

import com.tech.paper.domain.User;

public class LoginMsg {
    private String token;
    private User user;

    public LoginMsg() {
    }

    public LoginMsg(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
