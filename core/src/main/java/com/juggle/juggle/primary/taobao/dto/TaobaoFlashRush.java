package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;

public class TaobaoFlashRush implements Serializable {
    private String rushTime;

    private String status;

    public TaobaoFlashRush(String rushTime, String status) {
        this.rushTime = rushTime;
        this.status = status;
    }

    public String getRushTime(){
        return rushTime;
    }

    public void setRushTime(String rushTime){
        this.rushTime = rushTime;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
