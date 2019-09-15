package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.dto.ShopExpressDto;
import com.juggle.juggle.primary.shop.model.ShopExpress;
import com.juggle.juggle.primary.shop.service.ShopExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/shop/shopExpress")
public class ShopExpressApi extends BaseApi<ShopExpress,ShopExpress,ShopExpress> {
    @Autowired
    private ShopExpressService service;

    @Override
    protected ICRUDService<ShopExpress> getService() {
        return service;
    }

    @RequestMapping(value = "/getExpress/{orderId}",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getExpress(@PathVariable Long orderId){
        ShopExpressDto shopExpress = service.getExpress(orderId);
        return ApiResponse.make(shopExpress);
    }
}
