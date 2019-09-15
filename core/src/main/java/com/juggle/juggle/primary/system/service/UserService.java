package com.juggle.juggle.primary.system.service;

import com.juggle.juggle.common.util.ValidationUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.system.dao.UserDao;
import com.juggle.juggle.primary.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseCRUDService<User> {
    @Autowired
    private UserDao dao;

    @Override
    protected IRepo<User> getRepo() {
        return dao;
    }

    @Override
    protected void onCreate(User entity) {
        ValidationUtil.valiMobile(entity.getMobile());
        ValidationUtil.valiEmail(entity.getEmail());
    }

    @Override
    protected void onUpdate(User entity) {
        ValidationUtil.valiMobile(entity.getMobile());
        ValidationUtil.valiEmail(entity.getEmail());
    }

    @Override
    protected void onDelete(Long id) {
        User user = (User) UserSession.getAuthorize().getUser();
        if(id.equals(user.getId())){
            throw new ServiceException(1001,"不能删除当前登录用户");
        }
    }

    public void normal(Long id){
        User user = dao.findOne(id);
        user.setStatus(Constants.USER_STATUS.NORMAL);
        update(id,user);
    }

    public void forbid(Long id){
        if(id.equals(UserSession.getAuthorize().getUserId())){
            throw new ServiceException(1001,"不能禁用当前登录用户");
        }
        User user = dao.findOne(id);
        user.setStatus(Constants.USER_STATUS.FORBID);
        update(id,user);
    }
}
