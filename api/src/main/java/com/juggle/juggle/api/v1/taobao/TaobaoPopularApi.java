package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoPopular;
import com.juggle.juggle.primary.taobao.service.TaobaoPopularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoPopular")
public class TaobaoPopularApi extends BaseApi<TaobaoPopular,TaobaoPopular,TaobaoPopular> {
    @Autowired
    private TaobaoPopularService service;

    @Override
    protected ICRUDService<TaobaoPopular> getService() {
        return service;
    }
}
