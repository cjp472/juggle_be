package com.juggle.juggle.primary.advert.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.advert.dao.AdvertDao;
import com.juggle.juggle.primary.advert.dao.AdvertTypeDao;
import com.juggle.juggle.primary.advert.model.Advert;
import com.juggle.juggle.primary.advert.model.AdvertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService extends BaseCRUDService<Advert> {
    @Autowired
    private AdvertDao dao;

    @Autowired
    private AdvertTypeDao advertTypeDao;

    @Override
    protected IRepo<Advert> getRepo() {
        return dao;
    }

    @Cache
    public List<Advert> archive(String code){
        List<Advert> dests = new ArrayList<>();
        AdvertType advertType = advertTypeDao.findFirstByCode(code);
        if(advertType.isEnabled()){
            List<Advert> adverts = dao.findAllByTypeIdAndEnabledOrderBySort(advertType.getId(),true);
            for (Advert advert : adverts) {
                Advert dest = new Advert();
                try{
                    PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dest,advert,"typeId","name","detail","updatedTime","updatedBy","createdTime","createdBy");
                }catch (Exception e){
                    throw new ServiceException(1001,"PropertyCopyUtil解析失败");
                }
                dests.add(dest);
            }
        }
        return dests;
    }
}
