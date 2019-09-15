package com.juggle.juggle.primary.shop.dto;

import com.juggle.juggle.primary.app.dto.DeliveryAddressDto;

import java.io.Serializable;
import java.util.List;

public class ShopOrderSnapshotDto implements Serializable {
    private ShopProductDto product;

    private List<ShopProductSkuDto> skus;

    private DeliveryAddressDto address;

    public ShopProductDto getProduct() {
        return product;
    }

    public void setProduct(ShopProductDto product) {
        this.product = product;
    }

    public List<ShopProductSkuDto> getSkus() {
        return skus;
    }

    public void setSkus(List<ShopProductSkuDto> skus) {
        this.skus = skus;
    }

    public DeliveryAddressDto getAddress() {
        return address;
    }

    public void setAddress(DeliveryAddressDto address) {
        this.address = address;
    }
}
