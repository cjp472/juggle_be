package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoGoods;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoGoodsDao extends IRepo<TaobaoGoods> {
    boolean existsByTitleLike(String title);
}
