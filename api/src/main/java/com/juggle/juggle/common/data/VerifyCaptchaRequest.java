package com.juggle.juggle.common.data;

import java.io.Serializable;

public class VerifyCaptchaRequest implements Serializable {
    private String mobile;

    private String captcha;

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getCaptcha(){
        return captcha;
    }

    public void setCaptcha(String captcha){
        this.captcha = captcha;
    }
}
