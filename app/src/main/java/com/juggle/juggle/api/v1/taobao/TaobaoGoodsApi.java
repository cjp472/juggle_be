package com.juggle.juggle.api.v1.taobao;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageResult;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.taobao.model.TaobaoGoods;
import com.juggle.juggle.primary.taobao.service.TaobaoGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/taobao/taobaoGoods")
public class TaobaoGoodsApi extends BaseApi<TaobaoGoods,TaobaoGoods,TaobaoGoods> {
    @Autowired
    private TaobaoGoodsService service;

    @Override
    protected ICRUDService<TaobaoGoods> getService() {
        return service;
    }

    @RequestMapping(value = "/search",method = {RequestMethod.POST})
    public @ResponseBody
    Object search(@RequestBody @Valid PageSearch pageSearch){
        PageResult pageResult = service.searchSimplify(pageSearch);
        return ApiResponse.make(pageResult);
    }
}