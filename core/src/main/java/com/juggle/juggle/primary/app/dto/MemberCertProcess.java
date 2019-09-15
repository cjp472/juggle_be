package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;

public class MemberCertProcess implements Serializable {
    private boolean certified;

    private String processStatus;

    public boolean isCertified(){
        return certified;
    }

    public void setCertified(boolean certified){
        this.certified = certified;
    }

    public String getProcessStatus(){
        return processStatus;
    }

    public void setProcessStatus(String processStatus){
        this.processStatus = processStatus;
    }
}
