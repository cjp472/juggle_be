package com.juggle.juggle.common.data;

import java.io.Serializable;

public class WxPayParams implements Serializable {
    private String apiKey;

    private String appId;

    private String mchId;

    private String notifyUrl;

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getAppId(){
        return appId;
    }

    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getMchId(){
        return mchId;
    }

    public void setMchId(String mchId){
        this.mchId = mchId;
    }

    public String getNotifyUrl(){
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl){
        this.notifyUrl = notifyUrl;
    }
}
