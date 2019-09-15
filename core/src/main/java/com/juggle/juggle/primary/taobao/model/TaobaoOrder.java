package com.juggle.juggle.primary.taobao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "taobao_order")
public class TaobaoOrder extends LongEntity {
    @Filtered
    private Long relationId;

    private String tradeId;

    private String title;

    private String pictUrl;

    private String shopTitle;

    private String orderType;

    private Integer itemNum;

    private BigDecimal itemPrice;

    private BigDecimal alipayTotalPrice;

    private String snapshot;

    @Filtered
    private Integer tkStatus;

    private Date tkCreateTime;

    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    private boolean handle;

    private Date updatedTime;

    private Date createdTime;

    @JsonIgnore
    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getTradeId(){
        return tradeId;
    }

    public void setTradeId(String tradeId){
        this.tradeId = tradeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getOrderType(){
        return orderType;
    }

    public void setOrderType(String orderType){
        this.orderType = orderType;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getAlipayTotalPrice(){
        return alipayTotalPrice;
    }

    public void setAlipayTotalPrice(BigDecimal alipayTotalPrice){
        this.alipayTotalPrice = alipayTotalPrice;
    }

    @JsonIgnore
    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public Integer getTkStatus() {
        return tkStatus;
    }

    public void setTkStatus(Integer tkStatus) {
        this.tkStatus = tkStatus;
    }

    public Date getTkCreateTime() {
        return tkCreateTime;
    }

    public void setTkCreateTime(Date tkCreateTime) {
        this.tkCreateTime = tkCreateTime;
    }

    public BigDecimal getPrimaryReward(){
        return primaryReward;
    }

    public void setPrimaryReward(BigDecimal primaryReward){
        this.primaryReward = primaryReward;
    }

    public BigDecimal getSecondaryReward(){
        return secondaryReward;
    }

    public void setSecondaryReward(BigDecimal secondaryReward){
        this.secondaryReward = secondaryReward;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    @JsonIgnore
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @JsonIgnore
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
