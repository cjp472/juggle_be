package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaobaoCategoryDao extends IRepo<TaobaoCategory> {
    List<TaobaoCategory> findAllByEnabledOrderBySort(boolean enabled);
}
