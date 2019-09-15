package com.juggle.juggle.primary.account.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "member_account")
public class MemberAccount extends LongEntity {
    private Long memberId;

    private BigDecimal amount;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
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
