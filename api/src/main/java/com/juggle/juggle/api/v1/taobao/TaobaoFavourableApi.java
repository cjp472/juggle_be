package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoFavourable;
import com.juggle.juggle.primary.taobao.service.TaobaoFavourableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoFavourable")
public class TaobaoFavourableApi extends BaseApi<TaobaoFavourable,TaobaoFavourable,TaobaoFavourable> {
    @Autowired
    private TaobaoFavourableService service;

    @Override
    protected ICRUDService<TaobaoFavourable> getService() {
        return service;
    }
}
