package com.juggle.juggle.api.v1.auth.dto;

import java.io.Serializable;

public class UpdatePwdDto implements Serializable {
    private String oldPassword;

    private String newPassword;

    public String getOldPassword(){
        return oldPassword;
    }

    public void setOldPassword(String oldPassword){
        this.oldPassword = oldPassword;
    }

    public String getNewPassword(){
        return newPassword;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
}
