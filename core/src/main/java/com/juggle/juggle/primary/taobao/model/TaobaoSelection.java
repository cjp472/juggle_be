package com.juggle.juggle.primary.taobao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.taobao.forumal.CalTaobaoSelectionSnapshot;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "taobao_selection")
@Formula(value="snapshot",calc = CalTaobaoSelectionSnapshot.class)
@ExtView
public class TaobaoSelection extends LongEntity {
    @Filtered
    @State(Constants.TAOBAO_SELECTION_TYPE.class)
    private String type;

    private Long itemId;

    @Filtered
    private String title;

    private String pictUrl;

    private BigDecimal reservePrice;

    @Filtered
    private BigDecimal zkFinalPrice;

    private String shopTitle;

    private String snapshot;

    private Long volume;

    private Integer userType;

    @Filtered
    private BigDecimal couponAmount;

    @Filtered
    private BigDecimal primaryReward;

    private BigDecimal secondaryReward;

    private Date updatedTime;

    private Date createdTime;

    @Transient
    private String sub = Constants.TAOBAO_SUB.SELECTION;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

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

    public Date getUpdatedTime(){
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
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
