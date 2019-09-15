package com.juggle.juggle.primary.app.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reward")
@ExtView
public class Reward extends LongEntity {
    @Filtered
    @State(Constants.REWARD_TYPE.class)
    private String type;

    @State(Constants.REWARD_ORDER_TYPE.class)
    private String orderType;

    private Long orderId;

    @Filtered
    @One(value = "member",target = Member.class)
    private Long memberId;

    @Filtered
    @One(value = "teamer",target = Member.class)
    private Long teamerId;

    @Filtered
    @State(Constants.REWARD_TEAMER_LEVEL.class)
    private String teamerLevel;

    private BigDecimal amount;

    @Filtered
    @State(Constants.REWARD_STATUS.class)
    private String status;

    private Date updatedTime;

    @Filtered
    private Date createdTime;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getTeamerId(){
        return teamerId;
    }

    public void setTeamerId(Long teamerId){
        this.teamerId = teamerId;
    }

    public String getTeamerLevel(){
        return teamerLevel;
    }

    public void setTeamerLevel(String teamerLevel){
        this.teamerLevel = teamerLevel;
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
