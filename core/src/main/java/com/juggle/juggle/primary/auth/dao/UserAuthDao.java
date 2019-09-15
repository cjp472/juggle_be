package com.juggle.juggle.primary.auth.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.auth.model.UserAuth;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthDao extends IRepo<UserAuth> {
    UserAuth findFirstByUserId(Long userId);
}
