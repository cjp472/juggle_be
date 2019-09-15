package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PresentRewardCensus implements Serializable {
    private BigDecimal estimatePerson = BigDecimal.ZERO;

    private Integer validPersonOrder = 0;

    private BigDecimal validPersonOrderAmount = BigDecimal.ZERO;

    private BigDecimal estimateTeam = BigDecimal.ZERO;

    private Integer validTeamOrder = 0;

    private BigDecimal validTeamOrderAmount = BigDecimal.ZERO;

    public BigDecimal getEstimatePerson() {
        return estimatePerson;
    }

    public void setEstimatePerson(BigDecimal estimatePerson) {
        this.estimatePerson = estimatePerson;
    }

    public Integer getValidPersonOrder() {
        return validPersonOrder;
    }

    public void setValidPersonOrder(Integer validPersonOrder) {
        this.validPersonOrder = validPersonOrder;
    }

    public BigDecimal getValidPersonOrderAmount() {
        return validPersonOrderAmount;
    }

    public void setValidPersonOrderAmount(BigDecimal validPersonOrderAmount) {
        this.validPersonOrderAmount = validPersonOrderAmount;
    }

    public BigDecimal getEstimateTeam() {
        return estimateTeam;
    }

    public void setEstimateTeam(BigDecimal estimateTeam) {
        this.estimateTeam = estimateTeam;
    }

    public Integer getValidTeamOrder() {
        return validTeamOrder;
    }

    public void setValidTeamOrder(Integer validTeamOrder) {
        this.validTeamOrder = validTeamOrder;
    }

    public BigDecimal getValidTeamOrderAmount() {
        return validTeamOrderAmount;
    }

    public void setValidTeamOrderAmount(BigDecimal validTeamOrderAmount) {
        this.validTeamOrderAmount = validTeamOrderAmount;
    }
}
