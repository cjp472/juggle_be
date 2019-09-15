package com.juggle.juggle.primary.business.service;

import com.juggle.juggle.common.service.AliyunSmsService;
import com.juggle.juggle.common.util.ValidationUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.business.dao.SmsCodeDao;
import com.juggle.juggle.primary.business.model.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsCodeService extends BaseCRUDService<SmsCode> {
    @Autowired
    private SmsCodeDao dao;

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    protected IRepo<SmsCode> getRepo() {
        return dao;
    }

    public SmsCode findLatestSms(String mobile) {
        List<SmsCode> smsCodes = dao.findByMobileAndStatusOrderByIdDesc(mobile, Constants.SMS_CODE_STATUS.UNVERIFIED);
        if(smsCodes.size()>0)
            return smsCodes.get(0);
        return null;
    }

    public void getCaptcha(String mobile){
        ValidationUtil.valiMobile(mobile);
        aliyunSmsService.sendVerify(mobile);
    }

    public void verifyCaptcha(String mobile,String captcha){
        boolean valid = aliyunSmsService.verifyCaptcha(mobile,captcha);
        if(!valid){
            throw new ServiceException(1001,"验证码不正确");
        }
    }
}
