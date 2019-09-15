package com.juggle.juggle.api.v1.log;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.log.model.ScheduleLog;
import com.juggle.juggle.primary.log.service.ScheduleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/log/scheduleLog")
public class ScheduleLogApi extends BaseApi<ScheduleLog,ScheduleLog,ScheduleLog> {
    @Autowired
    private ScheduleLogService service;

    @Override
    protected ICRUDService<ScheduleLog> getService() {
        return service;
    }
}
