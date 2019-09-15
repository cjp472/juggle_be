package com.juggle.juggle.primary.auth.dto;

import com.juggle.juggle.primary.system.model.User;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private User user;

    private String token;

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
