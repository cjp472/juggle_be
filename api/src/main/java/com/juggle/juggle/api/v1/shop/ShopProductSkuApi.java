package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopProductSku;
import com.juggle.juggle.primary.shop.service.ShopProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/shop/shopProductSku")
public class ShopProductSkuApi extends BaseApi<ShopProductSku,ShopProductSku,ShopProductSku> {
    @Autowired
    private ShopProductSkuService service;

    @Override
    protected ICRUDService<ShopProductSku> getService() {
        return service;
    }
}
