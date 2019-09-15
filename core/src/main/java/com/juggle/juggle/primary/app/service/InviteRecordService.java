package com.juggle.juggle.primary.app.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.app.dao.InviteRecordDao;
import com.juggle.juggle.primary.app.model.InviteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteRecordService extends BaseCRUDService<InviteRecord> {
    @Autowired
    private InviteRecordDao dao;

    @Override
    protected IRepo<InviteRecord> getRepo() {
        return dao;
    }
}
