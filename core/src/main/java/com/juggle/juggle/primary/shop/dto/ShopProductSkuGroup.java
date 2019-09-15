package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;
import java.util.List;

public class ShopProductSkuGroup implements Serializable {
    private Long id;

    private String name;

    private List<ShopProductSkuDto> skus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopProductSkuDto> getSkus() {
        return skus;
    }

    public void setSkus(List<ShopProductSkuDto> skus) {
        this.skus = skus;
    }
}
