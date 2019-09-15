package com.juggle.juggle.primary.app.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "member_cert")
public class MemberCert extends LongEntity {
    private Long memberId;

    @NotBlank
    private String realName;

    @NotBlank
    private String mobile;

    @NotBlank
    private String idCard;

    @NotBlank
    private String idCardFront;

    @NotBlank
    private String idCardBack;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
