package com.juggle.juggle.api.v1.recharge;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.recharge.model.PhoneBill;
import com.juggle.juggle.primary.recharge.service.PhoneBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/recharge/phoneBill")
public class PhoneBillApi extends BaseApi<PhoneBill,PhoneBill,PhoneBill> {
    @Autowired
    private PhoneBillService service;

    @Override
    protected ICRUDService<PhoneBill> getService() {
        return service;
    }
}
