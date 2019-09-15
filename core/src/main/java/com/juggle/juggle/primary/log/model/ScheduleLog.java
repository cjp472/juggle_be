package com.juggle.juggle.primary.log.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "schedule_log")
@ExtView
public class ScheduleLog extends LongEntity {
    @One(value = "task",target = ScheduleTask.class)
    private Long taskId;

    private Date startTime;

    private Date endTime;

    @Filtered
    @State(Constants.SCHEDULE_LOG_STATUS.class)
    private String status;

    private String exception;

    private Date createdTime;

    public Long getTaskId(){
        return taskId;
    }

    public void setTaskId(Long taskId){
        this.taskId = taskId;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getException(){
        return exception;
    }

    public void setException(String exception){
        this.exception = exception;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}

