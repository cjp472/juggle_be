package com.juggle.juggle.common.data;

import java.io.Serializable;

public class CaptchaRequest implements Serializable {
    private String mobile;

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }
}
