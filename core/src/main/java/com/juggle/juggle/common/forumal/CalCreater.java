package com.juggle.juggle.common.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.entity.Audit;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.system.dao.UserDao;
import com.juggle.juggle.primary.system.model.User;

import java.util.Collection;

public class CalCreater implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        Audit<Long> entity = (Audit) bean;
        UserDao userDao = ApplicationUtils.getBean(UserDao.class);
        User user = userDao.findOne(entity.getCreatedBy());
        return user;
    }
}
