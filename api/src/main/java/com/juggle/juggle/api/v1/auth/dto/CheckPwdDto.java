package com.juggle.juggle.api.v1.auth.dto;

import java.io.Serializable;

public class CheckPwdDto implements Serializable {
    private String password;

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
