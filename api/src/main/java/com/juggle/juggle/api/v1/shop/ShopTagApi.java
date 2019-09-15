package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopTag;
import com.juggle.juggle.primary.shop.service.ShopTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/shop/shopTag")
public class ShopTagApi extends BaseApi<ShopTag,ShopTag,ShopTag> {
    @Autowired
    private ShopTagService service;

    @Override
    protected ICRUDService<ShopTag> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllEnabled",method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    Object readAllEnabled(){
        List<SelectItem> selectDtoList = service.readAllEnabled();
        return ApiResponse.make(selectDtoList);
    }
}
