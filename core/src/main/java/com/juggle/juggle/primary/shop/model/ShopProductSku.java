package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.One;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "shop_product_sku")
@ExtView
public class ShopProductSku extends AuditEntity {
    @One(value = "type",target = ShopProductSkuType.class)
    private Long typeId;

    @Filtered
    private Long productId;

    @NotBlank
    private String name;

    private boolean enabled;

    public Long getTypeId(){
        return typeId;
    }

    public void setTypeId(Long typeId){
        this.typeId = typeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
