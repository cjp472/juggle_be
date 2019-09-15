package com.juggle.juggle.primary.taobao.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.taobao.model.TaobaoOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaobaoOrderDao extends IRepo<TaobaoOrder> {
    TaobaoOrder findFirstByTradeId(String tradeId);

    boolean existsByRelationIdAndTkStatus(Long relationId,Integer tkStatus);

    TaobaoOrder findFirstByRelationIdAndTkStatus(Long relationId,Integer tkStatus);

    @Query(value = "select t from TaobaoOrder t where t.handle=:handle")
    List<TaobaoOrder> findAllByHandle(@Param("handle") boolean handle);
}
