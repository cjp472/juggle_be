package com.juggle.juggle.api.v1.advert;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.advert.model.Advert;
import com.juggle.juggle.primary.advert.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/advert/advert")
public class AdvertApi extends BaseApi<Advert,Advert,Advert> {
    @Autowired
    private AdvertService service;

    @Override
    protected ICRUDService<Advert> getService() {
        return service;
    }
}
