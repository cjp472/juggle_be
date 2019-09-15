package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RewardCensus implements Serializable {
    private BigDecimal estimateSpread = BigDecimal.ZERO;

    private BigDecimal estimateSale = BigDecimal.ZERO;

    private BigDecimal estimateGuide = BigDecimal.ZERO;

    private BigDecimal lastBalance = BigDecimal.ZERO;

    private BigDecimal lastEstimate = BigDecimal.ZERO;

    public BigDecimal getEstimateSpread() {
        return estimateSpread;
    }

    public void setEstimateSpread(BigDecimal estimateSpread) {
        this.estimateSpread = estimateSpread;
    }

    public BigDecimal getEstimateSale() {
        return estimateSale;
    }

    public void setEstimateSale(BigDecimal estimateSale) {
        this.estimateSale = estimateSale;
    }

    public BigDecimal getEstimateGuide() {
        return estimateGuide;
    }

    public void setEstimateGuide(BigDecimal estimateGuide) {
        this.estimateGuide = estimateGuide;
    }

    public BigDecimal getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(BigDecimal lastBalance) {
        this.lastBalance = lastBalance;
    }

    public BigDecimal getLastEstimate() {
        return lastEstimate;
    }

    public void setLastEstimate(BigDecimal lastEstimate) {
        this.lastEstimate = lastEstimate;
    }
}
