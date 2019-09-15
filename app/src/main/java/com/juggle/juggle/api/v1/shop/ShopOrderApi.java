package com.juggle.juggle.api.v1.shop;

import com.juggle.juggle.api.v1.shop.dto.CancelOrderRequest;
import com.juggle.juggle.api.v1.shop.dto.CommentOrderRequest;
import com.juggle.juggle.api.v1.shop.dto.CreateOrderRequest;
import com.juggle.juggle.common.data.LongRequest;
import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.framework.data.filter.PageSearch;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.shop.dto.ShopOrderCensus;
import com.juggle.juggle.primary.shop.dto.ShopOrderDto;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/shop/shopOrder")
public class ShopOrderApi extends BaseApi<ShopOrder,ShopOrder,ShopOrder> {
    @Autowired
    private ShopOrderService service;

    @Override
    protected ICRUDService<ShopOrder> getService() {
        return service;
    }

    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST})
    public @ResponseBody Object createOrder(@RequestBody CreateOrderRequest req){
        ShopOrderDto shopOrderDto = service.createOrder(req.getCount(),req.getProductId(),req.getSkuIds(),req.getAddressId());
        return ApiResponse.make(shopOrderDto);
    }

    @RequestMapping(value = "/cancelOrder",method = {RequestMethod.POST})
    public @ResponseBody Object cancelOrder(@RequestBody CancelOrderRequest req){
        ShopOrderDto shopOrderDto = service.cancelOrder(req.getId(),req.getRemark());
        return ApiResponse.make(shopOrderDto);
    }

    @RequestMapping(value = "/confirmReceived",method = {RequestMethod.POST})
    public @ResponseBody Object confirmReceived(@RequestBody LongRequest req){
        ShopOrderDto shopOrderDto = service.confirmReceived(req.getId());
        return ApiResponse.make(shopOrderDto);
    }

    @RequestMapping(value = "/commentOrder",method = {RequestMethod.POST})
    public @ResponseBody Object commentOrder(@RequestBody CommentOrderRequest req){
        ShopOrderDto shopOrderDto = service.commentOrder(req.getId(),req.getContent(),req.getImages(),req.getcStar());
        return ApiResponse.make(shopOrderDto);
    }

    @RequestMapping(value = "/searchOrder",method = {RequestMethod.POST})
    public @ResponseBody Object searchOrder(@RequestBody @Valid PageSearch pageRequest){
        String sort = pageRequest.getSort();
        if(StringUtils.isEmpty(sort)){
            sort = "id desc";
        }else if(!sort.contains("id")){
            sort = sort + ",id desc";
        }
        pageRequest.setSort(sort);
        return ApiResponse.make(service.searchOrder(pageRequest));
    }

    @RequestMapping(value = "/readOrder",method = {RequestMethod.POST})
    public @ResponseBody Object readOrder(@RequestBody LongRequest req){
        ShopOrderDto shopOrderDto = service.readOrder(req.getId());
        return ApiResponse.make(shopOrderDto);
    }

    @RequestMapping(value = "/getCensus",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object getCensus(){
        ShopOrderCensus orderCensus = service.getCensus();
        return ApiResponse.make(orderCensus);
    }
}
