package com.juggle.juggle.primary.auth.dto;

import com.juggle.juggle.primary.app.dto.MemberDto;

import java.io.Serializable;

public class MemberParent implements Serializable {
    private MemberDto member;

    private Long domainId;

    public MemberDto getMember(){
        return member;
    }

    public void setMember(MemberDto member){
        this.member = member;
    }

    public Long getDomainId(){
        return domainId;
    }

    public void setDomainId(Long domainId){
        this.domainId = domainId;
    }
}
