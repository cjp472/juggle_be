package com.juggle.juggle.api.v1.auth.dto;

import java.io.Serializable;

public class RegisterDto implements Serializable {
    private String inviteCode;

    private String mobile;

    private String password;

    private Long domainId;

    public String getInviteCode(){
        return inviteCode;
    }

    public void setInviteCode(String inviteCode){
        this.inviteCode = inviteCode;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Long getDomainId(){
        return domainId;
    }

    public void setDomainId(Long domainId){
        this.domainId = domainId;
    }
}
