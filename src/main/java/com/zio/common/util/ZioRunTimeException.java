package com.zio.common.util;

import com.zio.common.data.api.Error;

public class ZioRunTimeException extends RuntimeException {
    Error error;

    public ZioRunTimeException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ZioRunTimeException(ZioException ex) {
        super(ex.getMessage());
        this.error = ex.error;
    }
}
