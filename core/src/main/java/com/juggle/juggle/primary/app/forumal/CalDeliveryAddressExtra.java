package com.juggle.juggle.primary.app.forumal;

import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.app.dto.DeliveryAddressExtra;
import com.juggle.juggle.primary.app.model.DeliveryAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalDeliveryAddressExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        DeliveryAddress entity = (DeliveryAddress) bean;
        DeliveryAddressExtra deliveryAddressExtra = new DeliveryAddressExtra();
        List<String> areas = new ArrayList<>();
        areas.add(entity.getProvince());
        areas.add(entity.getCity());
        areas.add(entity.getRegion());
        String area = String.join(" ",areas);
        deliveryAddressExtra.setArea(area);
        return deliveryAddressExtra;
    }
}
