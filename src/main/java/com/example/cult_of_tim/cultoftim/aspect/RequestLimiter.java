package com.example.cult_of_tim.cultoftim.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
public class RequestLimiter {
    private int requestCount = 0;
    private Instant currentInstant = Instant.now();



    @Around("within(com.example.cult_of_tim.cultoftim.controller.*)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        updateCurrentInstant();
        var currentRequests = requestCount++;
        if (currentRequests > 50000) {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Too many requests");
        }

        Object retVal = pjp.proceed();
        return retVal;
    }

    private Instant updateCurrentInstant() {
        var now = Instant.now();
        if (now.minus(1, ChronoUnit.MINUTES).isAfter(currentInstant)) {
            currentInstant = now;
            requestCount = 0;
        }
        return currentInstant;
    }

}
