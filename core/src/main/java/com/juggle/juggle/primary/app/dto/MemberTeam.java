package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberTeam implements Serializable {
    private Long id;

    private String avatar;

    private String nickName;

    private String grade;

    private String mobile;

    private Date createdTime;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

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

    public String getGrade(){
        return grade;
    }

    public void setGrade(String grade){
        this.grade = grade;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}
