package com.juggle.juggle.primary.approval.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.dao.MemberCertDao;
import com.juggle.juggle.primary.app.dao.WithdrawDao;
import com.juggle.juggle.primary.app.model.MemberCert;
import com.juggle.juggle.primary.app.model.Withdraw;
import com.juggle.juggle.primary.approval.model.Approval;

import java.util.Collection;

public class CalApprovalExtra implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        Approval entity = (Approval) bean;
        if(entity.getProcessInstanceType().equals(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.CERT)){
            MemberCertDao memberCertDao = ApplicationUtils.getBean(MemberCertDao.class);
            MemberCert memberCert = memberCertDao.findOne(entity.getProcessInstanceId());
            return memberCert;
        }else if(entity.getProcessInstanceType().equals(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.WITHDRAW)){
            WithdrawDao withdrawDao = ApplicationUtils.getBean(WithdrawDao.class);
            Withdraw withdraw = withdrawDao.findOne(entity.getProcessInstanceId());
            return withdraw;
        }else{
            return null;
        }
    }
}
