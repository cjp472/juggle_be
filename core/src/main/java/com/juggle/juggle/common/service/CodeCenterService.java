package com.juggle.juggle.common.service;

import com.juggle.juggle.framework.data.idm.IdentityManager;
import com.juggle.juggle.primary.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CodeCenterService {
    @Resource
    private IdentityManager idManager;

    public String genCommunityCode() {
        return "C-" + getCode(Constants.IDS.COMMUNITY);
    }

    public String genArticleCode() {
        return "I-" + getCode(Constants.IDS.ARTICLE);
    }

    public String genResourceCode() {
        return "R-" + getCode(Constants.IDS.RESOURCE);
    }

    public String genTaobaoCategoryCode() {
        return "T-" + getCode(Constants.IDS.TAOBAO_CATEGORY);
    }

    public String genTaobaoTypeCode() {
        return "D-" + getCode(Constants.IDS.TAOBAO_TYPE);
    }

    public String genShopTypeCode(){
        return "S-" + getCode(Constants.IDS.SHOP_TYPE);
    }

    public String genShopTagCode(){
        return "L-" + getCode(Constants.IDS.SHOP_TAG);
    }

    private String getCode(String name) {
        return StringUtils.leftPad(String.valueOf(idManager.next(name)), Constants.IDS.PAD, '0');
    }

    private String getLongCode(String name) {
        return StringUtils.leftPad(String.valueOf(idManager.next(name)), Constants.IDS.PAD_L, '0');
    }
}
