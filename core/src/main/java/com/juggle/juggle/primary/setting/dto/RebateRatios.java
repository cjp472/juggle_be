package com.juggle.juggle.primary.setting.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RebateRatios implements Serializable {
    private BigDecimal primaryRatio;

    private BigDecimal secondaryRatio;

    public BigDecimal getPrimaryRatio() {
        return primaryRatio;
    }

    public void setPrimaryRatio(BigDecimal primaryRatio) {
        this.primaryRatio = primaryRatio;
    }

    public BigDecimal getSecondaryRatio() {
        return secondaryRatio;
    }

    public void setSecondaryRatio(BigDecimal secondaryRatio) {
        this.secondaryRatio = secondaryRatio;
    }
}
