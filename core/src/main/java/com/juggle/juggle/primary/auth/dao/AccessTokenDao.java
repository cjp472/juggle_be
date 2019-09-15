package com.juggle.juggle.primary.auth.dao;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.primary.auth.model.AccessToken;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AccessTokenDao extends IRepo<AccessToken> {
    AccessToken findFirstByTypeAndTypeId(String type,Long typeId);

    AccessToken findFirstByTypeAndToken(String type,String token);

    AccessToken findFirstByTypeAndTokenAndExpiredTimeGreaterThan(String type,String token, Date expiredTime);
}
