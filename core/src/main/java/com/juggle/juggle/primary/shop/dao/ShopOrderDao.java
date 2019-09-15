package com.juggle.juggle.primary.shop.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ShopOrderDao extends IRepo<ShopOrder> {
    @Query(value = "select sum(s.amount) from ShopOrder s where s.status<>'OPEN' and s.status<>'CANCEL' and s.status<>'CLOSED'")
    BigDecimal sumAllOrdersAmount();

    @Query(value = "select count(s.id) from ShopOrder s where s.status<>'OPEN' and s.status<>'CANCEL' and s.status<>'CLOSED'")
    Long countAllOrders();

    List<ShopOrder> findAllByStatusAndCreatedTimeLessThan(String status, Date createdTime);

    List<ShopOrder> findFirst5ByStatusOrderByUpdatedTimeDesc(String status);

    int countAllByMemberIdAndStatus(Long memberId,String status);

    @Query(value = "select s from ShopOrder s where s.handle=:handle and s.status<>'OPEN' and s.status<>'CANCEL' and s.status<>'CLOSED'")
    List<ShopOrder> findAllByHandleAndStatus(@Param("handle") boolean handle);
}
