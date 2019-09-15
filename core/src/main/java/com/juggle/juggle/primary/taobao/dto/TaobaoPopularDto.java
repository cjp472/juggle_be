package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaobaoPopularDto implements Serializable {
    private Long id;

    private String title;

    private String pictUrl;

    private BigDecimal reservePrice;

    private BigDecimal zkFinalPrice;

    private BigDecimal couponAmount;

    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    private String shopTitle;

    private String sub;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public BigDecimal getZkFinalPrice(){
        return zkFinalPrice;
    }

    public void setZkFinalPrice(BigDecimal zkFinalPrice){
        this.zkFinalPrice = zkFinalPrice;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getPrimaryReward() {
        return primaryReward;
    }

    public void setPrimaryReward(BigDecimal primaryReward) {
        this.primaryReward = primaryReward;
    }

    public BigDecimal getSecondaryReward() {
        return secondaryReward;
    }

    public void setSecondaryReward(BigDecimal secondaryReward) {
        this.secondaryReward = secondaryReward;
    }

    public String getShopTitle(){
        return shopTitle;
    }

    public void setShopTitle(String shopTitle){
        this.shopTitle = shopTitle;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
