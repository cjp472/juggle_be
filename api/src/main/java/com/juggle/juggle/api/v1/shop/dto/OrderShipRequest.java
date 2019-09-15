package com.juggle.juggle.api.v1.shop.dto;

import java.io.Serializable;

public class OrderShipRequest implements Serializable {
    private Long id;

    private String shippedCom;

    private String shippedNo;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getShippedCom(){
        return shippedCom;
    }

    public void setShippedCom(String shippedCom){
        this.shippedCom = shippedCom;
    }

    public String getShippedNo(){
        return shippedNo;
    }

    public void setShippedNo(String shippedNo){
        this.shippedNo = shippedNo;
    }
}
