package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.Withdraw;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface WithdrawDao extends IRepo<Withdraw> {
    List<Withdraw> findAllByAccountTypeAndStatusOrderByCreatedTime(String accountType,String status);

    @Query(value = "select sum(w.amount) from Withdraw w where w.memberId=:memberId and w.status='PAID'")
    BigDecimal sumAllPaidByMemberId(@Param("memberId") Long memberId);
}
