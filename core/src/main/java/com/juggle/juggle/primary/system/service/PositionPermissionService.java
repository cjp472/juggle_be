package com.juggle.juggle.primary.system.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.system.dao.PositionPermissionDao;
import com.juggle.juggle.primary.system.model.PositionPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionPermissionService extends BaseCRUDService<PositionPermission> {
    @Autowired
    private PositionPermissionDao dao;

    @Override
    protected IRepo<PositionPermission> getRepo() {
        return dao;
    }
}
