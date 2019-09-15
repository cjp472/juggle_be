package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "shop_express")
public class ShopExpress extends LongEntity {
    private Long orderId;

    private String shippedNo;

    @State(Constants.SHOP_EXPRESS_SHIPPED_COM.class)
    private String shippedCom;

    private String snapshot;

    private Date updatedTime;

    private Date createdTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShippedNo() {
        return shippedNo;
    }

    public void setShippedNo(String shippedNo) {
        this.shippedNo = shippedNo;
    }

    public String getShippedCom() {
        return shippedCom;
    }

    public void setShippedCom(String shippedCom) {
        this.shippedCom = shippedCom;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
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
