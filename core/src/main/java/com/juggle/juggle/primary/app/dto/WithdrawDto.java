package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawDto implements Serializable {
    private Long id;

    private String code;

    private String accountType;

    private BigDecimal amount;

    private Object alipayAccount;

    private Object bankAccount;

    private String status;

    private Date updatedTime;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Object getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(Object alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public Object getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Object bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
