package com.juggle.juggle.api.v1.log;

import com.juggle.juggle.framework.api.BaseApi;
import com.juggle.juggle.framework.data.service.ICRUDService;
import com.juggle.juggle.primary.log.model.OperateLog;
import com.juggle.juggle.primary.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/log/operateLog")
public class OperateLogApi extends BaseApi<OperateLog,OperateLog,OperateLog> {
    @Autowired
    private OperateLogService service;

    @Override
    protected ICRUDService<OperateLog> getService() {
        return service;
    }
}
