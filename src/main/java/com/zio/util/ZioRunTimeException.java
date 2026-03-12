package com.zio.util;

import com.zio.data.api.Error;

public class ZioRunTimeException extends RuntimeException {
    Error error;

    public ZioRunTimeException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
