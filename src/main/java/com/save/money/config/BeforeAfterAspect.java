package com.save.money.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Aspect
@Configuration
public class BeforeAfterAspect {
    @Before(value = "com.save.money.config.CommonJoinPointConfig.allLayerExecution()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("before {}", joinPoint);
    }

    @After(value = "com.save.money.config.CommonJoinPointConfig.allLayerExecution()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        log.info("after {}", joinPoint);
    }

}
