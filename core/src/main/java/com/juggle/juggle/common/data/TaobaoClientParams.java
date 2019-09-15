package com.juggle.juggle.common.data;

import java.io.Serializable;

public class TaobaoClientParams implements Serializable {
    private String appKey;

    private String appSecret;

    private Long adzoneId;

    public String getAppKey(){
        return appKey;
    }

    public void setAppKey(String appKey){
        this.appKey = appKey;
    }

    public String getAppSecret(){
        return appSecret;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }

    public Long getAdzoneId(){
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId){
        this.adzoneId = adzoneId;
    }
}
