package com.juggle.juggle.common.util;

import com.juggle.juggle.common.data.WxPayParams;
import com.juggle.juggle.common.data.WxUnifiedOrder;
import com.juggle.juggle.framework.common.errors.ServiceException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
 * 微信支付工具类*/
public class WxPayUtil {
    private WxPayParams params;

    public WxPayUtil(WxPayParams params) {
        this.params = params;
    }

    //创建微信支付加密签名
    public String createUnifiedOrderSign(WxUnifiedOrder unifiedOrder) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("body", unifiedOrder.getBody());
        map.put("mch_id", unifiedOrder.getMch_id());
        map.put("appid", unifiedOrder.getAppid());
        map.put("nonce_str", unifiedOrder.getNonce_str());
        map.put("notify_url", unifiedOrder.getNotify_url());
        map.put("spbill_create_ip", unifiedOrder.getSpbill_create_ip());
        map.put("out_trade_no", unifiedOrder.getOut_trade_no());
        map.put("total_fee", unifiedOrder.getTotal_fee());
        map.put("trade_type", unifiedOrder.getTrade_type());
        String sign = WxPaySignatureUtils.signature(map,params.getApiKey());
        return sign;
    }

    //微信支付统一下单请求
    public Map<String, String> createUnifiedOrder(String spbillCreateIp, BigDecimal totalFee, String notiftUrl){
        NetworkUtil baseUtil = new NetworkUtil();
        WxUnifiedOrder unifiedOrder = new WxUnifiedOrder();
        unifiedOrder.setAppid(params.getAppId());
        unifiedOrder.setBody("juggle");
        unifiedOrder.setMch_id(params.getMchId());
        unifiedOrder.setNonce_str(baseUtil.getCode());
        unifiedOrder.setNotify_url(notiftUrl);
        unifiedOrder.setOut_trade_no(baseUtil.getCode());
        unifiedOrder.setTrade_type("APP");
        unifiedOrder.setSpbill_create_ip(spbillCreateIp);
        unifiedOrder.setTotal_fee(totalFee.multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        unifiedOrder.setSign(createUnifiedOrderSign(unifiedOrder));
        XMLUtil xmlUtil = new XMLUtil();
        xmlUtil.xstream().alias("xml", unifiedOrder.getClass());
        String xml = xmlUtil.xstream().toXML(unifiedOrder);
        try{
            Map<String, String> map = baseUtil.httpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
            String prepayId = map.get("prepay_id");
            String timeStamp = String.valueOf((System.currentTimeMillis()/1000));
            Map<String,String> hashMap = new HashMap<>();
            hashMap.put("appid",params.getAppId());
            hashMap.put("partnerid",params.getMchId());
            hashMap.put("prepayid",prepayId);
            hashMap.put("package","Sign=WXPay");
            hashMap.put("noncestr",baseUtil.getCode());
            hashMap.put("timestamp",timeStamp);
            String paySign = WxPaySignatureUtils.signature(hashMap,params.getApiKey());
            hashMap.put("sign",paySign);
            return hashMap;
        }catch (Exception e){
            throw new ServiceException(1001,"微信支付统一下单请求失败");
        }
    }
}