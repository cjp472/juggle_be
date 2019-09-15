package com.juggle.juggle.primary.platform.service;

import com.juggle.juggle.common.data.WxPayParams;
import com.juggle.juggle.common.util.WxPayUtil;
import com.juggle.juggle.common.util.XMLUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.IpUtils;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.recharge.model.PhoneBill;
import com.juggle.juggle.primary.recharge.service.PhoneBillService;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WxPayService {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private PhoneBillService phoneBillService;

    public Map<String,String> unifiedOrder(Long orderId, HttpServletRequest request){
        ShopOrder shopOrder = shopOrderService.find(orderId);
        if(null==shopOrder){
            throw new ServiceException(1001,"订单不存在");
        }
        if(!shopOrder.getStatus().equals(Constants.SHOP_ORDER_STATUS.OPEN)){
            throw new ServiceException(1001,"订单状态异常");
        }
        WxPayParams wxPayParams = dictionaryService.getWxPayDictionaries();
        WxPayUtil wxPayUtil = new WxPayUtil(wxPayParams);
        Map map = wxPayUtil.createUnifiedOrder(IpUtils.getIpAddr(request),shopOrder.getAmount(),wxPayParams.getNotifyUrl()+"/SHOP_ORDER:"+orderId);
        return map;
    }

    public void notify(String compose,String notifyXml) throws Exception{
        String[] strings = StringUtils.split(compose,":");
        String type = strings[0];
        Long id = Long.valueOf(strings[1]);
        XMLUtil xmlUtil= new XMLUtil();
        Map<String, String> map = xmlUtil.parseXml(notifyXml);
        if(map.get("result_code").equals("SUCCESS")&&map.get("return_code").equals("SUCCESS")){
            if(type.equals("SHOP_ORDER")){
                ShopOrder shopOrder = shopOrderService.find(id);
                confirmPaid(shopOrder);
            }else if(type.equals("PHONE_BILL")){
                PhoneBill phoneBill = phoneBillService.find(id);
                confirmPhoneBill(phoneBill);
            }
        }
    }

    private void confirmPaid(ShopOrder shopOrder){
        shopOrder.setPayWay(Constants.PAY_WAY.WXPAY);
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.PAID);
        shopOrderService.save(shopOrder);
    }

    private void confirmPhoneBill(PhoneBill phoneBill){
        phoneBill.setPayWay(Constants.PAY_WAY.ALIPAY);
        phoneBill.setStatus(Constants.PHONE_BILL_STATUS.WAIT);
        phoneBillService.save(phoneBill);
    }
}
