package com.juggle.juggle.primary.log.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "operate_log")
public class OperateLog extends LongEntity {
    @Filtered
    private String loginName;

    @Filtered
    private String realName;

    private String ip;

    @Filtered
    private String content;

    private Date createdTime;

    private Long createdBy;

    public String getLoginName(){
        return loginName;
    }

    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    public String getRealName(){
        return realName;
    }

    public void setRealName(String realName){
        this.realName = realName;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public Long getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(Long createdBy){
        this.createdBy = createdBy;
    }
}
