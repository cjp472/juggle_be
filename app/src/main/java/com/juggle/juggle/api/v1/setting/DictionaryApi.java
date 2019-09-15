package com.juggle.juggle.api.v1.setting;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.setting.dto.RebateRatios;
import com.juggle.juggle.primary.setting.model.Dictionary;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/setting/dictionary")
public class DictionaryApi extends BaseApi<Dictionary,Dictionary,Dictionary> {
    @Autowired
    private DictionaryService service;

    @Override
    protected ICRUDService<Dictionary> getService() {
        return service;
    }

    @RequestMapping(value = "/getMemberRatio",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getMemberRatio(){
        RebateRatios rebateRatios = service.getTaobaoRebateDictionaries();
        return ApiResponse.make(rebateRatios);
    }
}
