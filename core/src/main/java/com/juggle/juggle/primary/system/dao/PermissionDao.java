package com.juggle.juggle.primary.system.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.system.model.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao extends IRepo<Permission> {
}
