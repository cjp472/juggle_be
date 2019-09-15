package com.juggle.juggle.primary.business.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "group_sms")
public class GroupSms extends AuditEntity {
    @Filtered
    private String title;

    @Filtered
    private String content;

    private Integer totalCount;

    private Integer sendCount;

    private Date sendTime;

    @Filtered
    @State(Constants.GROUP_SMS_STATUS.class)
    private String status;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Integer getTotalCount(){
        return totalCount;
    }

    public void setTotalCount(Integer totalCount){
        this.totalCount = totalCount;
    }

    public Integer getSendCount(){
        return sendCount;
    }

    public void setSendCount(Integer sendCount){
        this.sendCount = sendCount;
    }

    public Date getSendTime(){
        return sendTime;
    }

    public void setSendTime(Date sendTime){
        this.sendTime = sendTime;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
