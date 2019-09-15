package com.juggle.juggle.common.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.juggle.juggle.common.data.SmsParams;
import com.juggle.juggle.common.data.SmsResponse;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.business.model.SmsCode;
import com.juggle.juggle.primary.business.service.SmsCodeService;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class AliyunSmsService {
    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private DictionaryService dictionaryService;

    private Logger logger = LoggerFactory.getLogger(AliyunSmsService.class);

    private String VERIFY_CODES = "1234567890";

    private long SMS_VERIFY_INTERVAL = 300;

    private SmsResponse sendMsg(String mobile) {
        SmsParams smsParams = dictionaryService.getSmsDictionaries();
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen", smsParams.getAccessKey(), smsParams.getAccessSecret());
            DefaultProfile.addEndpoint("cn-shenzhen", "cn-shenzhen", "Dysmsapi",  "dysmsapi.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(smsParams.getSignName());
            String code = new String();
            request.setTemplateCode(smsParams.getTemplateCode1());
            code = generateVerifyCode(6, null);
            request.setTemplateParam("{\"code\":\""+code+"\"}");
            request.setPhoneNumbers(mobile);
            SendSmsResponse httpResponse = client.getAcsResponse(request);
            SmsCode smsCode = new SmsCode();
            smsCode.setCode(code);
            smsCode.setMobile(mobile);
            smsCode.setStatus(Constants.SMS_CODE_STATUS.UNVERIFIED);
            smsCodeService.save(smsCode);
            return new SmsResponse(httpResponse);
        } catch (ServerException e) {
            SmsResponse smsResponse = new SmsResponse(e);
            logger.warn(smsResponse.toString());
            return smsResponse;
        }
        catch (ClientException e) {
            SmsResponse smsResponse = new SmsResponse(e);
            logger.warn(smsResponse.toString());
            return smsResponse;
        }
    }

    public SmsResponse sendVerify(String mobile){
        return sendMsg(mobile);
    }

    public boolean verifyCaptcha(String mobile,String code){
        SmsCode smsCode = smsCodeService.findLatestSms(mobile);
        if(smsCode != null) {
            smsCode.setStatus(Constants.SMS_CODE_STATUS.VERIFIED);
            smsCodeService.save(smsCode);
            Date createDate = smsCode.getCreatedTime();
            Date currentDate = new Date();
            long interval = (currentDate.getTime() - createDate.getTime())/1000;
            if(smsCode.getCode().equals(code) && interval < SMS_VERIFY_INTERVAL) {
                return true;
            }
        }
        return false;
    }

    private String generateVerifyCode(int verifySize, String sources){
        if(sources == null || sources.length() == 0){
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for(int i = 0; i < verifySize; i++){
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
        }
        return verifyCode.toString();
    }
}
