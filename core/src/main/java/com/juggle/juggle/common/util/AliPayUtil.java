package com.juggle.juggle.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.juggle.juggle.common.data.AliPayParams;
import com.juggle.juggle.framework.common.errors.ServiceException;

import java.math.BigDecimal;

public class AliPayUtil {
    private AliPayParams params;

    private AlipayClient alipayClient;

    public AliPayUtil(AliPayParams params,String charset) {
        this.params = params;
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", params.getAppId(), params.getAlipayPrivateKey(),"json", charset, params.getAlipayPublicKey(),"RSA2");
    }

    public String preCreate(BigDecimal amount,String notifyUrl){
        NetworkUtil baseUtil = new NetworkUtil();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("juggle");
        model.setSubject("juggle");
        model.setOutTradeNo(baseUtil.getCode());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(amount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new ServiceException(1001,"支付宝支付预下单失败");
        }
    }

    public void toAccountTransfer(String payeeAccount,String payeeRealName,BigDecimal amount){
        NetworkUtil baseUtil = new NetworkUtil();
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\""+baseUtil.getCode()+"\"," +
                "\"payee_type\":\"ALIPAY_LOGONID\"," +
                "\"payee_account\":\""+payeeAccount+"\"," +
                "\"amount\":\""+amount.toString()+"\"," +
                "\"payer_show_name\":\"华利来呗\"," +
                "\"payee_real_name\":\""+payeeRealName+"\"," +
                "\"remark\":\"佣金提现\"" +
                "  }");
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        }catch (Exception e){
            throw new ServiceException(1001,"单笔转账到支付宝账户接口调用失败");
        }
    }
}
