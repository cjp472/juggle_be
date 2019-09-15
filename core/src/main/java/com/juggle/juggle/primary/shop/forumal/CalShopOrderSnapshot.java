package com.juggle.juggle.primary.shop.forumal;

import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.shop.dto.ShopOrderSnapshot;
import com.juggle.juggle.primary.shop.model.ShopOrder;

import java.util.Collection;

public class CalShopOrderSnapshot implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        ShopOrder entity = (ShopOrder) bean;
        ShopOrderSnapshot snapshot = JsonUtils.readValue(entity.getSnapshot(),ShopOrderSnapshot.class);
        return snapshot;
    }
}
