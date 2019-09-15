package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopExpressDto implements Serializable {
    private Long orderId;

    private String shippedNo;

    private String shippedCom;

    private Object snapshot;

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

    public Object getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Object snapshot) {
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
