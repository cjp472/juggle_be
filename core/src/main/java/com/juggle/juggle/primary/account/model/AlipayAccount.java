package com.juggle.juggle.primary.account.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "alipay_account")
public class AlipayAccount extends LongEntity {
    private Long memberId;

    @NotBlank
    private String name;

    @NotBlank
    private String account;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAccount(){
        return account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public Date getUpdatedTime(){
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}
