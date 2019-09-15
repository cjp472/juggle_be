package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopExpress;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopExpressDao extends IRepo<ShopExpress> {
    ShopExpress findFirstByOrderId(Long orderId);
}
