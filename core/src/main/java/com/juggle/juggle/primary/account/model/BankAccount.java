package com.juggle.juggle.primary.account.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "bank_account")
public class BankAccount extends LongEntity {
    @Filtered
    private Long memberId;

    @NotBlank
    private String name;

    @NotBlank
    private String bank;

    @NotBlank
    private String cardNo;

    @NotBlank
    private String cardId;

    @NotBlank
    private String mobile;

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

    public String getBank(){
        return bank;
    }

    public void setBank(String bank){
        this.bank = bank;
    }

    public String getCardNo(){
        return cardNo;
    }

    public void setCardNo(String cardNo){
        this.cardNo = cardNo;
    }

    public String getCardId(){
        return cardId;
    }

    public void setCardId(String cardId){
        this.cardId = cardId;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
