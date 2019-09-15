package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;

public class ShopProductSkuDto implements Serializable {
    private Long id;

    private String name;

    private String typeName;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
