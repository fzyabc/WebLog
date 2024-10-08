package com.fzy.weblog.admin.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminAdvice {
    @Around("execution(* com.fzy.weblog.admin.controller.*.findArticlePageList(..))")
    public Object adminAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start=System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end=System.currentTimeMillis();
        System.out.println("目标方法: "+joinPoint.toShortString()+"的执行时间是："+(end-start)/1000+"秒");

        return result;
    }
}
