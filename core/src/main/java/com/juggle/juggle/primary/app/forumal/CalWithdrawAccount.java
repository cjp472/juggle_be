package com.juggle.juggle.primary.app.forumal;

import com.juggle.juggle.framework.common.utils.ApplicationUtils;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.account.dao.AlipayAccountDao;
import com.juggle.juggle.primary.account.dao.BankAccountDao;
import com.juggle.juggle.primary.account.model.AlipayAccount;
import com.juggle.juggle.primary.account.model.BankAccount;
import com.juggle.juggle.primary.app.model.Withdraw;

import java.util.Collection;

public class CalWithdrawAccount implements Calculator {

    @Override
    public Object calc(Object bean, Collection exts) {
        Withdraw entity = (Withdraw) bean;
        if(entity.getAccountType().equals(Constants.WITHDRAW_ACCOUNT_TYPE.ALIPAY)){
            AlipayAccountDao accountDao = ApplicationUtils.getBean(AlipayAccountDao.class);
            AlipayAccount alipayAccount = accountDao.findOne(entity.getAccountId());
            return alipayAccount;
        }else{
            BankAccountDao accountDao = ApplicationUtils.getBean(BankAccountDao.class);
            BankAccount bankAccount = accountDao.findOne(entity.getAccountId());
            return bankAccount;
        }
    }
}
