package com.juggle.juggle.primary.system.model;

import com.juggle.juggle.framework.common.session.UserInterface;
import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.constraint.meta.Unique;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.system.forumal.CalUserChains;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "user")
@Formula(value="chains",calc = CalUserChains.class)
@ExtView
public class User extends AuditEntity implements UserInterface {
    @One(value = "organziation",target = Organization.class)
    private Long organizationId;

    @One(value = "position",target = Position.class)
    private Long positionId;

    private String avatar;

    @Unique
    @Filtered
    @NotBlank
    private String loginName;

    @Filtered
    @NotBlank
    private String realName;

    @Filtered
    @NotBlank
    private String mobile;

    @Filtered
    @NotBlank
    private String email;

    private String detail;

    @NotBlank
    private String address;

    @Filtered
    @State(Constants.USER_STATUS.class)
    private String status;

    @Transient
    private List<Long> chains;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getLoginName(){
        return loginName;
    }

    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName(){
        return loginName;
    }

    public List<Long> getChains(){
        return chains;
    }

    public void setChains(List<Long> chains){
        this.chains = chains;
    }
}
