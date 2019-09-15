package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopTagDao extends IRepo<ShopTag> {
    List<ShopTag> findAllByEnabled(boolean enabled);

    List<ShopTag> findAllByIdInAndEnabled(List<Long> ids,boolean enabled);
}
