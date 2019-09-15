package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopCoupon;
import com.juggle.juggle.primary.shop.service.ShopCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/shop/shopCoupon")
public class ShopCouponApi extends BaseApi<ShopCoupon,ShopCoupon,ShopCoupon> {
    @Autowired
    private ShopCouponService service;

    @Override
    protected ICRUDService<ShopCoupon> getService() {
        return service;
    }

    @RequestMapping(value = "/{id}/normal",method = {RequestMethod.POST})
    public @ResponseBody Object normal(@PathVariable Long id){
        service.normal(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/forbid",method = {RequestMethod.POST})
    public @ResponseBody Object forbid(@PathVariable Long id){
        service.forbid(id);
        return ApiResponse.make();
    }
}
