package com.zio.common.util;

import com.zio.common.data.api.Error;

public class ZioException extends Exception {

    public Error error;

    public ZioException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
