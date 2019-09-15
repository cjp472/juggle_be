package com.juggle.juggle.schedule.aspect;

import com.juggle.juggle.primary.Constants;
import com.juggle.juggle.primary.log.model.ScheduleLog;
import com.juggle.juggle.primary.schedule.model.ScheduleTask;
import com.juggle.juggle.primary.log.service.ScheduleLogService;
import com.juggle.juggle.primary.schedule.service.ScheduleTaskService;
import com.juggle.juggle.schedule.annotation.SchLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SchLogAspect {
    private static Logger logger = LoggerFactory.getLogger(SchLogAspect.class);

    private ScheduleTask scheduleTask;

    private ScheduleLog scheduleLog;

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Autowired
    private ScheduleLogService scheduleLogService;

    @Pointcut("@annotation(com.juggle.juggle.schedule.annotation.SchLog)")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        SchLog schLog = getSchLog(proceedingJoinPoint);
        if(null == schLog){
            return;
        }
        Date currentTime = new Date();
        scheduleTask = scheduleTaskService.findByCode(schLog.name());
        scheduleTask.setLastSyncStatus(Constants.SCHEDULE_TASK_LAST_SYNC_STATUS.ING);
        scheduleTask.setLastSyncTime(currentTime);
        scheduleTaskService.save(scheduleTask);
        this.scheduleLog = new ScheduleLog();
        scheduleLog.setTaskId(scheduleTask.getId());
        scheduleLog.setStartTime(currentTime);
        scheduleLog.setStatus(Constants.SCHEDULE_LOG_STATUS.NEW);
        scheduleLogService.save(scheduleLog);
        proceedingJoinPoint.proceed();
        try{
            currentTime = new Date();
            scheduleLog.setEndTime(currentTime);
            scheduleLog.setStatus(Constants.SCHEDULE_LOG_STATUS.SUCCESS);
            scheduleLogService.save(scheduleLog);
            scheduleTask.setLastSyncStatus(Constants.SCHEDULE_TASK_LAST_SYNC_STATUS.SUCCESS);
            scheduleTask.setLastSyncTime(currentTime);
            scheduleTaskService.save(scheduleTask);
            logger.warn("Successful implementation of planned task:"+schLog.name());
        }catch (Throwable t){
            currentTime = new Date();
            scheduleLog.setEndTime(currentTime);
            scheduleLog.setStatus(Constants.SCHEDULE_LOG_STATUS.FAIL);
            scheduleLog.setException(t.getMessage());
            scheduleLogService.save(scheduleLog);
            scheduleTask.setLastSyncStatus(Constants.SCHEDULE_TASK_LAST_SYNC_STATUS.FAIL);
            scheduleTask.setLastSyncTime(currentTime);
            scheduleTaskService.save(scheduleTask);
            logger.warn("Failure to execute planned task:"+schLog.name());
        }
    }

    public SchLog getSchLog(ProceedingJoinPoint joinPoint) throws Exception{
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature)signature).getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
        SchLog schLog = realMethod.getAnnotation(SchLog.class);
        return schLog;
    }
}
