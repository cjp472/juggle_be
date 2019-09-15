package com.juggle.juggle.primary.auth.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "member_auth")
public class MemberAuth extends LongEntity {
    private Long memberId;

    private String password;

    private String payPassword;

    private Date lastLoginTime;

    private String lastLoginIp;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword(){
        return payPassword;
    }

    public void setPayPassword(String payPassword){
        this.payPassword = payPassword;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
