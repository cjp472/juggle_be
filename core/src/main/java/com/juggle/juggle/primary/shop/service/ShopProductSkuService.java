package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.shop.dao.ShopProductSkuDao;
import com.juggle.juggle.primary.shop.model.ShopProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopProductSkuService extends BaseCRUDService<ShopProductSku> {
    @Autowired
    private ShopProductSkuDao dao;

    @Override
    protected IRepo<ShopProductSku> getRepo() {
        return dao;
    }
}
