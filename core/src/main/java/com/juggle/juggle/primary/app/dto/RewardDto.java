package com.juggle.juggle.primary.app.dto;

import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.app.model.Member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ExtView
public class RewardDto implements Serializable {
    private Long id;

    private String type;

    private String orderType;

    @One(value = "teamer",target = Member.class)
    private Long teamerId;

    private String teamerLevel;

    private BigDecimal amount;

    private RewardSnapshot snapshot;

    private String status;

    private Date updatedTime;

    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getTeamerId() {
        return teamerId;
    }

    public void setTeamerId(Long teamerId) {
        this.teamerId = teamerId;
    }

    public String getTeamerLevel() {
        return teamerLevel;
    }

    public void setTeamerLevel(String teamerLevel) {
        this.teamerLevel = teamerLevel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public RewardSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(RewardSnapshot snapshot) {
        this.snapshot = snapshot;
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
