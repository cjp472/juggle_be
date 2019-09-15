package com.juggle.juggle.primary.auth.service;

import com.juggle.juggle.framework.common.session.UserInterface;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberDao;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.auth.dao.AccessTokenDao;
import com.juggle.juggle.primary.auth.model.AccessToken;
import com.juggle.juggle.primary.system.dao.UserDao;
import com.juggle.juggle.primary.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MemberDao memberDao;

    @Cacheable(value = "readUserProfile",key = "#token")
    public UserSession.Authorize<UserInterface> readUserProfile(String token) {
        AccessToken accessToken = accessTokenDao.findFirstByTypeAndTokenAndExpiredTimeGreaterThan(Constants.ACCESS_TOKEN_TYPE.USER,token,new Date());
        if(null==accessToken){
            return null;
        }
        User user = userDao.findFirstByIdAndStatus(accessToken.getTypeId(),Constants.USER_STATUS.NORMAL);
        if(null != user){
            return new UserSession.Authorize(token,user,null,null);
        }
        return null;
    }

    @Cacheable(value = "readMemberProfile",key = "#token")
    public UserSession.Authorize<UserInterface> readMemberProfile(String token) {
        AccessToken accessToken = accessTokenDao.findFirstByTypeAndTokenAndExpiredTimeGreaterThan(Constants.ACCESS_TOKEN_TYPE.MEMBER,token,new Date());
        if(null==accessToken){
            return null;
        }
        Member member = memberDao.findFirstByIdAndStatus(accessToken.getTypeId(),Constants.MEMBER_STATUS.NORMAL);
        if(null != member){
            return new UserSession.Authorize(token,member,null,null);
        }
        return null;
    }
}
