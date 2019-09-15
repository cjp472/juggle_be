package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TaobaoDetaily implements Serializable {
    private Long id;

    private Long itemId;

    private String pictUrl;

    private List<String> smallImages;

    private String title;

    private BigDecimal reservePrice;

    private BigDecimal zkFinalPrice;

    private Integer soldNum;

    private Long volume;

    private Integer userType;

    private BigDecimal couponAmount;

    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    private String shopTitle;

    private String shopPictUrl;

    private String jumpUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public List<String> getSmallImages(){
        return smallImages;
    }

    public void setSmallImages(List<String> smallImages){
        this.smallImages = smallImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
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

    public String getShopPictUrl() {
        return shopPictUrl;
    }

    public void setShopPictUrl(String shopPictUrl) {
        this.shopPictUrl = shopPictUrl;
    }

    public String getJumpUrl(){
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl){
        this.jumpUrl = jumpUrl;
    }
}
