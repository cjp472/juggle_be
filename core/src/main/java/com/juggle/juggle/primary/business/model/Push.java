package com.juggle.juggle.primary.business.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "push")
public class Push extends AuditEntity {
    @Filtered
    @NotBlank
    private String title;

    @Filtered
    @NotBlank
    private String content;

    @NotNull
    private Date pushTime;

    private Integer totalCount;

    private Integer pushCount;

    @Filtered
    @State(Constants.PUSH_STATUS.class)
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

    public Date getPushTime(){
        return pushTime;
    }

    public void setPushTime(Date pushTime){
        this.pushTime = pushTime;
    }

    public Integer getTotalCount(){
        return totalCount;
    }

    public void setTotalCount(Integer totalCount){
        this.totalCount = totalCount;
    }

    public Integer getPushCount(){
        return pushCount;
    }

    public void setPushCount(Integer pushCount){
        this.pushCount = pushCount;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
