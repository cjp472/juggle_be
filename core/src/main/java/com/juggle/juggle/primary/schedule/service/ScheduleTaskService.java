package com.juggle.juggle.primary.schedule.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.schedule.dao.ScheduleTaskDao;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskService extends BaseCRUDService<ScheduleTask> {
    @Autowired
    private ScheduleTaskDao dao;

    @Override
    protected IRepo<ScheduleTask> getRepo() {
        return dao;
    }

    public ScheduleTask findByCode(String code){
        ScheduleTask scheduleTask = dao.findFirstByCode(code);
        return scheduleTask;
    }
}
