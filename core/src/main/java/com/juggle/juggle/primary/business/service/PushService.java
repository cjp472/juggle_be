package com.juggle.juggle.primary.business.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.business.dao.PushDao;
import com.juggle.juggle.primary.business.model.Push;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushService extends BaseCRUDService<Push> {
    @Autowired
    private PushDao dao;

    @Override
    protected IRepo<Push> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(Push entity) {
        entity.setStatus(Constants.PUSH_STATUS.WAIT);
    }

    public Push push(Long id){
        Push push = dao.findOne(id);
        push.setStatus(Constants.PUSH_STATUS.COMPLETE);
        return update(id,push);
    }
}
