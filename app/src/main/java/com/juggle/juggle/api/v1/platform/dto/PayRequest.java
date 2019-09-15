package com.juggle.juggle.api.v1.platform.dto;

import java.io.Serializable;

public class PayRequest implements Serializable {
    private Long orderId;

    public Long getOrderId(){
        return orderId;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }
}
