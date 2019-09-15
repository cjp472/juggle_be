package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MemberDto implements Serializable {
    private String code;

    private String avatar;

    private String nickName;

    private String mobile;

    private String grade;

    private Date termTime;

    private BigDecimal amount;

    private boolean certified;

    private boolean setPay;

    private Long relationId;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getTermTime() {
        return termTime;
    }

    public void setTermTime(Date termTime) {
        this.termTime = termTime;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public boolean isCertified() {
        return certified;
    }

    public void setCertified(boolean certified) {
        this.certified = certified;
    }

    public boolean isSetPay(){
        return setPay;
    }

    public void setSetPay(boolean setPay){
        this.setPay = setPay;
    }

    public Long getRelationId(){
        return relationId;
    }

    public void setRelationId(Long relationId){
        this.relationId = relationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
