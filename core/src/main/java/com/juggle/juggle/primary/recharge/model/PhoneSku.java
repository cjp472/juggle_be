package com.juggle.juggle.primary.recharge.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "phone_sku")
public class PhoneSku extends AuditEntity {
    @NotNull
    private BigDecimal denomination;

    @NotNull
    private BigDecimal price;

    private Integer sort;

    @Filtered
    private boolean enabled;

    public BigDecimal getDenomination() {
        return denomination;
    }

    public void setDenomination(BigDecimal denomination) {
        this.denomination = denomination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
