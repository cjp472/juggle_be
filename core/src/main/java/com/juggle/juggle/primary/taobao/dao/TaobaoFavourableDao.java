package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoFavourable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoFavourableDao extends IRepo<TaobaoFavourable> {
    boolean existsByTitleLike(String title);
}
