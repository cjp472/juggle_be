package com.juggle.juggle.primary.shop.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.shop.dao.ShopTypeDao;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import com.juggle.juggle.primary.shop.model.ShopType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalShopProductChains implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        ShopProduct entity = (ShopProduct) bean;
        ShopTypeDao shopTypeDao = ApplicationUtils.getBean(ShopTypeDao.class);
        List<Long> chains = new ArrayList<>();
        if(null!=entity.getTypeId()){
            chains = iterationChains(entity.getTypeId(),chains,shopTypeDao);
        }
        return chains;
    }

    private List<Long> iterationChains(Long id,List<Long> chains,ShopTypeDao shopTypeDao){
        chains.add(0,id);
        ShopType shopType = shopTypeDao.findOne(id);
        if(null!=shopType.getParentId()){
            chains = iterationChains(shopType.getParentId(),chains,shopTypeDao);
        }
        return chains;
    }
}
