package com.juggle.juggle.primary.recharge.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.recharge.model.PhoneSku;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneSkuDao extends IRepo<PhoneSku> {
    List<PhoneSku> findAllByEnabledOrderBySort(boolean enabled);
}
