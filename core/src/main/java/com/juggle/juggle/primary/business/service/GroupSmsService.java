package com.juggle.juggle.primary.business.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.business.dao.GroupSmsDao;
import com.juggle.juggle.primary.business.model.GroupSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupSmsService extends BaseCRUDService<GroupSms> {
    @Autowired
    private GroupSmsDao dao;

    @Override
    protected IRepo<GroupSms> getRepo() {
        return dao;
    }

    public GroupSms send(Long id){
        GroupSms groupSms = dao.findOne(id);
        groupSms.setStatus(Constants.PUSH_STATUS.COMPLETE);
        return update(id,groupSms);
    }
}
