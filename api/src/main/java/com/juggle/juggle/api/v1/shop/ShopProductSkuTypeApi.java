package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopProductSkuType;
import com.juggle.juggle.primary.shop.service.ShopProductSkuTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/shop/shopProductSkuType")
public class ShopProductSkuTypeApi extends BaseApi<ShopProductSkuType,ShopProductSkuType,ShopProductSkuType> {
    @Autowired
    private ShopProductSkuTypeService service;

    @Override
    protected ICRUDService<ShopProductSkuType> getService() {
        return service;
    }

    @RequestMapping(value = "/readAll",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object readAll(){
        List<SelectItem> items = service.readAllItem();
        return ApiResponse.make(items);
    }
}
