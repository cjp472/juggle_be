package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.common.data.LongRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.dto.ShopProductDetail;
import com.juggle.juggle.primary.shop.dto.ShopProductRecommend;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import com.juggle.juggle.primary.shop.service.ShopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/shop/shopProduct")
public class ShopProductApi extends BaseApi<ShopProduct,ShopProduct,ShopProduct> {
    @Autowired
    private ShopProductService service;

    @Override
    protected ICRUDService<ShopProduct> getService() {
        return service;
    }

    @RequestMapping(value = "/readAllRecommend",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object realAllRecommend() throws Exception{
        List<ShopProductRecommend> shopProductRecommends = service.readAllRecommend();
        return ApiResponse.make(shopProductRecommends);
    }

    @RequestMapping(value = "/readDetail",method = {RequestMethod.POST})
    public @ResponseBody Object readDetail(@RequestBody LongRequest req){
        ShopProductDetail detail = service.readDetail(req.getId());
        return ApiResponse.make(detail);
    }
}
