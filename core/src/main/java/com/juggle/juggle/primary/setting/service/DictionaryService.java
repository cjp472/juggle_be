package com.juggle.juggle.primary.setting.service;

import com.juggle.juggle.common.data.*;
import com.juggle.juggle.framework.cache.meta.Cache;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.setting.dao.DictionaryDao;
import com.juggle.juggle.primary.setting.dto.RebateRatios;
import com.juggle.juggle.primary.setting.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DictionaryService extends BaseCRUDService<Dictionary> {
    @Autowired
    private DictionaryDao dao;

    @Override
    protected IRepo<Dictionary> getRepo() {
        return dao;
    }

    @Cache
    public Dictionary getFirstByTypeAndDictKey(String type,String dictKey){
        Dictionary dictionary = dao.findFirstByTypeAndDictKey(type,dictKey);
        return dictionary;
    }

    @Cache
    public TaobaoClientParams getTaobaoDictionaries() {
        TaobaoClientParams taobaoClientParams = new TaobaoClientParams();
        taobaoClientParams.setAppKey(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.TAOBAO,"APP_KEY").getDictValue());
        taobaoClientParams.setAppSecret(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.TAOBAO,"APP_SECRET").getDictValue());
        taobaoClientParams.setAdzoneId(Long.valueOf(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.TAOBAO,"ADZONE_ID").getDictValue()));
        return taobaoClientParams;
    }

    @Cache
    public OSSParams getOssDictionaries() {
        OSSParams ossParams = new OSSParams();
        ossParams.setAccessKeyId(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.OSS,"ACCESS_KEY_ID").getDictValue());
        ossParams.setAccessKeySecret(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.OSS,"ACCESS_KEY_SECRET").getDictValue());
        ossParams.setBucketName(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.OSS,"BUCKET_NAME").getDictValue());
        return ossParams;
    }

    @Cache
    public SmsParams getSmsDictionaries(){
        SmsParams smsParams = new SmsParams();
        smsParams.setAccessKey(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SMS,"ACCESS_KEY").getDictValue());
        smsParams.setAccessSecret(dao.findFirstByTypeAndDictKey (Constants.DICTIONARY_TYPE.SMS,"ACCESS_SECRET").getDictValue());
        smsParams.setSignName(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SMS,"SIGN_NAME").getDictValue());
        smsParams.setTemplateCode1(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SMS,"TEMPLATE_CODE").getDictValue());
        return smsParams;
    }

    @Cache
    public RebateRatios getTaobaoRebateDictionaries(){
        RebateRatios rebateRatios = new RebateRatios();
        rebateRatios.setPrimaryRatio(new BigDecimal(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_GRADE1_TAOBAO").getDictValue()));
        rebateRatios.setSecondaryRatio(new BigDecimal(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.SYSTEM,"REBATE_GRADE2_TAOBAO").getDictValue()));
        return rebateRatios;
    }

    @Cache
    public WxPayParams getWxPayDictionaries(){
        WxPayParams wxPayParams = new WxPayParams();
        wxPayParams.setApiKey(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.WXPAY,"API_KEY").getDictValue());
        wxPayParams.setAppId(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.WXPAY,"APP_ID").getDictValue());
        wxPayParams.setMchId(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.WXPAY,"MCH_ID").getDictValue());
        wxPayParams.setNotifyUrl(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.WXPAY,"NOTIFY_URL").getDictValue());
        return wxPayParams;
    }

    @Cache
    public AliPayParams getAliPayDictionaries(){
        AliPayParams aliPayParams = new AliPayParams();
        aliPayParams.setAppId(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.ALIPAY,"APP_ID").getDictValue());
        aliPayParams.setAlipayPublicKey(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.ALIPAY,"PUBLIC_KEY").getDictValue());
        aliPayParams.setAlipayPrivateKey(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.ALIPAY,"PRIVATE_KEY").getDictValue());
        aliPayParams.setNotifyUrl(dao.findFirstByTypeAndDictKey(Constants.DICTIONARY_TYPE.ALIPAY,"NOTIFY_URL").getDictValue());
        return aliPayParams;
    }
}
