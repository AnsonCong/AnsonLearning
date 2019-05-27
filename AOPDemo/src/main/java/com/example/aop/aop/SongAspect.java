package com.example.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Anson
 * @date 2019/5/27
 */

@Aspect
@Component
public class SongAspect {

    @Pointcut("execution(* com.example.aop.domain.Singer.sing(..))")
    public void logSong(){}


    @Pointcut("@annotation(com.example.aop.Annotation.LogSong)")
    public void AnnotationSong(){}


    @After("AnnotationSong()")
    public void afterAnnotationSong(){
        System.out.println("After Annotation Song");
    }


    @Before("logSong()")
    public void beforeSong() {
        System.out.println("Before: songName is ");
    }


    @Around("logSong()")
    public void aroundSong(  ProceedingJoinPoint joinPoint) {
        System.out.println("Around before: songName is "  );
        try {
            joinPoint.proceed();
            System.out.println("Around after: songName is " );
        } catch (Throwable throwable) {
            System.out.println("Around exception: songName is " );
            throwable.printStackTrace();
        }

    }



}
