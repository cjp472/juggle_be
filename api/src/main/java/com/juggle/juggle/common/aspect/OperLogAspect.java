package com.juggle.juggle.common.aspect;

import com.juggle.juggle.common.annotation.OperLog;
import com.juggle.juggle.framework.common.session.UserSession;
import com.juggle.juggle.framework.common.utils.IpUtils;
import com.juggle.juggle.primary.log.model.OperateLog;
import com.juggle.juggle.primary.log.service.OperateLogService;
import com.juggle.juggle.primary.system.model.User;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperLogAspect {
    private static Logger logger = LoggerFactory.getLogger(OperLogAspect.class);

    @Autowired
    private OperateLogService operateLogService;

    @Pointcut("@annotation(com.juggle.juggle.common.annotation.OperLog)")
    public void pointCut() {

    }


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        OperLog operLog = getOperLog(proceedingJoinPoint);
        if(null!=operLog){
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            User user = (User) UserSession.getAuthorize().getUser();
            OperateLog operateLog = new OperateLog();
            operateLog.setLoginName(user.getLoginName());
            operateLog.setRealName(user.getRealName());
            operateLog.setContent(operLog.name());
            operateLog.setIp(IpUtils.getIpAddr(request));
            operateLogService.create(operateLog);
            logger.warn(operLog.name());
        }
        return proceedingJoinPoint.proceed();
    }

    public OperLog getOperLog(ProceedingJoinPoint joinPoint) throws Exception{
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature)signature).getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
        OperLog operLog = realMethod.getAnnotation(OperLog.class);
        return operLog;
    }
}
