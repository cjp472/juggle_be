package com.juggle.juggle.primary.taobao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.taobao.forumal.CalTaobaoFlashSnapshot;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "taobao_flash")
@Formula(value="snapshot",calc = CalTaobaoFlashSnapshot.class)
@ExtView
public class TaobaoFlash extends LongEntity {
    private Long itemId;

    @Filtered
    private String title;

    private String pictUrl;

    private BigDecimal reservePrice;

    @Filtered
    private BigDecimal zkFinalPrice;

    private Integer totalAmount;

    private Integer soldNum;

    @Filtered
    private Date startTime;

    @Filtered
    private Date endTime;

    @Filtered
    private Long volume;

    private Integer userType;

    private String shopTitle;

    private String snapshot;

    @Filtered
    private BigDecimal couponAmount;

    @Filtered
    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    @Filtered
    @State(Constants.TAOBAO_FLASH_RUSH_TIME.class)
    private String rushTime;

    @Filtered
    private boolean top;

    private Date updatedTime;

    private Date createdTime;

    @Transient
    private String sub = Constants.TAOBAO_SUB.FLASH;

    public Long getItemId(){
        return itemId;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getPictUrl(){
        return pictUrl;
    }

    public void setPictUrl(String pictUrl){
        this.pictUrl = pictUrl;
    }

    public BigDecimal getReservePrice(){
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice){
        this.reservePrice = reservePrice;
    }

    public BigDecimal getZkFinalPrice(){
        return zkFinalPrice;
    }

    public void setZkFinalPrice(BigDecimal zkFinalPrice){
        this.zkFinalPrice = zkFinalPrice;
    }

    public Integer getTotalAmount(){
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount){
        this.totalAmount = totalAmount;
    }

    public Integer getSoldNum(){
        return soldNum;
    }

    public void setSoldNum(Integer soldNum){
        this.soldNum = soldNum;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Long getVolume(){
        return volume;
    }

    public void setVolume(Long volume){
        this.volume = volume;
    }

    public Integer getUserType(){
        return userType;
    }

    public void setUserType(Integer userType){
        this.userType = userType;
    }

    public String getShopTitle(){
        return shopTitle;
    }

    public void setShopTitle(String shopTitle){
        this.shopTitle = shopTitle;
    }

    @JsonIgnore
    public String getSnapshot(){
        return snapshot;
    }

    public void setSnapshot(String snapshot){
        this.snapshot = snapshot;
    }

    public BigDecimal getCouponAmount(){
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount){
        this.couponAmount = couponAmount;
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

    public String getRushTime(){
        return rushTime;
    }

    public void setRushTime(String rushTime){
        this.rushTime = rushTime;
    }

    public boolean isTop(){
        return top;
    }

    public void setTop(boolean top){
        this.top = top;
    }

    public Date getUpdatedTime(){
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
