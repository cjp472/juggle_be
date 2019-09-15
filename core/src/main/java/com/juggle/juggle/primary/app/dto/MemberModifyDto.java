package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;

public class MemberModifyDto implements Serializable {
    private String avatar;

    private String nickName;

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }
}
