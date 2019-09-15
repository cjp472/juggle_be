package com.juggle.juggle.primary.approval.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.app.model.Member;
import com.juggle.juggle.primary.approval.forumal.CalApprovalExtra;
import com.juggle.juggle.primary.system.model.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "approval")
@Formula(value="extra",calc = CalApprovalExtra.class)
@ExtView
public class Approval extends LongEntity {
    @Filtered
    @One(value = "member",target = Member.class)
    private Long memberId;

    @Filtered
    @State(Constants.APPROVAL_PROCESS_INSTANCE_TYPE.class)
    private String processInstanceType;

    private Long processInstanceId;

    @Filtered
    @State(Constants.APPROVAL_PROCESS_STATUS.class)
    private String processStatus;

    @One(value = "approver",target = User.class)
    private Long approvalBy;

    private Date approvalTime;

    private Date createdTime;

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public String getProcessInstanceType(){
        return processInstanceType;
    }

    public void setProcessInstanceType(String processInstanceType){
        this.processInstanceType = processInstanceType;
    }

    public Long getProcessInstanceId(){
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId){
        this.processInstanceId = processInstanceId;
    }

    public String getProcessStatus(){
        return processStatus;
    }

    public void setProcessStatus(String processStatus){
        this.processStatus = processStatus;
    }

    public Long getApprovalBy(){
        return approvalBy;
    }

    public void setApprovalBy(Long approvalBy){
        this.approvalBy = approvalBy;
    }

    public Date getApprovalTime(){
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime){
        this.approvalTime = approvalTime;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}
