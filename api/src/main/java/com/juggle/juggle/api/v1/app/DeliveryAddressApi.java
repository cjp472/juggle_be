package com.juggle.juggle.api.v1.app;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.app.model.DeliveryAddress;
import com.juggle.juggle.primary.app.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/app/deliveryAddress")
public class DeliveryAddressApi extends BaseApi<DeliveryAddress,DeliveryAddress,DeliveryAddress> {
    @Autowired
    private DeliveryAddressService service;

    @Override
    protected ICRUDService<DeliveryAddress> getService() {
        return service;
    }
}
