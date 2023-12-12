package com.example.cult_of_tim.cultoftim.controller;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;

@RestController
@RequestMapping("/test")
public class SqlErroringController {



    @GetMapping("/sql-error")
    public ResponseEntity<Void> getSqlError() {
        //return ResponseEntity.noContent().build();
        throw new JDBCException("", new SQLException(""));

        //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't execute request");
    }
}
