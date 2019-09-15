package com.juggle.juggle.primary.app.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.account.dao.AlipayAccountDao;
import com.juggle.juggle.primary.app.dao.MemberCertDao;
import com.juggle.juggle.primary.app.dto.MemberExtra;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.auth.dao.MemberAuthDao;
import com.juggle.juggle.primary.auth.model.MemberAuth;

import java.util.Collection;

public class CalMemberExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        Member entity = (Member) bean;
        MemberAuthDao memberAuthDao = ApplicationUtils.getBean(MemberAuthDao.class);
        MemberCertDao memberCertDao = ApplicationUtils.getBean(MemberCertDao.class);
        MemberAuth memberAuth = memberAuthDao.findFirstByMemberId(entity.getId());
        MemberCert memberCert = memberCertDao.findFirstByMemberId(entity.getId());
        AlipayAccountDao alipayAccountDao = ApplicationUtils.getBean(AlipayAccountDao.class);
        MemberExtra extra = new MemberExtra();
        if(null!=memberCert){
            extra.setRealName(memberCert.getRealName());
            extra.setIdCard(memberCert.getIdCard());
            extra.setMobile(memberCert.getMobile());
            extra.setIdCardFront(memberCert.getIdCardFront());
            extra.setIdCardBack(memberCert.getIdCardBack());
        }
        AlipayAccount alipayAccount = alipayAccountDao.findFirstByMemberId(entity.getId());
        if(null!=alipayAccount){
            extra.setAlipayName(alipayAccount.getName());
            extra.setAlipayAccount(alipayAccount.getAccount());
        }
        if(null!=memberAuth){
            extra.setLastLoginTime(memberAuth.getLastLoginTime());
            extra.setLastLoginIp(memberAuth.getLastLoginIp());
        }
        return extra;
    }
}
