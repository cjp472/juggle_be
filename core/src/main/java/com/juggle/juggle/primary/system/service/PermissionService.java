package com.juggle.juggle.primary.system.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.system.dao.PermissionDao;
import com.juggle.juggle.primary.system.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends BaseCRUDService<Permission> {
    @Autowired
    private PermissionDao dao;

    @Override
    protected IRepo<Permission> getRepo() {
        return dao;
    }
}
