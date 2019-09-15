package com.juggle.juggle.common.data;

import java.io.Serializable;

public class SmsParams implements Serializable {
    private String accessKey;

    private String accessSecret;

    private String signName;

    private String templateCode1;

    public String getAccessKey(){
        return accessKey;
    }

    public void setAccessKey(String accessKey){
        this.accessKey = accessKey;
    }

    public String getAccessSecret(){
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret){
        this.accessSecret = accessSecret;
    }

    public String getSignName(){
        return signName;
    }

    public void setSignName(String signName){
        this.signName = signName;
    }

    public String getTemplateCode1(){
        return templateCode1;
    }

    public void setTemplateCode1(String templateCode1){
        this.templateCode1 = templateCode1;
    }
}
