package com.save.money.config;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
    @Pointcut("execution(* com.save.money.controller.*.*(..))")
    public void controllerLayerExecution(){}

    @Pointcut("execution(* com.save.money.services.*.*(..))")
    public void serviceLayerExecution(){}

    @Pointcut("execution(* com.save.money.repository.*.*(..))")
    public void repositoryLayerExecution(){}

    @Pointcut("controllerLayerExecution() || serviceLayerExecution() || repositoryLayerExecution()")
    public void allLayerExecution(){}
}
