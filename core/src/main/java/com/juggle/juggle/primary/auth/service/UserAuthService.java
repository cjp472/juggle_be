package com.juggle.juggle.primary.auth.service;

import com.juggle.juggle.common.service.AliyunSmsService;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.DateUtils;
import com.juggle.juggle.framework.common.utils.IpUtils;
import com.juggle.juggle.framework.common.utils.PasswordUtils;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.auth.dao.AccessTokenDao;
import com.juggle.juggle.primary.auth.dao.UserAuthDao;
import com.juggle.juggle.primary.auth.dto.UserProfile;
import com.juggle.juggle.primary.auth.model.AccessToken;
import com.juggle.juggle.primary.auth.model.UserAuth;
import com.juggle.juggle.primary.system.dao.UserDao;
import com.juggle.juggle.primary.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserAuthService extends BaseCRUDService<UserAuth> {
    @Autowired
    private UserAuthDao dao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccessTokenDao accessTokenDao;

    @Autowired
    private AliyunSmsService smsService;

    @Override
    protected IRepo<UserAuth> getRepo() {
        return dao;
    }

    public UserProfile login(String name, String password, HttpServletRequest request){
        User user = userDao.findFirstByLoginName(name);
        if(user==null){
            throw new ServiceException(1005,"账号或密码错误");
        }
        if(user.getStatus().equals(Constants.USER_STATUS.FORBID)){
            throw new ServiceException(1005,"账号已被禁用");
        }
        UserAuth userAuth = dao.findFirstByUserId(user.getId());
        if(!userAuth.getPassword().equals(PasswordUtils.encript(password))){
            throw new ServiceException(1005,"账号或密码错误");
        }
        updateLast(userAuth,request);
        AccessToken accessToken = updateToken(user);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setToken(accessToken.getToken());
        return userProfile;
    }

    public UserProfile loginByMobile(String mobile,String captcha,HttpServletRequest request){
        boolean verify = smsService.verifyCaptcha(mobile,captcha);
        if(!verify){
            throw new ServiceException(1005,"验证码错误");
        }
        User user = userDao.findFirstByMobile(mobile);
        if(user.getStatus().equals(Constants.USER_STATUS.FORBID)){
            throw new ServiceException(1005,"账号已被禁用");
        }
        UserAuth userAuth = dao.findFirstByUserId(user.getId());
        updateLast(userAuth,request);
        AccessToken accessToken = updateToken(user);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setToken(accessToken.getToken());
        return userProfile;
    }

    public void checkPwd(String password){
        User user = (User) UserSession.getAuthorize().getUser();
        UserAuth userAuth = dao.findFirstByUserId(user.getId());
        if(!PasswordUtils.encript(password).equals(userAuth.getPassword())){
            throw new ServiceException(1001,"用户密码错误");
        }
    }

    public void updatePwd(String oldPassword,String newPassword){
        User user = (User) UserSession.getAuthorize().getUser();
        UserAuth userAuth = dao.findFirstByUserId(user.getId());
        if(PasswordUtils.encript(oldPassword).equals(userAuth.getPassword())){
            userAuth.setPassword(PasswordUtils.encript(newPassword));
            update(userAuth.getId(),userAuth);
        }else{
            throw new ServiceException(1001,"旧密码错误");
        }
    }

    protected AccessToken updateToken(User user){
        AccessToken accessToken = accessTokenDao.findFirstByTypeAndTypeId(Constants.ACCESS_TOKEN_TYPE.USER,user.getId());
        Date now = new Date();
        if(null!=accessToken){
            accessToken.setToken(PasswordUtils.createRandom(16));
            accessToken.setExpiredTime(DateUtils.goFuture(now,604800));
            accessTokenDao.save(accessToken);
        }else{
            accessToken = new AccessToken();
            accessToken.setType(Constants.ACCESS_TOKEN_TYPE.USER);
            accessToken.setTypeId(user.getId());
            accessToken.setToken(PasswordUtils.createRandom(16));
            accessToken.setExpiredTime(DateUtils.goFuture(now,604800));
            accessTokenDao.save(accessToken);
        }
        return accessToken;
    }

    protected void updateLast(UserAuth userAuth,HttpServletRequest request){
        userAuth.setLastLoginIp(IpUtils.getIpAddr(request));
        userAuth.setLastLoginTime(new Date());
        update(userAuth.getId(),userAuth);
    }
}
