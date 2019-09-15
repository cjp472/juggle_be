package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoFlash;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface TaobaoFlashDao extends IRepo<TaobaoFlash> {
    @Transactional
    int deleteAllByStartTimeLessThan(@Param("time") Date time);

    boolean existsByTitleLike(String title);

    TaobaoFlash findFirstByItemId(Long itemId);
}
