package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.common.data.SimpleItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoCategory;
import com.juggle.juggle.primary.taobao.service.TaobaoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoCategory")
public class TaobaoCategoryApi extends BaseApi<TaobaoCategory,TaobaoCategory,TaobaoCategory> {
    @Autowired
    private TaobaoCategoryService service;

    @Override
    protected ICRUDService<TaobaoCategory> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllEnabled",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAllEnabled(){
        List<SimpleItem> simpleItems = service.readAllSimple();
        return ApiResponse.make(simpleItems);
    }
}
