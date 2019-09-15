package com.juggle.juggle.primary.log.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.log.dao.ScheduleLogDao;
import com.juggle.juggle.primary.log.model.ScheduleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleLogService extends BaseCRUDService<ScheduleLog> {
    @Autowired
    private ScheduleLogDao dao;

    @Override
    protected IRepo<ScheduleLog> getRepo() {
        return dao;
    }
}
