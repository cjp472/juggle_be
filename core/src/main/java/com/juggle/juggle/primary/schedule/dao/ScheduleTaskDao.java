package com.juggle.juggle.primary.schedule.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTaskDao extends IRepo<ScheduleTask> {
    ScheduleTask findFirstByCode(String code);
}
