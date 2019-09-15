package com.juggle.juggle.primary.platform.service;

import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.shop.dao.ShopOrderDao;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private ShopOrderDao shopOrderDao;

    public List<ShopOrder> getOrderNotices(){
        List<ShopOrder> orders = shopOrderDao.findFirst5ByStatusOrderByUpdatedTimeDesc(Constants.SHOP_ORDER_STATUS.PAID);
        return orders;
    }
}
