package com.zio.util;

import com.zio.data.api.Error;
import com.zio.data.api.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AspectExceptions {

    @Autowired
    Environment environment;

    Log log = LogFactory.getLog(getClass().getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> generalException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new Error(exception.getMessage(), 1), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> generalRTException(RuntimeException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new Error("UnknownError", 1), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ZioException.class)
    public ResponseEntity<Error> zioException(ZioException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.error, HttpStatusCode.valueOf(ex.error.getStatus()));
    }

    @ExceptionHandler(ZioRunTimeException.class)
    public ResponseEntity<Error> zioRTException(ZioRunTimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.error, HttpStatusCode.valueOf(ex.error.getStatus()));
    }


}
