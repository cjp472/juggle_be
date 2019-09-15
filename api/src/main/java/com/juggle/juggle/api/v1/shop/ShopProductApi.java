package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import com.juggle.juggle.primary.shop.service.ShopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/shop/shopProduct")
public class ShopProductApi extends BaseApi<ShopProduct,ShopProduct,ShopProduct> {
    @Autowired
    private ShopProductService service;

    @Override
    protected ICRUDService<ShopProduct> getService() {
        return service;
    }

    @RequestMapping(value="/{id}/enable",method = {RequestMethod.POST})
    public @ResponseBody Object enable(@PathVariable Long id){
        service.enable(id);
        return ApiResponse.make();
    }

    @RequestMapping(value = "/{id}/disable",method = {RequestMethod.POST})
    public @ResponseBody Object disable(@PathVariable Long id){
        service.disable(id);
        return ApiResponse.make();
    }
}
