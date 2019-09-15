package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoSelection;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoSelectionDao extends IRepo<TaobaoSelection> {
    boolean existsByTitleLike(String title);
}
