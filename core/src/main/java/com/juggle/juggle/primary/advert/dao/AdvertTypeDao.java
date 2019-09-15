package com.juggle.juggle.primary.advert.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.advert.model.AdvertType;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypeDao extends IRepo<AdvertType> {
    AdvertType findFirstByCode(String code);
}
