package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.shop.dao.ShopCouponDao;
import com.juggle.juggle.primary.shop.model.ShopCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopCouponService extends BaseCRUDService<ShopCoupon> {
    @Autowired
    private ShopCouponDao dao;

    @Override
    protected IRepo<ShopCoupon> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(ShopCoupon entity) {
        entity.setTake(0);
    }

    public void normal(Long id){
        ShopCoupon shopCoupon = dao.findOne(id);
        shopCoupon.setStatus(Constants.SHOP_COUPON_STATUS.NORMAL);
        update(id,shopCoupon);
    }

    public void forbid(Long id){
        ShopCoupon shopCoupon = dao.findOne(id);
        shopCoupon.setStatus(Constants.SHOP_COUPON_STATUS.FORBID);
        update(id,shopCoupon);
    }
}
