package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopProductComment;
import com.juggle.juggle.primary.shop.service.ShopProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/shop/shopProductComment")
public class ShopProductCommentApi extends BaseApi<ShopProductComment,ShopProductComment,ShopProductComment> {
    @Autowired
    private ShopProductCommentService service;

    @Override
    protected ICRUDService<ShopProductComment> getService() {
        return service;
    }
}
