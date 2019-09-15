package com.juggle.juggle.api.v1.auth.dto;

import java.io.Serializable;

public class ParentDto implements Serializable {
    private String inviteCode;

    public String getInviteCode(){
        return inviteCode;
    }

    public void setInviteCode(String inviteCode){
        this.inviteCode = inviteCode;
    }
}
