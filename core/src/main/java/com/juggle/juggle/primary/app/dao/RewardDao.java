package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.Reward;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface RewardDao extends IRepo<Reward> {
    @Query(value = "select sum(r.amount) from Reward r where r.memberId=:memberId and (r.status='PEND' or r.status='PAID')")
    BigDecimal sumAllRewardByMemberId(@Param("memberId") Long memberId);

    @Query(value = "select sum(r.amount) from Reward r where r.memberId=:memberId and r.status='PEND'")
    BigDecimal sumAllPendByMemberId(@Param("memberId") Long memberId);

    List<Reward> findAllByCreatedTimeLessThanAndStatus(Date createdTime, String status);

    List<Reward> findAllByOrderTypeAndOrderId(String orderType,Long orderId                     );
}
