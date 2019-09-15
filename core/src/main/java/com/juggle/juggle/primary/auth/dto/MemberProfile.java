package com.juggle.juggle.primary.auth.dto;

import com.juggle.juggle.primary.app.dto.MemberDto;

import java.io.Serializable;

public class MemberProfile implements Serializable {
    private MemberDto member;

    private String token;

    public MemberDto getMember() {
        return member;
    }

    public void setMember(MemberDto member) {
        this.member = member;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
