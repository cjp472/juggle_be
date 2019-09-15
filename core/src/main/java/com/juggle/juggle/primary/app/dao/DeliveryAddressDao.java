package com.juggle.juggle.primary.app.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.app.model.DeliveryAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAddressDao extends IRepo<DeliveryAddress> {
    List<DeliveryAddress> findAllByMemberId(Long memberId);

    @Modifying
    @Query(value = "update DeliveryAddress d set d.present=false where d.memberId=:memberId and d.id<>:id")
    int updatePresent(@Param("memberId") Long memberId,@Param("id") Long id);
}
