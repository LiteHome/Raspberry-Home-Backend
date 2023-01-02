package com.iot.rashome.commons.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
    
    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(public * com.iot.rashome.controller..*.*(..))")
    public void controllerPointcut(){}

}
