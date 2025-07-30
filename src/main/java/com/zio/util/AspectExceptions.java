package com.zio.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AspectExceptions {

    @Autowired
    Environment environment;

    Log log = LogFactory.getLog(getClass().getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>("check logs", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
