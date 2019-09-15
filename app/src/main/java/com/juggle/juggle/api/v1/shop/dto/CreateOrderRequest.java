package com.juggle.juggle.api.v1.shop.dto;

import java.io.Serializable;
import java.util.List;

public class CreateOrderRequest implements Serializable {
    private Integer count;

    private Long productId;

    private List<Long> skuIds;

    private Long addressId;

    public Integer getCount(){
        return count;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public List<Long> getSkuIds(){
        return skuIds;
    }

    public void setSkuIds(List<Long> skuIds){
        this.skuIds = skuIds;
    }

    public Long getAddressId(){
        return addressId;
    }

    public void setAddressId(Long addressId){
        this.addressId = addressId;
    }
}
