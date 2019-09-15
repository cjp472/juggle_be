package com.juggle.juggle.primary.platform.service;

import com.juggle.juggle.common.data.AliPayParams;
import com.juggle.juggle.common.util.AliPayUtil;
import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.recharge.model.PhoneBill;
import com.juggle.juggle.primary.recharge.service.PhoneBillService;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import com.juggle.juggle.primary.shop.model.ShopOrder;
import com.juggle.juggle.primary.shop.service.ShopOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AliPayService {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private PhoneBillService phoneBillService;

    public String preCreate(Long orderId){
        ShopOrder shopOrder = shopOrderService.find(orderId);
        if(null==shopOrder){
            throw new ServiceException(1001,"订单不存在");
        }
        if(!shopOrder.getStatus().equals(Constants.SHOP_ORDER_STATUS.OPEN)){
            throw new ServiceException(1001,"订单状态异常");
        }
        AliPayParams aliPayParams = dictionaryService.getAliPayDictionaries();
        AliPayUtil aliPayUtil = new AliPayUtil(aliPayParams,"utf-8");
        String result = aliPayUtil.preCreate(shopOrder.getAmount(),aliPayParams.getNotifyUrl()+"/SHOP_ORDER:"+orderId);
        return result;
    }

    public void notify(String compose,Map<String,String[]> map){
        String[] strings = StringUtils.split(compose,":");
        String type = strings[0];
        Long id = Long.valueOf(strings[1]);
        if(map.get("trade_status")[0].equals("TRADE_SUCCESS")){
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
        shopOrder.setPayWay(Constants.PAY_WAY.ALIPAY);
        shopOrder.setStatus(Constants.SHOP_ORDER_STATUS.PAID);
        shopOrderService.save(shopOrder);
    }

    private void confirmPhoneBill(PhoneBill phoneBill){
        phoneBill.setPayWay(Constants.PAY_WAY.ALIPAY);
        phoneBill.setStatus(Constants.PHONE_BILL_STATUS.WAIT);
        phoneBillService.save(phoneBill);
    }
}
