package com.juggle.juggle.primary.recharge.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.model.Member;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "phone_bill")
@ExtView
public class PhoneBill extends LongEntity {
    @One(value = "member",target = Member.class)
    private Long memberId;

    @Filtered
    @State(Constants.PAY_WAY.class)
    private String payWay;

    @Filtered
    private String mobile;

    @NotNull
    private BigDecimal denomination;

    @NotNull
    private BigDecimal amount;

    @Filtered
    @State(Constants.PHONE_BILL_STATUS.class)
    private String status;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getDenomination() {
        return denomination;
    }

    public void setDenomination(BigDecimal denomination) {
        this.denomination = denomination;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
