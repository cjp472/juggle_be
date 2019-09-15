package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoPopular;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoPopularDao extends IRepo<TaobaoPopular> {
    boolean existsByTitleLike(String title);
}
