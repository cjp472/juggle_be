package com.juggle.juggle.primary.recharge.service;

import com.juggle.juggle.common.data.AliPayParams;
import com.juggle.juggle.common.data.WxPayParams;
import com.juggle.juggle.common.util.AliPayUtil;
import com.juggle.juggle.common.util.WxPayUtil;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.IpUtils;
import com.juggle.juggle.framework.data.repo.IRepo;
import com.juggle.juggle.framework.data.service.BaseCRUDService;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.recharge.dao.PhoneBillDao;
import com.juggle.juggle.primary.recharge.dao.PhoneSkuDao;
import com.juggle.juggle.primary.recharge.model.PhoneBill;
import com.juggle.juggle.primary.recharge.model.PhoneSku;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class PhoneBillService extends BaseCRUDService<PhoneBill> {
    @Autowired
    private PhoneBillDao dao;

    @Autowired
    private PhoneSkuDao phoneSkuDao;

    @Autowired
    private DictionaryService dictionaryService;

    @Override
    protected IRepo<PhoneBill> getRepo() {
        return dao;
    }

    public Map<String,String> createWxBill(String mobile, Long skuId, HttpServletRequest request){
        PhoneBill phoneBill = createBillByPayWay(Constants.PAY_WAY.WXPAY,mobile,skuId);
        WxPayParams wxPayParams = dictionaryService.getWxPayDictionaries();
        WxPayUtil wxPayUtil = new WxPayUtil(wxPayParams);
        Map<String,String> signature = wxPayUtil.createUnifiedOrder(IpUtils.getIpAddr(request),phoneBill.getAmount(),wxPayParams.getNotifyUrl()+"/PHONE_BILL:"+phoneBill.getId());
        return signature;
    }

    public String createAliBill(String mobile, Long skuId){
        PhoneBill phoneBill = createBillByPayWay(Constants.PAY_WAY.ALIPAY,mobile,skuId);
        AliPayParams aliPayParams = dictionaryService.getAliPayDictionaries();
        AliPayUtil aliPayUtil = new AliPayUtil(aliPayParams,"utf-8");
        String signature = aliPayUtil.preCreate(phoneBill.getAmount(),aliPayParams.getNotifyUrl()+"/PHONE_BILL:"+phoneBill.getId());
        return signature;
    }

    private PhoneBill createBillByPayWay(String payWay,String mobile,Long skuId){
        Member member = (Member) UserSession.getAuthorize().getUser();
        PhoneSku phoneSku = phoneSkuDao.findOne(skuId);
        PhoneBill phoneBill = new PhoneBill();
        phoneBill.setMemberId(member.getId());
        phoneBill.setPayWay(payWay);
        phoneBill.setMobile(mobile);
        phoneBill.setDenomination(phoneSku.getDenomination());
        phoneBill.setAmount(phoneSku.getPrice());
        phoneBill.setStatus(Constants.PHONE_BILL_STATUS.OPEN);
        return create(phoneBill);
    }
}
