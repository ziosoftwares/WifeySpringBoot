package com.zio.common.data.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private int status = 500;
    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
