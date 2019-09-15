package com.juggle.juggle.primary.shop.dto;

import com.juggle.juggle.primary.app.model.DeliveryAddress;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import com.juggle.juggle.primary.shop.model.ShopProductSku;

import java.io.Serializable;
import java.util.List;

public class ShopOrderSnapshot implements Serializable {
    private ShopProduct product;

    private List<ShopProductSku> skus;

    private DeliveryAddress address;

    public ShopProduct getProduct() {
        return product;
    }

    public void setProduct(ShopProduct product) {
        this.product = product;
    }

    public List<ShopProductSku> getSkus(){
        return skus;
    }

    public void setSkus(List<ShopProductSku> skus){
        this.skus = skus;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
}
