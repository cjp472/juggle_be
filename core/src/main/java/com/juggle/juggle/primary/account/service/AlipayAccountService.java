package com.juggle.juggle.primary.account.service;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.PropertyCopyUtil;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.account.dao.AlipayAccountDao;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.app.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayAccountService extends BaseCRUDService<AlipayAccount> {
    @Autowired
    private AlipayAccountDao dao;

    @Override
    protected IRepo<AlipayAccount> getRepo() {
        return dao;
    }

    public AlipayAccount bindAccount(String name,String account){
        Member member = (Member) UserSession.getAuthorize().getUser();
        AlipayAccount alipayAccount = dao.findFirstByMemberId(member.getId());
        if(null!=alipayAccount){
            alipayAccount.setName(name);
            alipayAccount.setAccount(account);
            alipayAccount = update(alipayAccount.getId(),alipayAccount);
        }else{
            alipayAccount = new AlipayAccount();
            alipayAccount.setMemberId(member.getId());
            alipayAccount.setName(member.getName());
            alipayAccount.setAccount(account);
            alipayAccount = create(alipayAccount);
        }
        AlipayAccount dest = new AlipayAccount();
        try{
            PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(dest,alipayAccount,"memberId","createdTime","updatedTime");
        }catch (Exception e){
            throw new ServiceException(1001,"PropertyCopyUtil解析失败");
        }
        return dest;
    }

    public AlipayAccount readAccount(){
        Member member = (Member) UserSession.getAuthorize().getUser();
        AlipayAccount alipayAccount = dao.findFirstByMemberId(member.getId());
        if(null!=alipayAccount){
            AlipayAccount account = new AlipayAccount();
            try{
                PropertyCopyUtil.getInstance().copyPropertiesWithIgnores(account,alipayAccount,"memberId","createdTime","updatedTime");
            }catch (Exception e){
                throw new ServiceException(1001,"PropertyCopyUtil解析失败");
            }
            return account;
        }else{
            return null;
        }
    }
}
