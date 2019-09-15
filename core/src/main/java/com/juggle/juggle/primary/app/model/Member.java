package com.juggle.juggle.primary.app.model;

import com.juggle.juggle.framework.common.session.UserInterface;
import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.constraint.meta.Unique;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.forumal.CalMemberExtra;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "member")
@Formula(value="extra",calc = CalMemberExtra.class)
@ExtView
public class Member extends LongEntity implements UserInterface {
    private Long relationId;

    @Filtered
    private Long parentId;

    @Unique
    @Filtered
    private String code;

    private String avatar;

    @Filtered
    private String nickName;

    @Unique
    @NotBlank
    @Filtered
    private String mobile;

    private String gender;

    @Filtered
    @State(Constants.MEMBER_GRADE.class)
    private String grade;

    private Date termTime;

    private Long domainId;

    @Filtered
    private boolean certified;

    @Filtered
    @State(Constants.MEMBER_STATUS.class)
    private String status;

    private Date updatedTime;

    private Date createdTime;

    public Long getRelationId(){
        return relationId;
    }

    public void setRelationId(Long relationId){
        this.relationId = relationId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getTermTime(){
        return termTime;
    }

    public void setTermTime(Date termTime){
        this.termTime = termTime;
    }

    public Long getDomainId(){
        return domainId;
    }

    public void setDomainId(Long domainId){
        this.domainId = domainId;
    }

    public boolean isCertified(){
        return certified;
    }

    public void setCertified(boolean certified){
        this.certified = certified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getName(){
        return nickName;
    }
}
