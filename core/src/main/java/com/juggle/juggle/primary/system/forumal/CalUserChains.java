package com.juggle.juggle.primary.system.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.system.dao.OrganizationDao;
import com.juggle.juggle.primary.system.model.Organization;
import com.juggle.juggle.primary.system.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalUserChains implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        User entity = (User) bean;
        OrganizationDao organizationDao = ApplicationUtils.getBean(OrganizationDao.class);
        List<Long> chains = new ArrayList<>();
        if(null!=entity.getOrganizationId()){
            chains = iterationChains(entity.getId(),chains,organizationDao);
        }
        return chains;
    }

    private List<Long> iterationChains(Long id,List<Long> chains,OrganizationDao organizationDao){
        chains.add(0,id);
        Organization organization = organizationDao.findOne(id);
        if(null!=organization.getParentId()){
            chains = iterationChains(organization.getParentId(),chains,organizationDao);
        }
        return chains;
    }
}
