package com.zio.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AspectLogging {

    Log log = LogFactory.getLog(getClass().getName());

    @Before("execution(public * com.zio.apis.*.*(..))")
    public void logRequestIncoming(JoinPoint joinPoint) {
        log.trace("Incoming req at: " + joinPoint.getSignature().getName() + ", at time: " + LocalDateTime.now());
    }

}
