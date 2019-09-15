package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LastRewardCensus implements Serializable {
    private BigDecimal totalPaid = BigDecimal.ZERO;

    private BigDecimal estimateAmount = BigDecimal.ZERO;

    private BigDecimal estimatePerson = BigDecimal.ZERO;

    private BigDecimal estimateTeam = BigDecimal.ZERO;

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getEstimateAmount() {
        return estimateAmount;
    }

    public void setEstimateAmount(BigDecimal estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public BigDecimal getEstimatePerson() {
        return estimatePerson;
    }

    public void setEstimatePerson(BigDecimal estimatePerson) {
        this.estimatePerson = estimatePerson;
    }

    public BigDecimal getEstimateTeam() {
        return estimateTeam;
    }

    public void setEstimateTeam(BigDecimal estimateTeam) {
        this.estimateTeam = estimateTeam;
    }
}
