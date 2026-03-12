package com.zio.util;

import com.zio.data.api.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ZioException extends Exception {

    public Error error;

    public ZioException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
