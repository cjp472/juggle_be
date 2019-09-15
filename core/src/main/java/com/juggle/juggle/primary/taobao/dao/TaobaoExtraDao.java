package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoExtra;
import org.springframework.stereotype.Repository;

@Repository
public interface TaobaoExtraDao extends IRepo<TaobaoExtra> {
    TaobaoExtra findFirstBySubAndSubId(String sub, Long subId);
}
