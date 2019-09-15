package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopProductSku;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopProductSkuDao extends IRepo<ShopProductSku> {
    List<ShopProductSku> findAllByProductId(Long productId);

    List<ShopProductSku> findAllByIdInAndEnabled(List<Long> ids,boolean enabled);
}
