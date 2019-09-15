package com.juggle.juggle.api.v1;

import com.juggle.juggle.common.data.AliPayParams;
import com.juggle.juggle.common.util.AliPayUtil;
import com.juggle.juggle.framework.api.utils.ApiResponse;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.model.Reward;
import com.juggle.juggle.primary.app.service.RewardService;
import com.juggle.juggle.primary.setting.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/")
public class DefaultApi {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RewardService rewardService;

    @GetMapping("/**")
    public @ResponseBody
    Object index() {
        return ApiResponse.make("Server is working");
    }

    @RequestMapping(value = "/alipayTransfer", method = {RequestMethod.GET})
    public @ResponseBody
    Object alipayTransfer() {
        AliPayParams aliPayParams = dictionaryService.getAliPayDictionaries();
        AliPayUtil aliPayUtil = new AliPayUtil(aliPayParams, "GBK");
        aliPayUtil.toAccountTransfer("18814182439", "许光杰", BigDecimal.valueOf(0.1));
        return ApiResponse.make();
    }

    @RequestMapping(value = "createReward", method = {RequestMethod.GET})
    public @ResponseBody
    Object createReward() {
        Reward reward = new Reward();
        reward.setMemberId(38439L);
        reward.setAmount(BigDecimal.valueOf(0.1));
        reward.setType(Constants.REWARD_TYPE.SALE);
        reward.setOrderType(Constants.REWARD_ORDER_TYPE.SHOP);
        reward.setOrderId(46L);
        reward.setStatus(Constants.REWARD_STATUS.PEND);
        return ApiResponse.make(rewardService.create(reward));
    }

}
