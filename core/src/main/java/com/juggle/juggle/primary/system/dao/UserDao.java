package com.juggle.juggle.primary.system.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends IRepo<User> {
    User findFirstByLoginName(String loginName);

    User findFirstByMobile(String mobile);

    User findFirstByIdAndStatus(Long id,String status);
}
