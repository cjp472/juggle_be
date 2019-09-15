package com.juggle.juggle.primary.approval.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawApply implements Serializable {
    private String accountType;

    private Long accountId;

    private BigDecimal amount;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
