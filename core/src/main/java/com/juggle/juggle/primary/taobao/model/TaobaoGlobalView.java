package com.juggle.juggle.primary.taobao.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "taobao_global_view")
public class TaobaoGlobalView extends LongEntity {
    private String sub;

    private Long itemId;

    @Filtered
    private String title;

    private String pictUrl;

    private Integer userType;

    @Filtered
    private BigDecimal couponAmount;

    private BigDecimal reservePrice;

    private BigDecimal zkFinalPrice;

    private Long volume;

    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    private String shopTitle;

    private Date updatedTime;

    private Date createdTime;

    public String getSub(){
        return sub;
    }

    public void setSub(String sub){
        this.sub = sub;
    }

    public Long getItemId(){
        return itemId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public BigDecimal getZkFinalPrice() {
        return zkFinalPrice;
    }

    public void setZkFinalPrice(BigDecimal zkFinalPrice) {
        this.zkFinalPrice = zkFinalPrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
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

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
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
