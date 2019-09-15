package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopProductDao extends IRepo<ShopProduct> {
    List<ShopProduct> findAllByRecommendAndEnabled(boolean recommend,boolean enabled);

    boolean existsByNameLike(String name);
}
