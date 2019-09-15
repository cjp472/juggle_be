package com.juggle.juggle.api.v1.advert;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.advert.model.AdvertType;
import com.juggle.juggle.primary.advert.service.AdvertTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/advert/advertType")
public class AdvertTypeApi extends BaseApi<AdvertType,AdvertType,AdvertType> {
    @Autowired
    private AdvertTypeService service;

    @Override
    protected ICRUDService<AdvertType> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllEnabled",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(){
        List<SelectItem> selectDtoList = service.readAllEnabled();
        return ApiResponse.make(selectDtoList);
    }
}
