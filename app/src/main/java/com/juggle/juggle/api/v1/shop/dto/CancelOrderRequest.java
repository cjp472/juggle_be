package com.juggle.juggle.api.v1.shop.dto;

import java.io.Serializable;

public class CancelOrderRequest implements Serializable {
    private Long id;

    private String remark;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
}
