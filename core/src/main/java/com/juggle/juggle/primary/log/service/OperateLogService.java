package com.juggle.juggle.primary.log.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.log.dao.OperateLogDao;
import com.juggle.juggle.primary.log.model.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateLogService extends BaseCRUDService<OperateLog> {
    @Autowired
    private OperateLogDao dao;

    @Override
    protected IRepo<OperateLog> getRepo() {
        return dao;
    }
}
