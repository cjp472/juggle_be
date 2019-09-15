package com.juggle.juggle.primary.recharge.service;

import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.recharge.dao.PhoneSkuDao;
import com.juggle.juggle.primary.recharge.model.PhoneSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneSkuService extends BaseCRUDService<PhoneSku> {
    @Autowired
    private PhoneSkuDao dao;

    @Override
    protected IRepo<PhoneSku> getRepo() {
        return dao;
    }

    @Cache
    public List<PhoneSku> readAllEnabled(){
        List<PhoneSku> skus = dao.findAllByEnabledOrderBySort(true);
        List<PhoneSku> dests = new ArrayList<>();
        for (PhoneSku phoneSku : skus) {
            PhoneSku sku = new PhoneSku();
            try {
                PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(sku,phoneSku,"sort","updatedTime","updatedBy","createdTime","createdBy");
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
            dests.add(sku);
        }
        return dests;
    }
}
