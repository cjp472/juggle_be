package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberExtra implements Serializable {
    private String realName;

    private String idCard;

    private String mobile;

    private String idCardFront;

    private String idCardBack;

    private String alipayName;

    private String alipayAccount;

    private Date lastLoginTime;

    private String lastLoginIp;

    public String getRealName(){
        return realName;
    }

    public void setRealName(String realName){
        this.realName = realName;
    }

    public String getIdCard(){
        return idCard;
    }

    public void setIdCard(String idCard){
        this.idCard = idCard;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getIdCardFront(){
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront){
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack(){
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack){
        this.idCardBack = idCardBack;
    }

    public String getAlipayName(){
        return alipayName;
    }

    public void setAlipayName(String alipayName){
        this.alipayName = alipayName;
    }

    public String getAlipayAccount(){
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount){
        this.alipayAccount = alipayAccount;
    }

    public Date getLastLoginTime(){
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp(){
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp){
        this.lastLoginIp = lastLoginIp;
    }
}
