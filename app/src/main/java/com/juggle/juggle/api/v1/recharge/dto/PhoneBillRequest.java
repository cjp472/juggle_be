package com.juggle.juggle.api.v1.recharge.dto;

import java.io.Serializable;

public class PhoneBillRequest implements Serializable {
    private String mobile;

    private Long skuId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
