package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoGoods;
import com.juggle.juggle.primary.taobao.service.TaobaoGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoGoods")
public class TaobaoGoodsApi extends BaseApi<TaobaoGoods,TaobaoGoods,TaobaoGoods> {
    @Autowired
    private TaobaoGoodsService service;

    @Override
    protected ICRUDService<TaobaoGoods> getService() {
        return service;
    }
}
