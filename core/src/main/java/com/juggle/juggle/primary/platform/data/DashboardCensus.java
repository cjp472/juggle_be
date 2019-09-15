package com.juggle.juggle.primary.platform.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class DashboardCensus implements Serializable {
    private Long memberNum;

    private BigDecimal shopOrderAmount;

    private Long shopOrderNum;

    private Long taobaoProductNum;

    public Long getMemberNum(){
        return memberNum;
    }

    public void setMemberNum(Long memberNum){
        this.memberNum = memberNum;
    }

    public BigDecimal getShopOrderAmount(){
        return shopOrderAmount;
    }

    public void setShopOrderAmount(BigDecimal shopOrderAmount){
        this.shopOrderAmount = shopOrderAmount;
    }

    public Long getShopOrderNum(){
        return shopOrderNum;
    }

    public void setShopOrderNum(Long shopOrderNum){
        this.shopOrderNum = shopOrderNum;
    }

    public Long getTaobaoProductNum(){
        return taobaoProductNum;
    }

    public void setTaobaoProductNum(Long taobaoProductNum){
        this.taobaoProductNum = taobaoProductNum;
    }
}
