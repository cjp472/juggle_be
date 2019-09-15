package com.juggle.juggle.primary.schedule.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="schedule_task")
public class ScheduleTask extends LongEntity {
    @Filtered
    private String code;

    @Filtered
    @State(Constants.SCHEDULE_TASK_TYPE.class)
    private String type;

    @Filtered
    private String name;

    private String detail;

    private String taskTime;

    private Integer taskInterval;

    private boolean enabled;

    private Date lastSyncTime;

    @State(Constants.SCHEDULE_TASK_LAST_SYNC_STATUS.class)
    private String lastSyncStatus;

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public String getTaskTime(){
        return taskTime;
    }

    public void setTaskTime(String taskTime){
        this.taskTime = taskTime;
    }

    public Integer getTaskInterval(){
        return taskInterval;
    }

    public void setTaskInterval(Integer taskInterval){
        this.taskInterval = taskInterval;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public Date getLastSyncTime(){
        return lastSyncTime;
    }

    public void setLastSyncTime(Date lastSyncTime){
        this.lastSyncTime = lastSyncTime;
    }

    public String getLastSyncStatus(){
        return lastSyncStatus;
    }

    public void setLastSyncStatus(String lastSyncStatus){
        this.lastSyncStatus = lastSyncStatus;
    }
}
