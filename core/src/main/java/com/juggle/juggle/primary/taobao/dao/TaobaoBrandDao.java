package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoBrand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaobaoBrandDao extends IRepo<TaobaoBrand> {
    int countAllByCategoryId(Long categoryId);

    List<TaobaoBrand> findAllByEnabledAndCategoryId(boolean enabled,Long categoryId);

    List<TaobaoBrand> findAllByEnabled(boolean enabled);
}
