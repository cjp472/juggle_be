package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoProductDao extends IRepo<TaobaoProduct> {
    int countAllByBrandId(Long brandId);

    int deleteAllByBrandId(Long brandId);

    boolean existsByTitleLike(String title);
}
