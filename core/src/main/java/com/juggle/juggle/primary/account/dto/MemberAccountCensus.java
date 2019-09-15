package com.juggle.juggle.primary.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberAccountCensus implements Serializable {
    private BigDecimal totalReward = BigDecimal.ZERO;

    private BigDecimal totalWithdraw = BigDecimal.ZERO;

    private BigDecimal totalPend = BigDecimal.ZERO;

    private BigDecimal amount = BigDecimal.ZERO;

    public BigDecimal getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(BigDecimal totalReward) {
        this.totalReward = totalReward;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }

    public BigDecimal getTotalPend() {
        return totalPend;
    }

    public void setTotalPend(BigDecimal totalPend) {
        this.totalPend = totalPend;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
