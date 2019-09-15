package com.juggle.juggle.primary.advert.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.advert.model.Advert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertDao extends IRepo<Advert> {
    List<Advert> findAllByTypeIdAndEnabledOrderBySort(Long typeId,boolean enabled);
}
