package com.example.cult_of_tim.cultoftim.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;

@Aspect
@Component
public class SqlExceptionAspect {

    private final Logger logger = LoggerFactory.getLogger(RequestLimiter.class);

    @Pointcut("within(com.example.cult_of_tim.cultoftim.controller.*)")
    public void controllerMethods() {
    }

    @AfterThrowing(value = "controllerMethods()", throwing = "ex")
    public Object catchSqlException(JDBCException ex) {
        logger.error(ex.getErrorMessage());
        logger.error("While executing " + ex.getSQL());
        return new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't execute request");
    }
}
