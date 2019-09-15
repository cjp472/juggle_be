package com.juggle.juggle.api.v1.recharge;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.recharge.model.PhoneSku;
import com.juggle.juggle.primary.recharge.service.PhoneSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/recharge/phoneSku")
public class PhoneSkuApi extends BaseApi<PhoneSku,PhoneSku,PhoneSku> {
    @Autowired
    private PhoneSkuService service;

    @Override
    protected ICRUDService<PhoneSku> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllEnabled",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(){
        List<PhoneSku> skus = service.readAllEnabled();
        return ApiResponse.make(skus);
    }
}
