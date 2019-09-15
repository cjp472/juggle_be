package com.juggle.juggle.primary.app.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.forumal.CalWithdrawAccount;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "withdraw")
@Formula(value="account",calc = CalWithdrawAccount.class)
@ExtView
public class Withdraw extends LongEntity {
    @Filtered
    private String code;

    @Filtered
    @One(value = "member",target = Member.class)
    private Long memberId;

    @Filtered
    @State(Constants.WITHDRAW_ACCOUNT_TYPE.class)
    private String accountType;

    private Long accountId;

    private BigDecimal amount;

    @Filtered
    @State(Constants.WITHDRAW_STATUS.class)
    private String status;

    private Date updatedTime;

    private Date createdTime;

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public String getAccountType(){
        return accountType;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    public Long getAccountId(){
        return accountId;
    }

    public void setAccountId(Long accountId){
        this.accountId = accountId;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
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
