package com.juggle.juggle.primary.shop.service;

import com.juggle.juggle.common.data.SelectItem;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.shop.dao.ShopProductSkuTypeDao;
import com.juggle.juggle.primary.shop.model.ShopProductSkuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopProductSkuTypeService extends BaseCRUDService<ShopProductSkuType> {
    @Autowired
    private ShopProductSkuTypeDao dao;

    @Override
    protected IRepo<ShopProductSkuType> getRepo() {
        return dao;
    }

    public List<SelectItem> readAllItem(){
        List<SelectItem> items = new ArrayList<>();
        List<ShopProductSkuType> productSkuTypes = readAll();
        for (ShopProductSkuType productSkuType : productSkuTypes) {
            SelectItem item = new SelectItem();
            item.setLabel(productSkuType.getName());
            item.setValue(productSkuType.getId());
            items.add(item);
        }
        return items;
    }
}
