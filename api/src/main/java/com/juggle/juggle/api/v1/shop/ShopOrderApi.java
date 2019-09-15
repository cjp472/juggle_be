package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.api.v1.shop.dto.OrderShipRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/shop/shopOrder")
public class ShopOrderApi extends BaseApi<ShopOrder,ShopOrder,ShopOrder> {
    @Autowired
    private ShopOrderService service;

    @Override
    protected ICRUDService<ShopOrder> getService() {
        return service;
    }

    @RequestMapping(value = "/ship",method = {RequestMethod.POST})
    public @ResponseBody Object ship(@RequestBody OrderShipRequest req){
        ShopOrder shopOrder = service.shipOrder(req.getId(),req.getShippedCom(),req.getShippedNo());
        return ApiResponse.make(shopOrder);
    }
}
