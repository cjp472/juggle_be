package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoSelection;
import com.juggle.juggle.primary.taobao.service.TaobaoSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoSelection")
public class TaobaoSelectionApi extends BaseApi<TaobaoSelection,TaobaoSelection,TaobaoSelection> {
    @Autowired
    private TaobaoSelectionService service;

    @Override
    protected ICRUDService<TaobaoSelection> getService() {
        return service;
    }
}
