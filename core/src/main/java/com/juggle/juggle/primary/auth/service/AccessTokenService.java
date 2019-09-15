package com.juggle.juggle.primary.auth.service;

import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.auth.dao.AccessTokenDao;
import com.juggle.juggle.primary.auth.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService extends BaseCRUDService<AccessToken> {
    @Autowired
    private AccessTokenDao dao;

    @Override
    protected IRepo<AccessToken> getRepo() {
        return dao;
    }
}
