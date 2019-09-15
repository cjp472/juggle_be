package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.dto.TaobaoSummary;
import com.juggle.juggle.primary.taobao.model.TaobaoProduct;
import com.juggle.juggle.primary.taobao.service.TaobaoProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoProduct")
public class TaobaoProductApi extends BaseApi<TaobaoProduct,TaobaoProduct,TaobaoProduct> {
    @Autowired
    private TaobaoProductService service;

    @Override
    protected ICRUDService<TaobaoProduct> getService() {
        return service;
    }

    @RequestMapping(value = "/summary",method = {RequestMethod.POST})
    public @ResponseBody Object summary(){
        TaobaoSummary taobaoSummary = service.getSummary();
        return ApiResponse.make(taobaoSummary);
    }
}
