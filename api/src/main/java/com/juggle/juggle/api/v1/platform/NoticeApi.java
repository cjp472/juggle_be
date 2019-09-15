package com.juggle.juggle.api.v1.platform;

import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.platform.service.NoticeService;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/platform/notice")
public class NoticeApi {
    @Autowired
    private NoticeService service;

    @RequestMapping(value = "/orderNotices",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object orderNotices(){
        List<ShopOrder> orders = service.getOrderNotices();
        return ApiResponse.make(orders);
    }
}
